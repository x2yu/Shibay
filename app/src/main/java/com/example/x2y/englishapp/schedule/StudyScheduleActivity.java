package com.example.x2y.englishapp.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.R;

import java.util.ArrayList;

/**
 * author:桂贞林
 * function:英语学习计划的制定
 */
public class StudyScheduleActivity extends AppCompatActivity {

    private ImageView scheduleBarBack = null;
    private TextView scheduleBarEdit = null;
    private TextView scheduleBarEnsure = null;
    private Button bookshelfAdd = null;
    private RecyclerView bookshelfShow = null;
    private TextView bookName = null;
    private TextView bookContentCount = null;
    private TextView bookLearnedCount = null;
    private TextView bookSchedule = null;
    private RecyclerView scheduleModify = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_schedule_layout);

        initial();
    }

    /**
     * 初始化成员变量
     */
    private void initial(){


        initialBookshelf();
        initialScheduleModify();
        initialBar();
        /*setSupportActionBar(scheduleBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.schedule_back);
        actionBar.seth*/
    }

    //初始化工具栏schedule_bar
    private void initialBar(){
        scheduleBarBack = (ImageView) findViewById(R.id.schedule_bar_back);
        scheduleBarEdit = (TextView)findViewById(R.id.schedule_bar_edit) ;
        scheduleBarEnsure = (TextView)findViewById(R.id.schedule_bar_ensure) ;

        //设置工具栏的监听事件
        scheduleBarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ScheduleDataManager.clearDate();
                finish();
            }
        });
        scheduleBarEdit.setOnClickListener(ScheduleDataManager.instance().getBookshelf());
        scheduleBarEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().saveData();
                finish();
            }
        });
    }

    //初始化书架schedule_bookshelf
    private void initialBookshelf(){
        Bookshelf bookshelf = new Bookshelf();
        ScheduleDataManager.instance().setBookshelf(bookshelf);

        bookshelfAdd = (Button) findViewById(R.id.schedule_bookshelf_add);
        bookshelfShow = (RecyclerView) findViewById(R.id.schedule_bookshelf_show);
        bookName = (TextView) findViewById(R.id.schedule_book_name);
        bookContentCount = (TextView) findViewById(R.id.schedule_book_contentCount);
        bookLearnedCount = (TextView) findViewById(R.id.schedule_book_learnedCount);
        bookSchedule = (TextView) findViewById(R.id.schedule_book_shedule);

        BookshelfAdapter bookshelfAdapter = new BookshelfAdapter(this, bookshelf);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bookshelfShow.setLayoutManager(linearLayoutManager);
        bookshelfShow.setAdapter(bookshelfAdapter);

        bookshelf.setBookshelfAdapter(bookshelfAdapter);
        bookshelf.setBookContentShowControls(bookName, bookContentCount, bookLearnedCount, bookSchedule);
        bookshelf.showBookInformation(0);

        bookshelfAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyScheduleActivity.this, StudyScheduleBookLibraryActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化计划修改栏
    public void initialScheduleModify(){
        scheduleModify = (RecyclerView) findViewById(R.id.schedule_modify);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scheduleModify.setLayoutManager(linearLayoutManager);


        ScheduleModify scheduleModifyData = new ScheduleModify(this, scheduleModify);
        ScheduleDataManager.instance().setScheduleModify(scheduleModifyData);
        scheduleModifyData.setBook(ScheduleDataManager.instance().getBookshelf().getBookByOrder(0));
        scheduleModifyData.setScheduleDateTextView((TextView)findViewById(R.id.schedule_modify_date));
    }





    public static void newInstance(Context context, Bundle bundle){
        Intent intent = new Intent(context, StudyScheduleActivity.class);
        context.startActivity(intent);
    }
}
