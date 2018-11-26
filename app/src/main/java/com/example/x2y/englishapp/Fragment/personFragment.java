package com.example.x2y.englishapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.LoginActivity;
import com.example.x2y.englishapp.MyApplication;
import com.example.x2y.englishapp.PersonPage.LexiconActivity;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.PersonPage.WordBookActivity;
import com.example.x2y.englishapp.PersonPage.WordProgressActivity;

import static android.content.Context.MODE_PRIVATE;

public class personFragment extends Fragment
{
    private  SharedPreferences pref;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_layout,container,false);

        //点击单词书跳转活动
        Button wordbook_bt = (Button)view.findViewById(R.id.wordbook_bt);
        TextView nameText =(TextView)view.findViewById(R.id.name_person);
        pref=MyApplication.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        nameText.setText(pref.getString("name",""));
        TextView idText=(TextView) view.findViewById(R.id.id_personid);
        idText.setText(""+pref.getInt("id",0));
        TextView shellText= (TextView)view.findViewById(R.id.shell_number);
        shellText.setText("贝壳："+pref.getInt("gold",0)+"个");
        wordbook_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WordBookActivity.class);
                startActivity(intent);
            }
        });

        //点击我的词库跳转活动
        Button lexicon_bt = (Button)view.findViewById(R.id.lexicon_bt);
        lexicon_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LexiconActivity.class);
                startActivity(intent);
            }
        });

        //点击单词进度跳转活动
        Button word_progress_bt = (Button)view.findViewById(R.id.word_progress_bt);
        word_progress_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WordProgressActivity.class);
                startActivity(intent);
            }
        });

        //点击拓展包跳转活动
        Button expand_package_bt = (Button)view.findViewById(R.id.expand_package_bt);
        expand_package_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(getActivity(),"尚在开发！",Toast.LENGTH_SHORT).show();
            }
        });

        Button bt = (Button)view.findViewById(R.id.person_bt);
        //测试点击
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Fragment3上的按钮被点击了",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
