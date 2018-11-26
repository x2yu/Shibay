package com.example.x2y.englishapp.schedule;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.x2y.englishapp.R;

import java.util.ArrayList;

public class BookLibrary {
    public static final int itemLayout = R.layout.study_schedule_new_book_selected_layout;
    public static final int HOT = 0;
    public static final int PRIMARY_SCHOOL = 1;
    public static final int MIDDLE_SCHOOL = 2;
    public static final int COLLEGE = 3;
    public static final int OTHERS = 4;
    public static final int libraryCount = 5;


    private Activity activity = null;
    private RecyclerView recyclerView = null;
    private BookLibraryAdapter bookLibraryAdapter = null;
    private Book selectedBook = null;
    private ArrayList<Book>[] bookLibrarysContents = null;


    public BookLibrary(Activity activity){
        this.activity = activity;

        bookLibrarysContents = ScheduleDataManager.initailBookLibraryData();
    }

    public boolean changeAdapter(int flag){
        BookLibraryAdapter temp = createBookLibraryAdapter(flag);

        if(temp != null) {
            bookLibraryAdapter = temp;
            recyclerView.setAdapter(bookLibraryAdapter);
            return  true;
        }else{
            return false;
        }
    }

    public void setLibrarysContents(ArrayList<Book>[] bookLibrarysContents){
        this.bookLibrarysContents = bookLibrarysContents;
    }


    public void saveDate(){
        Bookshelf bookshelf = ScheduleDataManager.instance().getBookshelf();
        if(selectedBook != null){

            if(bookshelf != null){
                bookshelf.addBook(selectedBook);
                bookshelf.notifyDataChange();
             }


            /**
             *
             */
        }


    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    private BookLibraryAdapter createBookLibraryAdapter(int flag){
        ArrayList<Book> booksList = null;
        if(flag < libraryCount) booksList = bookLibrarysContents[flag];
        else return null;
        return new BookLibraryAdapter(booksList);
    }




    class BookLibraryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<Book> booksList = null;

        public BookLibraryAdapter(ArrayList<Book> booksList){
            this.booksList = booksList;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(activity).inflate(itemLayout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ViewHolder holder = (ViewHolder)viewHolder;
            Book book = booksList.get(i);

            holder.book = book;
            holder.selectedName.setText(book.getBookName());
        }

        @Override
        public int getItemCount() {
            return booksList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView selectedName = null;
            ImageView selectedImage = null;
            Book book = null;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                selectedImage = (ImageView)itemView.findViewById(R.id.schedule_new_selected_image);
                selectedName = (TextView)itemView.findViewById(R.id.schedule_new_selected_name);
                selectedImage.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                selectedImage.setImageResource(R.drawable.book_selected);
                selectedBook = book;
                saveDate();
                activity.finish();

            }
        }
    }
}
