package com.example.x2y.englishapp.ReviewPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChooseInChineseActivity extends AppCompatActivity {
    private TextView finished, surplus, mean, rightWord;
    private Button wordA, wordB, wordC, wordD;
    private ImageView back;
    //用于获取单词列表的泛型数组，类型为Word
    private List<Word> wordList = new ArrayList<>();
    //index用于标识当前单词下标，count用于在初始化数组中计数，successCount用于标识完成的单词数量，max用于确定一次复习的单词数目
    private int index = 0, count = 0, successCount = 0, max = getResources().getInteger(R.integer.cic_max_wordlist);
    //用于标识每一个单词是否已经记得
    private boolean[] rightFlag = new boolean[max];
    //用于标识每一个单词的选择失败次数
    private int[] failCount = new int[max];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_in_chinese);

        //获取布局文件中的资源
        finished = findViewById(R.id.cic_finished);
        surplus = findViewById(R.id.cic_surplus);
        mean = findViewById(R.id.cic_mean);
        rightWord = findViewById(R.id.cic_right_word);
        wordA = findViewById(R.id.cic_word_A);
        wordB = findViewById(R.id.cic_word_B);
        wordC = findViewById(R.id.cic_word_C);
        wordD = findViewById(R.id.cic_word_D);
        back = findViewById(R.id.cic_back);

        //初始化单词列表，单词显示框，单词选项，已完成和剩余单词数
        initWord();
        mean.setText(wordList.get(index).getMean());
        loadOptions(index, max);
        finished.setText(getResources().getString(R.string.finsihed) + successCount);
        surplus.setText(getResources().getString(R.string.surplus) + (max - successCount));

        //返回按钮的点击监听事件，销毁当前活动，返回复习碎片
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //四个选项的点击监听事件
        wordA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordJudge(wordA.getText().toString());
            }
        });
        wordB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordJudge(wordB.getText().toString());
            }
        });
        wordC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordJudge(wordC.getText().toString());
            }
        });
        wordD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordJudge(wordD.getText().toString());
            }
        });
    }

    /*
    初始化单词列表
     */
    private void initWord() {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        //其中的表名应为已背单词列表
//        Cursor cursor = db.query("表名", null, null, null, null, null, null);
//        Random random = new Random();
//        if (cursor.moveToFirst()) {
//            do {
//                //遍历取出对象
//                String name = cursor.getString(cursor.getColumnIndex("name"));
//                String mean = cursor.getString(cursor.getColumnIndex("mean"));
//                Word word = new Word(name, mean);
//                wordList.add(word);
//                //初始化单词拼写flag列表为false
//                rightFlag[count] = false;
//                failCount[count]=0;
//                count++;
//                if (count == max - 1) break;
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
        //测试用单词，在数据库查询数据实现之后应将其删除或者注释
        Word apple = new Word("apple", "n.苹果");
        Word orange = new Word("orange", "n.橙子");
        Word banana = new Word("banana", "n.香蕉");
        Word peer = new Word("peer", "n.梨子");
        Word android = new Word("android", "安卓");
        Word review = new Word("review", "复习");
        Word chinese = new Word("chinese", "中文");
        Word test = new Word("test", "测试");
        wordList.add(apple);
        wordList.add(orange);
        wordList.add(banana);
        wordList.add(peer);
        wordList.add(android);
        wordList.add(review);
        wordList.add(chinese);
        wordList.add(test);
        for (int i = 0; i < max; i++) {
            rightFlag[i] = false;
            failCount[i] = 0;
        }
    }

    //装载单词选项的具体实现
    private void loadOptions(int index, int max) {
        //定义四个选项对应的index的数组，并初始化为-1
        int[] randomList = {-1, -1, -1, -1};
        //定义随机数
        int ra;
        //定义index重复的标志，以及是否有对应index的标志
        boolean sameFlag = false, indexFlag = false;
        //生成随机数和检查的循环，循环条件为生成的index列表中有重复或没有对应的下标
        while (sameFlag || !indexFlag) {
            //初始化标志都为false
            sameFlag = false;
            indexFlag = false;
            //生成随机数
            for (int i = 0; i < 4; i++) {
                randomList[i] = (int) (Math.random() * max);
            }
            //检查随机数列表中是否有重复的index
            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j < 4; j++) {
                    if (randomList[i] == randomList[j]) {
                        sameFlag = true;
                        break;
                    }
                }
                if (sameFlag) break;
            }
            //检查随机数列表中是否有对应的index
            for (int i = 0; i < 4; i++) {
                if (randomList[i] == index) {
                    indexFlag = true;
                    break;
                }
            }
        }
        //使用合格的index列表进行单词选项的装载
        wordA.setText(wordList.get(randomList[0]).getName());
        wordB.setText(wordList.get(randomList[1]).getName());
        wordC.setText(wordList.get(randomList[2]).getName());
        wordD.setText(wordList.get(randomList[3]).getName());
    }

    //选项监听事件中对错判断的具体实现
    private void wordJudge(String word) {
        //初始化成功数为0
        successCount = 0;
        //判定当前选项中的单词与对应index的wordList中的单词相同则认为选择了正确的选项
        if (word.equals(wordList.get(index).getName())) {
            //将当前单词的成功标志置为true
            rightFlag[index] = true;
            //统计单词正确数，并对完成单词数和剩余单词数进行更新
            for (int i = 0; i < max; i++) {
                if (rightFlag[i]) successCount++;
            }
            finished.setText(getResources().getString(R.string.finsihed) + successCount);
            surplus.setText(getResources().getString(R.string.surplus) + (max - successCount));
            //判断当前单词是否为最后一个单词，如果是最后一个单词则显示对应的toast
            if (index == max - 1) {
                Toast.makeText(ChooseInChineseActivity.this, getResources().getString(R.string.review_finish_toast), Toast.LENGTH_SHORT).show();
            }
            /*
            当前单词不是最后一个单词，显示对应toast，并且将单词下标加一，显示下一个单词，并且将当前单词的
            正确单词显示框清空，并且调用选项转载方法显示下一组单词选项
             */
            else {
                Toast.makeText(ChooseInChineseActivity.this, getResources().getString(R.string.cic_choose_right), Toast.LENGTH_SHORT).show();
                index++;
                mean.setText(wordList.get(index).getMean());
                rightWord.setText(null);
                loadOptions(index, max);
            }
        }
        //单词选择错误
        else {
            //当前单词的错误次数加一
            failCount[index]++;
            //判断当前单词错误次数，达到三次之后显示正确单词，未达到三次显示对应toast
            if (failCount[index] == 3) {
                rightWord.setText(wordList.get(index).getName());
            } else {
                Toast.makeText(ChooseInChineseActivity.this, getResources().getString(R.string.cic_choose_worng), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
