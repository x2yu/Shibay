package com.example.x2y.englishapp.ReviewPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.R;

public class SelfTestActivity extends AppCompatActivity {
    //定义对应类型的变量，其中forecast功能未实现
    private TextView words, means, forecast;
    private Button remember, forget, preword, nextword;
    private ImageView selfBack;
    /*
    index用于标识当前显示的单词的位置,在测试时将其设置为3，在实际使用
    时应将其设置为19，其表示的是一次自我评测的单词量
     */
    private int index = 0, count = 0, minword = 0, maxword = 0;
    private int[] wordforecast = {1230, 2150};
    initWord initWord=new initWord();
    //获取资源所需类，将代码中的字符移动到资源文件和需要调用该方法获取资源文件中的对应字符
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);
        //获取资源文件中的对应布局
        words =findViewById(R.id.selftest_words);
        means = findViewById(R.id.selftest_means);
        //单词量预测暂时没有实现，单词量预测应给予用户背单词多少和测试时记得的单词量进行综合判定
        forecast = findViewById(R.id.selftest_word_forecast);
        remember = findViewById(R.id.selftest_remember);
        forget = findViewById(R.id.selftest_forget);
        preword = findViewById(R.id.selftest_previous);
        nextword = findViewById(R.id.selftest_next);
        selfBack = findViewById(R.id.selftest_back);
        //返回主页面按钮的点击监听事件
        selfBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //显示第一个单词
        words.setText(initWord.wordList.get(index).getName());
        forecast.setText("预计单词量：" + wordforecast[0] + "-" + wordforecast[1]);
        //“记得”按钮的点击监听事件
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initWord.rightFlag[index] = true;
                //判断是否到达了最后一个单词，到达了显示toast，否则显示下一个单词并且清空当前单词意思
                if (index == initWord.max - 1)
                    Toast.makeText(SelfTestActivity.this, getResources().getString(R.string.next_toast), Toast.LENGTH_SHORT).show();
                else {
                    //更新单词量预测
                    minword = forecastmincheck(wordforecast, initWord.wordList.get(index).getName().length(), true)[0];
                    maxword = forecastmincheck(wordforecast, initWord.wordList.get(index).getName().length(), true)[1];
                    wordforecast[0] = minword;
                    wordforecast[1] = maxword;
                    forecast.setText("预计单词量：" + minword + "-" + maxword);
                    //单词下标加一，显示下一个单词，清空当前单词意思框
                    index++;
                    words.setText(initWord.wordList.get(index).getName());
                    means.setText(null);
                }
            }
        });
        //不记得按钮的点击监听事件
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    //传入预估单词数据，更新预估单词量
                    minword = forecastmincheck(wordforecast, initWord.wordList.get(index).getName().length(), false)[0];
                    maxword = forecastmincheck(wordforecast, initWord.wordList.get(index).getName().length(), false)[1];
                    wordforecast[0] = minword;
                    wordforecast[1] = maxword;
                    forecast.setText("预计单词量：" + minword + "-" + maxword);
                }
                //显示当前单词的意思
                means.setText(initWord.wordList.get(index).getMean());
            }
        });
        //上一个单词按钮的点击监听事件
        preword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断当前单词是否为第一个单词，如果是则显示toast，否则显示上一个单词和意思
                if (index == 0)
                    Toast.makeText(SelfTestActivity.this, getResources().getString(R.string.previous_toast), Toast.LENGTH_SHORT).show();
                else {
                    index--;
                    words.setText(initWord.wordList.get(index).getName());
                    if (initWord.rightFlag[index])
                        means.setText(initWord.wordList.get(index).getMean());
                    else {
                        means.setText(null);
                    }

                }
            }
        });
        //下一个单词按钮的点击监听事件
        nextword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否到达了最后一个单词，如果到了显示toast，否则显示下一个单词，并且将当前单词意思清空
                if (index == initWord.max)
                    Toast.makeText(SelfTestActivity.this, getResources().getString(R.string.next_toast), Toast.LENGTH_SHORT).show();
                else {
                    index++;
                    words.setText(initWord.wordList.get(index).getName());
                    if (initWord.rightFlag[index]) means.setText(initWord.wordList.get(index).getMean());
                    else {
                        means.setText(null);
                    }

                }
            }
        });
    }
    /*
    具体用于估计用户单词量的方法，参数第一个为预估最小值和最大值的值，第二个为单词长度，第三个为增加或减少
    的标志,返回值为更新后的最小值和最大值，其中增加和减少的参数可以进行调整，现在暂设定为具体值
     */
    private int[] forecastmincheck(int wordforecast[], int length, boolean flag) {

        int minword = wordforecast[0], maxword = wordforecast[1];
        int[] res = {0, 0};
        //增加部分
        if (flag) {
            if (length >= 13) {
                if (minword < 2000) minword += (int) (minword * 0.3);
                if (minword >= 2000 && minword < 4000) minword += (int) (minword * 0.18);
                if (minword >= 4000) minword += (int) (minword * 0.13);
                if (maxword < 3000) maxword += (int) (maxword * 0.3);
                if (maxword >= 3000 && maxword < 5000) maxword += (int) (maxword * 0.18);
                if (maxword >= 5000) maxword += (int) (maxword * 0.13);
            }
            if (length >= 6 && length < 13) {
                if (minword < 2000) minword += (int) (minword * 0.25);
                if (minword >= 2000 && minword < 4000) minword += (int) (minword * 0.15);
                if (minword >= 4000) minword += (int) (minword * 0.1);
                if (maxword < 3000) maxword += (int) (maxword * 0.25);
                if (maxword >= 3000 && maxword < 5000) maxword += (int) (maxword * 0.15);
                if (maxword >= 5000) maxword += (int) (maxword * 0.1);
            }
            if (length < 6) {
                if (minword < 2000) minword += (int) (minword * 0.2);
                if (minword >= 2000 && minword < 4000) minword += (int) (minword * 0.12);
                if (minword >= 4000) minword += (int) (minword * 0.07);
                if (maxword < 3000) maxword += (int) (maxword * 0.2);
                if (maxword >= 3000 && maxword < 5000) maxword += (int) (maxword * 0.12);
                if (maxword >= 5000) maxword += (int) (maxword * 0.07);
            }
        }
        //减少部分
        if (!flag) {
            if (length >= 13) {
                if (minword < 2000 && minword >= 500) minword -= (int) (minword * 0.2);
                if (minword >= 2000 && minword < 4000) minword -= (int) (minword * 0.12);
                if (minword >= 4000) minword -= (int) (minword * 0.07);
                if (maxword < 3000 && maxword >= 1000) maxword -= (int) (maxword * 0.18);
                if (maxword >= 3000 && maxword < 5000) maxword -= (int) (maxword * 0.1);
                if (maxword >= 5000) maxword -= (int) (maxword * 0.05);
                //当最小值下降到500，最大值下降到1000左右时间停止减少
                if (minword < 500 || maxword < 1000) ;
            }
            if (length >= 6 && length < 13) {
                if (minword < 2000 && minword >= 500) minword -= (int) (minword * 0.25);
                if (minword >= 2000 && minword < 4000) minword -= (int) (minword * 0.15);
                if (minword >= 4000) minword -= (int) (minword * 0.1);
                if (maxword < 3000 && maxword >= 1000) maxword -= (int) (maxword * 0.23);
                if (maxword >= 3000 && maxword < 5000) maxword -= (int) (maxword * 0.12);
                if (maxword >= 5000) maxword -= (int) (maxword * 0.08);
                if (minword < 500 || maxword < 1000) ;
            }
            if (length < 6) {
                if (minword < 2000 && minword >= 500) minword -= (int) (minword * 0.3);
                if (minword >= 2000 && minword < 4000) minword -= (int) (minword * 0.18);
                if (minword >= 4000) minword -= (int) (minword * 0.13);
                if (maxword < 3000 && maxword >= 1000) maxword -= (int) (maxword * 0.28);
                if (maxword >= 3000 && maxword < 5000) maxword -= (int) (maxword * 0.15);
                if (maxword >= 5000) maxword -= (int) (maxword * 0.12);
                if (minword < 500 || maxword < 1000) ;
            }
        }
        res[0] = minword;
        res[1] = maxword;
        return res;
    }
}
