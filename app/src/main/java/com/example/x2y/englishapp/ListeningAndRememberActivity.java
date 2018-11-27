package com.example.x2y.englishapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.Fragment.LARTestFragment;
import com.example.x2y.englishapp.Fragment.ListeningAndRememberFragment;
import com.example.x2y.englishapp.Util.GetUrl;
import com.example.x2y.englishapp.Util.HttpUtil;
import com.example.x2y.englishapp.bean.JavaBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.internal.Util;

import static com.example.x2y.englishapp.Util.GetUrl.hanndleWordResponse;

public class ListeningAndRememberActivity extends AppCompatActivity {

   //单词数
    public static int count1=10;
    //获取单词资源的次数
    public static int count2=0;
    //记录加载的第几个单词的记忆的fragment
    public int count3=0;
    //记录加载的第几个单词的题目的fragment
    public   int count4=0;
    //记录在LARTestfragment中四个选项的缓存，用于回到上一个fragemnt重新加载相同位置的选项；
    public List<List<JavaBean>> LARTestFragmentJavaBeanListTemp = new ArrayList<List<JavaBean>>();
    public   int[] checkedArray = new int[count1];
    public  List<JavaBean> javaBeanslist ;
    List<Fragment> mFragments ;
    private FragmentPagerAdapter mFragmentAdapter;
    ImageView buttonStart;
    public FloatingActionButton buttonNext;
    FloatingActionButton buttonPrevious;
    long lastClickTime=0;
    public  static JavaBean currentJavaBean;
    Chronometer timer;
    TextView StartView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_and_remember);
        timer=(Chronometer)findViewById(R.id.timer);
         mFragments = new ArrayList<Fragment>();
        javaBeanslist = new ArrayList<JavaBean>();
        StartView=(TextView)findViewById(R.id.statr_text);
        buttonStart=(ImageView) findViewById(R.id.start_button);
        buttonNext=(FloatingActionButton)findViewById(R.id.next_button) ;
        buttonPrevious=(FloatingActionButton)findViewById(R.id.previous_button);
        String[] words = {"hello", "good", "life","right","left","true","false","yes","text","is"};
        int length =words.length;
        ProgressBar bar = new ProgressBar(this);
        for (int i = 0; i < length; i++) {
            String url = GetUrl.getUrl(words[i]);
            requestForHttp(url);

        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(javaBeanslist.size()==count1)
               {
                   currentJavaBean=javaBeanslist.get(count3);
                   count3++;
                   replaceFragment(new ListeningAndRememberFragment());
                   timer.setBase(SystemClock.elapsedRealtime());
                   timer.start();
                   buttonStart.setVisibility(View.GONE);
                   buttonNext.setVisibility(View.VISIBLE);
                   buttonPrevious.setVisibility(View.VISIBLE);
                   StartView.setVisibility(View.GONE);
               }
               else {
                   Toast.makeText(ListeningAndRememberActivity.this,"未请求到网络数据，请确保网络正常连接。",Toast.LENGTH_SHORT).show();
               }

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long now = System.currentTimeMillis();
                if (now - lastClickTime > 200) {
                    lastClickTime = now;
                    if (count3 < count1) {

                        currentJavaBean = javaBeanslist.get(count3);
                        replaceFragment(new ListeningAndRememberFragment());
                        count3++;
                    } else if (count4 < count1) {
                        currentJavaBean = javaBeanslist.get(count4);
                        replaceFragmentLarFrament(new LARTestFragment());
                        count4++;

                    } else {
                        buttonNext.setClickable(false);
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(ListeningAndRememberActivity.this);
                        dialog.setTitle("提示");
                        boolean flag =false;
                        for(int i=0;i<checkedArray.length;i++)
                        {
                            if(checkedArray[i]==0)
                            {
                                dialog.setMessage("小主人，你还有题没做完呢，快去做吧");
                                flag=true;
                                dialog.setCancelable(false);
                                dialog.setNegativeButton("退出练习", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(ListeningAndRememberActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                dialog.setPositiveButton("继续练习", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                break;
                            }
                        }
                        if(flag==false)
                        {
                            String time = timer.getText().toString();
                            dialog.setMessage("你可真棒小主人你只花了"+time+"多少分钟就记忆完了");
                            dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ListeningAndRememberActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }


                        dialog.show();
                        buttonNext.setClickable(true);
                    }

                }
            }
        });


        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count3<=1)
                {
                    Toast.makeText(ListeningAndRememberActivity.this,"这是已经是第一个了哟，小主人",Toast.LENGTH_SHORT).show();
                }
                else if(count3<count1)
                {
                    count3=count3-2;
                    currentJavaBean=javaBeanslist.get(count3);
                    replaceFragment(new ListeningAndRememberFragment());
                    count3++;
                }
                else if(count3==count1&&count4==0)
                {
                    count3=count3-2;
                    currentJavaBean=javaBeanslist.get(count3);
                    replaceFragment(new ListeningAndRememberFragment());
                    count3++;

                }
                else if(count3==count1&&count4==1)
                {
                    count3--;
                    currentJavaBean=javaBeanslist.get(count3);
                    replaceFragment(new ListeningAndRememberFragment());
                    count3++;
                    count4--;

                }
                else
                {
                    if(count4==count1)
                    {
                        count4=count4-2;
                        currentJavaBean=javaBeanslist.get(count4);
                        replaceFragmentLarFrament(new LARTestFragment());
                        count4++;
                    }
                    else
                    {
                        count4=count4-2;
                        currentJavaBean=javaBeanslist.get(count4);
                        replaceFragmentLarFrament(new LARTestFragment());
                        count4++;

                    }

                }

            }
        });


    }
    private  void replaceFragmentLarFrament(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }
    private  void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }

    public void requestForHttp(String url) {

        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListeningAndRememberActivity.this, "未为请求到数据", Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final JavaBean javaBean = hanndleWordResponse(responseText);
                Context context = MyApplication.getContext();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        javaBeanslist.add(javaBean);
                        count2++;
                    }
                });

            }
        });
        ;
    }
    public static JavaBean hanndleWordResponse(String response) {


        return new Gson().fromJson(response,JavaBean.class);


    }

}
