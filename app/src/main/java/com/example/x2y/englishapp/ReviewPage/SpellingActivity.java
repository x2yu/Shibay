package com.example.x2y.englishapp.ReviewPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Word;

import java.util.ArrayList;
import java.util.List;

/*
该活动是单词拼写的具体实现的活动，其中包括了单词拼写，检查，提示，上一个，下一个等操作，并有完成拼写和剩余
单词的计数
 */
public class SpellingActivity extends AppCompatActivity {
    private ImageView back;
    private TextView mean, word, speld, surplus;
    private Button previous, next, check, toast;
    private EditText spel;
    private int index = 0, count = 0, successCount = 0;
    initWord initWord = new initWord();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling);
        //dbHelper = new MydatabaseHelper(this,"数据库名",null,1);
        //获取布局文件中的资源
        back = findViewById(R.id.spelling_back);
        mean = findViewById(R.id.spelling_mean);
        word = findViewById(R.id.spelling_word);
        speld = findViewById(R.id.spelling_speled);
        surplus = findViewById(R.id.spelling_surplus);
        previous = findViewById(R.id.spelling_previous);
        next = findViewById(R.id.spelling_next);
        check = findViewById(R.id.spelling_check);
        toast = findViewById(R.id.spelling_toast);
        spel = findViewById(R.id.spelling_spel);
        //相关变量初始化
        //待拼写单词，完成单词数，剩余单词数的初始化
        mean.setText(initWord.wordList.get(index).getMean());
        speld.setText(getResources().getString(R.string.finsihed) + successCount);
        surplus.setText(getResources().getString(R.string.surplus) + (initWord.max - successCount));

        //返回按钮的点击监听事件，其中调用finish（）方法销毁当前活动，返回到之前的碎片
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //检查按钮的点击监听事件
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化完成单词数为0
                successCount = 0;

                //判断单词是否拼写正确
                if ((spel.getText().toString()).equals(initWord.wordList.get(index).getName())) {
                    //拼写正确，显示对应toast，单词完成数增加一个，并且当前单词对应的拼写flag置为true
                    Toast.makeText(SpellingActivity.this, getResources().getString(R.string.spelling_success), Toast.LENGTH_SHORT).show();
                    word.setText(initWord.wordList.get(index).getName());
                    initWord.rightFlag[index] = true;
                }
                //拼写错误，显示对应toast，并且将单词编辑框置空
                else {
                    Toast.makeText(SpellingActivity.this, getResources().getString(R.string.spelling_fail), Toast.LENGTH_SHORT).show();
                    mean.setText(null);
                    spel.setText(null);
                }
                //遍历拼写flag列表，检查置为true的数量，得到拼写正确数，更新显示部分
                for (int i = 0; i <= index; i++) {
                    if (initWord.rightFlag[i]) successCount++;
                }
                speld.setText(getResources().getString(R.string.finsihed) + successCount);
                surplus.setText(getResources().getString(R.string.surplus) + (initWord.max - successCount));
                if (successCount == initWord.max) {
                    Toast.makeText(SpellingActivity.this, getResources().getString(R.string.review_finish_toast), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        //提示按钮的点击监听事件，点击提示按钮时将会将当前单词显示出来
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setText(initWord.wordList.get(index).getName());
            }
        });
        //上一个按钮的点击监听事件
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前单词是否为第一个单词
                if (index == 0)
                    //当前为第一个单词，显示一段提示性文字
                    Toast.makeText(SpellingActivity.this, getResources().getString(R.string.previous_toast), Toast.LENGTH_SHORT).show();
                else {
                    //当前不是第一个单词，当前单词下标减一，显示上一个单词意思
                    index--;
                    mean.setText(initWord.wordList.get(index).getMean());
                    //判断上一个单词是否已经拼写正确，正确时显示单词意思，单词，错误时清空单词显示框和拼写框
                    if (initWord.rightFlag[index]) {
                        word.setText(initWord.wordList.get(index).getName());
                        spel.setText(initWord.wordList.get(index).getName());
                    } else {
                        word.setText(null);
                        spel.setText(null);
                    }
                }
            }
        });
        //下一个按钮的点击监听事件
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前单词是否为最后一个单词
                if (index == initWord.max - 1)
                    //当前单词为最后一个单词，显示一段提示性文字
                    Toast.makeText(SpellingActivity.this, getResources().getString(R.string.next_toast), Toast.LENGTH_SHORT).show();
                else {
                    //当前单词不是最后一个单词，单词下标加一，显示下一个单词的意思
                    index++;
                    mean.setText(initWord.wordList.get(index).getMean());
                    //判断当前单词是否已经拼写正确，正确时单词显示宽和单词拼写框显示当前单词，错误时清空单词显示框和单词拼写框
                    if (initWord.rightFlag[index]) {
                        word.setText(initWord.wordList.get(index).getName());
                        spel.setText(initWord.wordList.get(index).getName());
                    } else {
                        word.setText(null);
                        spel.setText(null);
                    }
                }
            }
        });
    }
}
