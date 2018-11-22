package com.example.x2y.englishapp.schedule;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.R;

public class StudyScheduleBookLibraryActivity extends AppCompatActivity {
    public static final int permissionRequestCode = 1;

    private ImageView scheduleBarBack = null;
    private TextView scheduleNewHot = null;
    private TextView scheduleNewPrimarySchool = null;
    private TextView scheduleNewMiddleSchool = null;
    private TextView scheduleNewCollege = null;
    private TextView scheduleNewOthers = null;
    private RecyclerView scheduleNewSelected = null;

    private TextView selectedTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_schedule_new_layout);


        initial();
    }




    private void initial(){
        BookLibrary bookLibrary = new BookLibrary(this);
        ScheduleDataManager.instance().setBookLibrary(bookLibrary);

        initialTitleBar();
        initialNavigation();
        initialBookLibrary();
    }

    private void initialTitleBar(){
        scheduleBarBack = (ImageView)findViewById(R.id.schedule_bar_back);
        scheduleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().getBookLibrary().saveDate();
                finish();
            }
        });
    }

    private void initialNavigation(){
        scheduleNewHot = (TextView)findViewById(R.id.schedule_new_hot);
        scheduleNewPrimarySchool = (TextView)findViewById(R.id.schedule_new_primarySchool);
        scheduleNewMiddleSchool = (TextView)findViewById(R.id.schedule_new_middleSchool);
        scheduleNewCollege = (TextView)findViewById(R.id.schedule_new_college);
        scheduleNewOthers = (TextView)findViewById(R.id.schedule_new_others);

        selectedTextView = scheduleNewHot;
        selectedTextView.setBackgroundColor(Color.parseColor("#FE4D64"));
        scheduleNewHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().getBookLibrary().changeAdapter(BookLibrary.HOT);
                if(selectedTextView != scheduleNewHot){
                    selectedTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    selectedTextView = scheduleNewHot;
                    selectedTextView.setBackgroundColor(Color.parseColor("#FE4D64"));
                }
            }
        });

        scheduleNewPrimarySchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().getBookLibrary().changeAdapter(BookLibrary.PRIMARY_SCHOOL);
                if(selectedTextView != scheduleNewPrimarySchool){
                    selectedTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    selectedTextView = scheduleNewPrimarySchool;
                    selectedTextView.setBackgroundColor(Color.parseColor("#FE4D64"));
                }
            }
        });
        scheduleNewMiddleSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().getBookLibrary().changeAdapter(BookLibrary.MIDDLE_SCHOOL);
                if(selectedTextView != scheduleNewMiddleSchool){
                    selectedTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    selectedTextView = scheduleNewMiddleSchool;
                    selectedTextView.setBackgroundColor(Color.parseColor("#FE4D64"));
                }
            }
        });
        scheduleNewCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().getBookLibrary().changeAdapter(BookLibrary.COLLEGE);
                if(selectedTextView != scheduleNewCollege){
                    selectedTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    selectedTextView = scheduleNewCollege;
                    selectedTextView.setBackgroundColor(Color.parseColor("#FE4D64"));
                }
            }
        });
        scheduleNewOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDataManager.instance().getBookLibrary().changeAdapter(BookLibrary.OTHERS);
                if(selectedTextView != scheduleNewOthers){
                    selectedTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    selectedTextView = scheduleNewOthers;
                    selectedTextView.setBackgroundColor(Color.parseColor("#FE4D64"));
                }
            }
        });

    }

    private void initialBookLibrary(){
        scheduleNewSelected = (RecyclerView)findViewById(R.id.schedule_new_selected);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        scheduleNewSelected.setLayoutManager(gridLayoutManager);

        ScheduleDataManager.instance().getBookLibrary().setRecyclerView(scheduleNewSelected);
        ScheduleDataManager.instance().getBookLibrary().changeAdapter(BookLibrary.HOT);
    }


    /**
     * 获取网络连接权限
     */
    public void requestPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, permissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case permissionRequestCode:
                if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    /**
                     *
                     */
                }else{
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT);
                }
        }
    }

}
