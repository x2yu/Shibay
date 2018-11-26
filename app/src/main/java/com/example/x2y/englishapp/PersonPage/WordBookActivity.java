package com.example.x2y.englishapp.PersonPage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.x2y.englishapp.Adapter.BookAdapter;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Books;

import java.util.ArrayList;
import java.util.List;

public class WordBookActivity extends AppCompatActivity {

    private Books[] books={new Books("四级词汇",R.drawable.cet4),new Books
            ("六级词汇",R.drawable.cet6),new Books("考研词汇",R.drawable.pg)};
    private List<Books> booksList=new ArrayList<>();
    private BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_book);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        initBooks();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new BookAdapter(booksList);
        recyclerView.setAdapter(adapter);

    }
    private void initBooks(){
        booksList.clear();
        for(int i=0;i<(books.length);i++){
            booksList.add(books[i]);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
