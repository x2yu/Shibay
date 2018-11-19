package com.example.x2y.englishapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.LearnActivity;
import com.example.x2y.englishapp.R;

public class homeFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_layout,container,false);
        //初始化进度条
        initProgress(view);
        Button bt = (Button)view.findViewById(R.id.start_learn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开试背单词 尝试用Progressdialog来处理加载时间
                Intent startlearn = new Intent(getActivity(),LearnActivity.class);
                startActivity(startlearn);


            }
        });
        return view;
    }

    private void initProgress(View view){

        //拆分Text并且计算设置progressbar
       TextView havelearned = (TextView)view.findViewById(R.id.word_haveLearned);
       String [] str = havelearned.getText().toString().split("/");
       ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.home_progress);
       try {
           float progress =( Float.parseFloat(str[0])/Float.parseFloat(str[1]))*100;
           progressBar.setProgress((int) progress);
       }catch (Exception e){
           e.printStackTrace();
       }


    }
}
