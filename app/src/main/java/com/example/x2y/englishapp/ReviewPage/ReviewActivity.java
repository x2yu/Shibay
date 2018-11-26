package com.example.x2y.englishapp.ReviewPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.Adapter.MyFragAdpter;
import com.example.x2y.englishapp.Fragment.homeFragment;
import com.example.x2y.englishapp.Fragment.personFragment;
import com.example.x2y.englishapp.Fragment.reviewFragment;
import com.example.x2y.englishapp.MyApplication;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Word;

import java.util.ArrayList;
import java.util.List;

import static com.example.x2y.englishapp.MyApplication.getContext;

public class ReviewActivity extends AppCompatActivity {
    private ImageView reviewBack;
    private TextView word, means, finsih, surplus;
    private Button remember, toast, previous, next;
    /*
     *index用于标识textview中显示的当前单词在列表中的下标，count用于标识查询到的表中的单词总数量，在测试时
     * 将其设置为3，在实际使用时设置为0，在数据库查询代码正常情况下count的值会被更新为列表长度+1
     */
    private int index = 0, count = 0, max = getResources().getInteger(R.integer.review_max_wordlist), successCount = 0;
    //声明成功flag列表
    private boolean[] successFlag = new boolean[max];
    List<Fragment> listFragment;//存储页面对象

    //private MydatabaseHelper dbHelper; //数据库连接操作
    //定义类型为word的泛型列表，用于储存单词
    private List<Word> wordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        //获取资源文件中的布局
        remember = findViewById(R.id.review_remember);
        toast = findViewById(R.id.review_toast);
        previous = findViewById(R.id.review_previous);
        next = findViewById(R.id.review_next);
        word = findViewById(R.id.review_words);
        means = findViewById(R.id.review_means);
        finsih = findViewById(R.id.review_finish);
        surplus = findViewById(R.id.review_surplus);
        reviewBack = findViewById(R.id.review_back);
        //dbHelper = new MydatabaseHelper(this,"数据库名",null,1);
        //初始化单词列表，单词显示框，完成单词数，剩余单词数
        initWord();
        word.setText(wordList.get(index).getName());
        finsih.setText(getResources().getString(R.string.finsihed) + successCount);
        surplus.setText(getResources().getString(R.string.surplus) + (max - successCount));
        reviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //记住的点击监听事件，显示下一个单词
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化成功单词数
                successCount = 0;
                //当前单词成功flag置为true
                successFlag[index] = true;
                //判断当前单词是否为最后一个单词
                if (index == max - 1)
                    //最后一个单词，显示提示性文字
                    Toast.makeText(ReviewActivity.this, getResources().getText(R.string.next_toast), Toast.LENGTH_SHORT).show();
                else {
                    //不是最后一个单词，查询成功单词数显示成功数和剩余数，单词下标加一，显示下一个单词
                    index++;
                    word.setText(wordList.get(index).getName());
                    means.setText(null);
                }
                for (int i = 0; i <= index; i++) {
                    if (successFlag[i]) successCount++;
                }
                finsih.setText(getResources().getString(R.string.finsihed) + successCount);
                surplus.setText(getResources().getString(R.string.surplus) + (max - successCount));
                if (successCount == max) {
                    Toast.makeText(ReviewActivity.this, getResources().getString(R.string.review_finish_toast), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        //忘记的点击监听事件，显示当前单词的意思
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                means.setText(wordList.get(index).getMean());
            }
        });
        //上一个单词的点击监听事件
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0)//如果没有上一个单词显示toast，否则显示上一个单词和意思
                    Toast.makeText(ReviewActivity.this, getResources().getText(R.string.previous_toast), Toast.LENGTH_SHORT).show();
                else {
                    /*
                    当前单词不是第一个单词，单词下标减一，显示上一个单词，如果上一个单词已背则显示单词和
                    意思，如果上一个单词没有背，则只显示上一个单词而不显示意思
                     */
                    index--;
                    word.setText(wordList.get(index).getName());
                    if (successFlag[index])
                        means.setText(wordList.get(index).getMean());
                    else {
                        means.setText(null);
                    }
                }
            }
        });
        //下一个按钮的点击监听事件
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == max - 1)//如果没有下一个单词显示toast，否则显示下一个单词
                    Toast.makeText(ReviewActivity.this, getResources().getText(R.string.next_toast), Toast.LENGTH_SHORT).show();
                else {
                    /*
                    当前单词不是最后一个单词，单词下标加一，显示下一个单词，如果下一个单词已背则显示单词和
                    意思，如果没有背则只显示单词
                     */
                    index++;
                    word.setText(wordList.get(index).getName());
                    if (successFlag[index]) means.setText(wordList.get(index).getMean());
                    else {
                        means.setText(null);
                    }
                }
            }
        });
    }

    /*
     * 这里是单词列表初始化的实现部分，其中注释了向数据库查询数据并装入泛型列表的代码，其中包含了部分测试
     * 代码，在完成了数据库查询数据之后应将其删除
     */
    private void initWord() {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        //查询已背单词表
//        Cursor cursor = db.query("表名",null,null,null,null,null,null);
//        if(cursor.moveToFirst()){
//            do {
//                    //遍历取出对象
//                    String name = cursor.getString(cursor.getColumnIndex("name"));
//                    String mean = cursor.getString(cursor.getColumnIndex("mean"));
//                    Word word=new Word(name,mean);
//                    wordList.add(word);
//                    //初始化成功标志列表为false
//                    successFlag[count]=false;
//                    count++;
//                    if(count==max-1)break;
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
        //测试用单词，在数据库查询数据实现之后应将其删除或者注释
        Word apple = new Word("apple", "n.苹果");
        Word orange = new Word("orange", "n.橙子");
        Word banana = new Word("banana", "n.香蕉");
        Word peer = new Word("peer", "n.梨子");
        wordList.add(apple);
        wordList.add(orange);
        wordList.add(banana);
        wordList.add(peer);
        for (int i = 0; i < max; i++) {
            successFlag[i] = false;
        }
    }
}
