package com.example.x2y.englishapp.schedule;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bookshelf implements View.OnClickListener{
    public static final int BOOK_RESET = 0;  //书籍信息重置标志
    public static final int BOOK_DELETE = 1;  //删除书籍标志

    private ArrayList<Book> booksList = null;  //书籍集合
    private boolean bookEditState = false;  //判断书籍是否可以编辑
    private BookshelfAdapter bookshelfAdapter = null;  //书籍展示的RecyclerView的适配器

    private TextView bookName = null;
    private TextView bookContentCount = null;
    private TextView bookLearnedCount = null;
    private TextView bookSchedule = null;

    public Bookshelf(){
        initialBooksList();
    }



    //通过下标获得书籍
    public Book getBookByOrder(int order) {
        if(order <= booksList.size()){
            return booksList.get(order);
        }else {
            return null;
        }
    }

    //获得书籍的数目
    public int getBooksSize(){
        return booksList.size();
    }

    //通过下标删除指定书籍
    public boolean deleteBookByOrder(int order){
        if(bookEditState && order <= booksList.size() && order >= 0){
            booksList.remove(order);
            return true;
        }else {
            return false;
        }
    }

    //添加书籍
    public void addBook(Book book){
        if(book != null) booksList.add(book);
    }

    //获取书籍编辑状态
    public boolean getBookEditState(){
        return bookEditState;
    }

    /*//设置书籍状态
    public void setBookEditState(boolean bookEditState){
        this.bookEditState = bookEditState;
    }*/

    public void setBookshelfAdapter(BookshelfAdapter bookshelfAdapter){
        this.bookshelfAdapter = bookshelfAdapter;
    }

    @Override
    public void onClick(View v) {
        bookEditState = !bookEditState;
        if (bookshelfAdapter != null) {
            bookshelfAdapter.setOperateImageVison(bookEditState);
        }else{
            throw new NullPointerException("Bookshelf对象没有设置BookshelfAdapter对象");
        }
    }

    public void notifyDataChange(){
        bookshelfAdapter.notifyDataSetChanged();
    }


    /**
     * 重置书籍信息
     * @param order 重置书籍的在集合中序号
     * @return  返回执行结果
     */
    private boolean resetBookInformation(int order){
        Book book = null;

        if(order < booksList.size() && order >= 0){
            book = booksList.get(order);

            book.setLearnedWorldCount(0);
            book.setStudyDeadline(null);

            saveBookReset(book);
            return true;
        }
        return false;
    }

    /**
     * 从书架中删除书籍
     * @param order 被删除书籍在集合中序号
     * @return  返回执行结果
     */
    private boolean deleteBook(int order){
        if(order < booksList.size() && order >= 0){
            saveBookDelete(booksList.remove(order));
            bookshelfAdapter.notifyItemRemoved(order);
            bookshelfAdapter.notifyItemRangeChanged(order, booksList.size() - order);
            return true;
        }
        return false;
    }

    /**
     * 执行书架中书籍的操作
     * @param order  书籍在集合中序号
     * @param flag  书籍执行的行为
     * @return  返回执行结果
     */
    public boolean operateBook(int order, int flag){
        if(flag == Bookshelf.BOOK_DELETE){
            return deleteBook(order);
        }else{
            return resetBookInformation(order);
        }
    }

    public void setBookContentShowControls(TextView bookName, TextView bookContentCount, TextView bookLearnedCount, TextView bookSchedule){
        this.bookName = bookName;
        this.bookContentCount = bookContentCount;
        this.bookLearnedCount = bookLearnedCount;
        this.bookSchedule = bookSchedule;
    }

    /**
     * 显示书籍的具体信息
     * @param order
     */
    public void showBookInformation(int order) {
        Book book = null;

        if (order < booksList.size() && order >= 0){
            book = booksList.get(order);
            bookName.setText(book.getBookName());
            bookContentCount.setText("总词量：" + book.getWorldCount());
            bookLearnedCount.setText("已学习：" + book.getLearnedWorldCount());

            String str = book.getStudyDeadline();
            if(str == null){
                str = "亲，您还没设置哦！";
            }
            bookSchedule.setText("计划完成时间：" + str);
        }
    }

    public void notifyScheduleModify(int order){
        if (order < booksList.size() && order >= 0){
            ScheduleDataManager.instance().getScheduleModify().setBook(booksList.get(order));
        }
    }


    public void saveBookDelete(Book book){
        /**
         *
         */
    }

    public void saveBookReset(Book book){
        /**
         *
         */
    }

    public void saveBookSelected(){
        /**
         *
         */
    }



    //初始化书籍信息
    private  void initialBooksList(){
        booksList = ScheduleDataManager.initailBookshelfData();
    }
}
