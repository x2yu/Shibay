package com.example.x2y.englishapp.schedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.x2y.englishapp.R;

import java.util.ArrayList;

/**
 * 书架的RecyclerView的适配器
 */
public class BookshelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int itemLayoutID = R.layout.study_schedule_bookshelf_layout; //子项的布局文件
    public static final int bookLearningImage = R.drawable.book_learning;  //目前在学习的书的图片
    public static final int bookSelectedImage = R.drawable.book_selected;  //被选中的书的图片
    public static final int bookUnSelectedImage = R.drawable.book_unselected;  //没用被选中的书的图片
    public static final int bookReflush = R.drawable.book_reflush;  //书籍刷新图片
    public static final int bookDelete = R.drawable.book_delete;


    private Bookshelf bookshelf = null;  //英语书名字, 第一本书表示正在学习的书

    private int bookSelectedOrder = -1;  //记录被选中的书的下标, -1表示没有书被选中，准确的记录
    private ViewHolder bookSeletedViewHolder = null;  //这个记录不安全，只有在最初能正确记录被选中的书，当RecyclerView滑动时,此时记录变得不准确。
    private Context context = null;

    private ArrayList<ViewHolder> viewHoldersList = new ArrayList<ViewHolder>();



    public BookshelfAdapter(Context context, Bookshelf bookshelf){
        this.bookshelf = bookshelf;
        this.context = context;
    }


    public int getBookSelectedOrder(){
        return this.bookSelectedOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayoutID, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, this);
        viewHoldersList.add(viewHolder);
        if(bookshelf.getBookEditState()){
            viewHolder.operateImage.setVisibility(View.VISIBLE);
        }


        Log.d("Create", "                                          order: " + i);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder innerViewHolder = (ViewHolder)viewHolder;
        Book book = bookshelf.getBookByOrder(i);

        innerViewHolder.order = i;
        innerViewHolder.textView.setText(book.getBookName());

        if(book.getIsLearnningState()){
            innerViewHolder.bookImage.setImageResource(bookLearningImage);
            innerViewHolder.operateImage.setImageResource(bookReflush);
        }else{
            innerViewHolder.bookImage.setImageResource(bookUnSelectedImage);
            innerViewHolder.operateImage.setImageResource(bookDelete);
        }
        if(i == bookSelectedOrder){
            innerViewHolder.bookImage.setImageResource(bookSelectedImage);
        }
    }

    @Override
    public int getItemCount() {
        return bookshelf.getBooksSize();
    }

    public void setOperateImageVison(boolean isVison){
        int flag = View.INVISIBLE;
        if(isVison){
            flag = View.VISIBLE;
        }

        Log.d("Length", "                                     length: " + viewHoldersList.size());

        for (ViewHolder viewHolder : viewHoldersList){
            viewHolder.operateImage.setVisibility(flag);
        }

    }

    public ViewHolder removeItemFromViewHoldersList(int order){
        int index = -1;
        for(int i = viewHoldersList.size() - 1; i >= 0; --i){
            if(viewHoldersList.get(i).order == order){
                index = i;
                break;
            }
        }
        if(index >= 0){
            return  viewHoldersList.remove(index);
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage = null;
        TextView textView = null;
        ImageView operateImage = null;
        BookshelfAdapter bookshelfAdapter = null;
        int order = 0;

        public ViewHolder(@NonNull View itemView, BookshelfAdapter bookshelfAdapter) {
            super(itemView);
            bookImage = (ImageView)itemView.findViewById(R.id.schedule_bookshelf_show_bookImage);
            textView = (TextView)itemView.findViewById(R.id.schedule_bookshelf_show_bookName);
            operateImage = (ImageView)itemView.findViewById(R.id.schedule_bookshelf_show_operate);
            this.itemView.setOnClickListener(new BookFaceListener());
            this.operateImage.setOnClickListener(new BookOperateListener());

            this.bookshelfAdapter = bookshelfAdapter;
        }

        class BookFaceListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                int innderOrder = 0;
                //设置书籍被选中时的状态
                if(bookshelfAdapter.bookSelectedOrder != order) {
                    int orderTemp = bookshelfAdapter.bookSelectedOrder;

                    if(bookshelfAdapter.bookSeletedViewHolder != null &&
                            bookshelfAdapter.bookSelectedOrder == bookshelfAdapter.bookSeletedViewHolder.order){
                        if(bookshelfAdapter.bookSelectedOrder == 0){
                            bookshelfAdapter.bookSeletedViewHolder.bookImage.setImageResource(bookLearningImage);
                        }else{
                            bookshelfAdapter.bookSeletedViewHolder.bookImage.setImageResource(bookUnSelectedImage);
                            bookshelfAdapter.notifyItemChanged(bookshelfAdapter.bookSelectedOrder);
                        }
                    }

                    bookshelfAdapter.bookSelectedOrder = order;
                    bookshelfAdapter.bookSeletedViewHolder = ViewHolder.this;

                    bookImage.setImageResource(bookSelectedImage);
                    bookshelfAdapter.bookshelf.showBookInformation(order);
                    innderOrder = order;
                }else{
                    if (order == 0) {
                        bookImage.setImageResource(bookLearningImage);
                    }else{
                        bookImage.setImageResource(bookUnSelectedImage);
                    }
                    bookshelfAdapter.bookSeletedViewHolder = null;
                    bookshelfAdapter.bookSelectedOrder = -1;
                    bookshelfAdapter.bookshelf.showBookInformation(0);
                    innderOrder = 0;
                }
                bookshelfAdapter.bookshelf.notifyScheduleModify(innderOrder);
            }
        }

        class BookOperateListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(bookshelfAdapter.context);
                final Dialog alertDialog = builder.create();
                View view = LayoutInflater.from(bookshelfAdapter.context).inflate(R.layout.study_schedule_bookshelf_show_operate_layout,  null);

                if(order == 0){
                    TextView content = view.findViewById(R.id.schedule_bookshelf_show_operate_content);
                    content.setText("真的要重置码？你将重置本书的所用学习信息！");
                }else{
                    TextView content = view.findViewById(R.id.schedule_bookshelf_show_operate_content);
                    content.setText("真的要删除本书？你将从书架中删除本书！");
                }
                TextView cancel = view.findViewById(R.id.schedule_bookshelf_show_operate_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
//                        Toast.makeText(bookshelfAdapter.context, "我是cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                TextView ensure = view.findViewById(R.id.schedule_bookshelf_show_operate_ensure);
                ensure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int ordert = order;
                        alertDialog.dismiss();

                        if(ordert == 0){
                            bookshelfAdapter.bookshelf.operateBook(0, Bookshelf.BOOK_RESET);
                            bookshelfAdapter.bookshelf.showBookInformation(0);
                            bookshelfAdapter.bookshelf.notifyScheduleModify(0);
                        }else{
                            int orderTemp2 = 0;
                            int orderTemp = bookshelfAdapter.bookSelectedOrder;
                            bookshelfAdapter.bookshelf.operateBook(ordert, Bookshelf.BOOK_DELETE);
//                            bookshelfAdapter.removeItemFromViewHoldersList(ordert);
                            if(ordert == orderTemp){
                                bookshelfAdapter.bookshelf.showBookInformation(0);
                                bookshelfAdapter.bookSelectedOrder = -1;
                                bookshelfAdapter.bookSeletedViewHolder = null;
                            }else if(ordert < orderTemp){
                                bookshelfAdapter.bookSelectedOrder = orderTemp - 1;
                                bookshelfAdapter.bookSeletedViewHolder.order = orderTemp - 1;
                                orderTemp2 = orderTemp - 1;
                            }
                            bookshelfAdapter.bookshelf.notifyScheduleModify(orderTemp2);
                        }
                    }
                });

                alertDialog.setCancelable(false);
                alertDialog.show();
                alertDialog.getWindow().setContentView(view);
            }
        }
    }

}
