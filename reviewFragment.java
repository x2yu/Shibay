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

import com.example.x2y.englishapp.R;

public class reviewFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_layout,container,false);

        //利用view获取对应碎片中对应textview的布局
        final TextView selfTest=(TextView)view.findViewById(R.id.self_test);
        final TextView review=(TextView)view.findViewById(R.id.review);
        //自我评测的点击监听事件，用于跳转到自我评测的活动中去
        selfTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent selfTestIntent=new Intent("SELF_TEST");
                startActivity(selfTestIntent);
            }
        });
        //温故知新的点击监听事件，用于跳转到温故知新的活动中去
        review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent reviewIntrnt=new Intent("REVIEW");
                startActivity(reviewIntrnt);
            }
        });
        return view;
    }
}
