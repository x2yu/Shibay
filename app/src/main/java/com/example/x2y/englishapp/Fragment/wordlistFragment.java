package com.example.x2y.englishapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.x2y.englishapp.Adapter.WordAdapter;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Word;
import java.util.ArrayList;
import java.util.List;

public class wordlistFragment extends Fragment{
    private static final String ARG_PARAM = "param";
    private int mParam;//用来表示当前需要展示的是哪一页
    private TextView detail_text;//展示的具体内容，这里简单只用一个TextView展示
    private TextView word_count;//单词总数

    private List<Word> wordList = new ArrayList<>();
    private RecyclerView wordlist_recyclerview;
    private WordAdapter mWordAdapter;

    public  wordlistFragment() {
        // Required empty public constructor
    }
    public static wordlistFragment newInstance(int param) {
        wordlistFragment fragment = new wordlistFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }

        //初始化单词列表
        initWords();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wordlist_fragment, container, false);
        detail_text= (TextView) view.findViewById(R.id.detail_info);
        word_count = (TextView)view.findViewById(R.id.wordlist_count_tv);

        wordlist_recyclerview = (RecyclerView)view.findViewById(R.id.wordlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        wordlist_recyclerview.setLayoutManager(layoutManager);
        mWordAdapter = new WordAdapter(wordList);
        wordlist_recyclerview.setAdapter(mWordAdapter);

        //根据mParam来判断当前展示的是哪一页，根据页数的不同展示不同的信息
        switch (mParam){
         case 0:
             detail_text.setText("单词表1");
             word_count.setText("单词总数:"+wordList.size());
             break;
         case 1:
             detail_text.setText("单词表2");
             word_count.setText("单词总数:"+(wordList.size()+1));
             break;
         case 2:
             detail_text.setText("单词表3");
             word_count.setText("单词总数:"+(wordList.size()+2));
             break;
         case 3:
             detail_text.setText("单词表4");
             word_count.setText("单词总数:"+(wordList.size()+3));
             break;
         default:break;
        } return view;
    }

    private void initWords(){

        //暂时只是测试用数据，具体业务再改动
        for (int i=0;i<50;i++){
            Word apple = new Word("Apple","苹果");
            wordList.add(apple);
        }

    }


}
