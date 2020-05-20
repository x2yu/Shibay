package com.example.x2y.englishapp.PersonPage;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.x2y.englishapp.Adapter.WordAdapter;
import com.example.x2y.englishapp.Adapter.wordlistAdapter;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Word;

import java.util.ArrayList;
import java.util.List;

public class LexiconActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private android.support.v4.app.FragmentManager fm;

    private RecyclerView wordlist_recyclerview;
    private List<Word> wordList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon);
        mTabLayout= (TabLayout) findViewById(R.id.wordlist_tablelayout);
        mViewPager= (ViewPager) findViewById(R.id.wordlist_viewpaper);

        View backView = findViewById(R.id.word_list_back);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fm = getSupportFragmentManager();

        mViewPager.setAdapter(new wordlistAdapter(LexiconActivity.this, fm));
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
