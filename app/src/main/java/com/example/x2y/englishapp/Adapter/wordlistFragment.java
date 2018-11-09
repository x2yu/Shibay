package com.example.x2y.englishapp.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.x2y.englishapp.R;

public class wordlistFragment extends Fragment{
    private static final String ARG_PARAM = "param";
    private int mParam;//用来表示当前需要展示的是哪一页
    private TextView detail_text;//展示的具体内容，这里简单只用一个TextView展示

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
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wordlist_fragment, container, false);
        detail_text= (TextView) view.findViewById(R.id.detail_info);
        //根据mParam来判断当前展示的是哪一页，根据页数的不同展示不同的信息
        switch (mParam){
         case 0:
             detail_text.setText("单词表1");
             break;
         case 1:
             detail_text.setText("单词表2");
             break;
         case 2:
             detail_text.setText("单词表3");
             break;
         case 3: detail_text.setText("单词表4");
             break;
         default:break;
        } return view;
    }

}
