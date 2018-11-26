package com.example.x2y.englishapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.TextView;


import com.example.x2y.englishapp.ListeningAndRememberActivity;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.ReviewPage.ChooseInChineseActivity;
import com.example.x2y.englishapp.ReviewPage.ReviewActivity;
import com.example.x2y.englishapp.ReviewPage.SelfTestActivity;
import com.example.x2y.englishapp.ReviewPage.SpellingActivity;

public class reviewFragment extends Fragment {
    private static TextView selftestTV, reviewTV, spellingTV, cicTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.review_layout, container, false);
        initReview(view);
        

        return view;
    }

    private void initReview(View view) {
        //自测
        selftestTV = (TextView) view.findViewById(R.id.self_test);
        selftestTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelfTestActivity.class);
                startActivity(intent);
            }
        });
        //温故知新
        reviewTV = (TextView) view.findViewById(R.id.word_review);
        reviewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                startActivity(intent);
            }
        });
        //拼写练习
        spellingTV = (TextView) view.findViewById(R.id.spelling);
        spellingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpellingActivity.class);
                startActivity(intent);
            }
        });
        //中文选词
        cicTV = (TextView) view.findViewById(R.id.choose_in_chinese);
        cicTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseInChineseActivity.class);
                startActivity(intent);
            }
        });
    }
}
