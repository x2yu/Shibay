package com.example.x2y.englishapp.Adapter;

//显示词典用到的适配器
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.Utils.BookActivity;
import com.example.x2y.englishapp.bean.Books;

import java.util.List;

/**
 * Created by Lab on 2018/11/16.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<Books> mBookList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookImage;
        TextView bookName;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView) view;
            bookImage=(ImageView)view.findViewById(R.id.book_image);
            bookName=(TextView)view.findViewById(R.id.book_name);
        }
    }
    public BookAdapter(List<Books> bookList){
        mBookList=bookList;
    }
    public  ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.book_item,parent,false);

        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Books books=mBookList.get(position);
                Intent intent=new Intent(mContext,BookActivity.class);
                intent.putExtra(BookActivity.BOOK_NAME,books.getName());
                intent.putExtra(BookActivity.BOOK_IMAGE_ID,books.getImageId());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }
    public void onBindViewHolder(ViewHolder holder,int position){
        Books books=mBookList.get(position);
        holder.bookName.setText(books.getName());
        Glide.with(mContext).load(books.getImageId()).into(holder.bookImage);
    }
    public int getItemCount(){
        return mBookList.size();
    }
}
