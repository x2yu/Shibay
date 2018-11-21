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
    private TextView word,means;
    private Button remember,forget,previous,next;
    List<Fragment>listFragment;//存储页面对象
    /*
    *index用于标识textview中显示的当前单词在列表中的下标，count用于标识查询到的表中的单词总数量，在测试时
    * 将其设置为3，在实际使用时设置为0，在数据库查询代码正常情况下count的值会被更新为列表长度+1
     */
    private int index=0,count=3;
    //private MydatabaseHelper dbHelper; //数据库连接操作
    //定义类型为word的泛型列表，用于储存单词
    private List<Word> wordList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        //获取资源文件中的布局
        remember=(Button)findViewById(R.id.review_remember);
        forget=(Button)findViewById(R.id.review_forget);
        previous=(Button)findViewById(R.id.review_previous);
        next=(Button)findViewById(R.id.review_next);
        word=(TextView)findViewById(R.id.review_words);
        means=(TextView)findViewById(R.id.review_means);
        reviewBack=(ImageView)findViewById(R.id.review_back);
        //dbHelper = new MydatabaseHelper(this,"数据库名",null,1);
        initWord();
        word.setText(wordList.get(index).getName());

        reviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                /*
                Intent backIntent=new Intent(ReviewActivity.this,MainActivity.class);
                startActivity(backIntent);*/
            }
        });

        //记住的点击监听事件，显示下一个单词
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==count-1)
                    Toast.makeText(ReviewActivity.this,getResources().getText(R.string.next_toast),Toast.LENGTH_SHORT).show();
                else{
                    index++;
                    word.setText(wordList.get(index).getName());
                }
            }
        });
        //忘记的点击监听事件，显示当前单词的意思
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                means.setText(wordList.get(index).getMean());
            }
        });
        //上一个单词的点击监听事件
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==0)//如果没有上一个单词显示toast，否则显示上一个单词和意思
                    Toast.makeText(ReviewActivity.this,getResources().getText(R.string.previous_toast),Toast.LENGTH_SHORT).show();
                else{
                    index--;
                    word.setText(wordList.get(index).getName());
                    means.setText(wordList.get(index).getMean());
                }
            }
        });
        //下一个按钮的点击监听事件
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==count-1)//如果没有下一个单词显示toast，否则显示下一个单词
                    Toast.makeText(ReviewActivity.this,getResources().getText(R.string.next_toast),Toast.LENGTH_SHORT).show();
                else{
                    index++;
                    word.setText(wordList.get(index).getName());
                }
            }
        });
    }
    /*
     * 这里是单词列表初始化的实现部分，其中注释了向数据库查询数据并装入泛型列表的代码，其中包含了部分测试
     * 代码，在完成了数据库查询数据之后应将其删除
     */
    private void initWord(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询已背单词表
//        Cursor cursor = db.query("表名",null,null,null,null,null,null);
//        if(cursor.moveToFirst()){
//            do {
//                    //遍历取出对象
//                    String name = cursor.getString(cursor.getColumnIndex("name"));
//                    String mean = cursor.getString(cursor.getColumnIndex("mean"));
//                    Word word=new Word(name,mean);
//                    wordList.add(word);
//                    count++;
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
        //测试用单词，在数据库查询数据实现之后应将其删除或者注释
        Word apple=new Word("apple","n.苹果");
        Word orange=new Word("orange","n.橙子");
        Word banana=new Word("banana","n.香蕉");
        Word peer=new Word("peer","n.梨子");
        wordList.add(apple);
        wordList.add(orange);
        wordList.add(banana);
        wordList.add(peer);
    }
}
