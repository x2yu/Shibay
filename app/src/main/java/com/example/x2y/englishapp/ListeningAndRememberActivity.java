package com.example.x2y.englishapp;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    public static int count1;
    //获取单词资源的次数
    public static int count2;
    public int count3=0;
    ViewPager viewPagerFragment;
   public static List<JavaBean> javaBeanslist ;
    List<Fragment> mFragments ;
    private FragmentPagerAdapter mFragmentAdapter;
    Button buttonStart;
    Button buttonNext;
    Button buttonPrevious;
    public  static JavaBean currentJavaBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_and_remember);
         mFragments = new ArrayList<Fragment>();
        javaBeanslist = new ArrayList<JavaBean>();
        String[] words = {"hello", "good", "life","right","left","true","false","yes","text","is"};
        int length =words.length;
        count2=0;
        count1=length;
        for (int i = 0; i < length; i++) {
            String url = GetUrl.getUrl(words[i]);
            requestForHttp(url);

        }

        buttonStart=(Button)findViewById(R.id.start_button);
        buttonNext=(Button)findViewById(R.id.next_button) ;
        buttonPrevious=(Button)findViewById(R.id.previous_button);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(count3<count1)
                    button.setText("下一个");
                else if(count3==10)
                {
                    button.setText("下一个阶段");
                }
                else
                {
                    button.setText("下一个");
                }*/
                replaceFragment(new ListeningAndRememberFragment());
                currentJavaBean=javaBeanslist.get(count3++);
                buttonStart.setVisibility(View.GONE);
                buttonNext.setVisibility(View.VISIBLE);
                buttonPrevious.setVisibility(View.VISIBLE);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ListeningAndRememberFragment());
                currentJavaBean=javaBeanslist.get(count3++);

            }
        });
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentJavaBean=javaBeanslist.get(count3--);
                replaceFragment(new ListeningAndRememberFragment());

            }
        });


    }
    private  void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }

   /* public void showViewPager() {
        mFragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public  Fragment getItem(int position) {
                return  mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        viewPagerFragment.setAdapter(mFragmentAdapter);
        viewPagerFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void init() {
        viewPagerFragment = (ViewPager) findViewById(R.id.fragment_word);

        for (count3=0; count3 <count1; count3 ++) {
            mFragments.add(new ListeningAndRememberFragment());
        }


    }*/

    public void requestForHttp(String url) {

        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(), "未为请求到数据", Toast.LENGTH_SHORT).show();

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
                        if(count2==count1)
                        {
                            currentJavaBean=javaBeanslist.get(count3);
                        }
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
   /* TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_and_remember);
        textView = (TextView) findViewById(R.id.word_infor) ;
        String word ="hello";
        String url= GetUrl.getUrl(word);
        requestForHttp(url);
        button = (Button)findViewById(R.id.player);


    }
    public void requestForHttp(String url) {

        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),"未为请求到数据",Toast.LENGTH_SHORT).show();
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
                        show(javaBean);
                    }
                });

            }
        });
        ;
    }
    public void show(final JavaBean javaBean)
    {
        javaBean.save();
//        List<JavaBean> j = DataSupport.where("query=hello").find(JavaBean.class);
        textView.setText(javaBean.getErrorCode().toString()+"\n"+javaBean.getQuery()+"\n"+javaBean.getBasic().getExplains().get(0));
//        textView.setText(j.get(0).getBasic().getExplains().get(0));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    MediaPlayer  mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(ListeningAndRememberActivity.this,Uri.parse(javaBean.getSpeakUrl()));
                    Toast.makeText(ListeningAndRememberActivity.this,"test",Toast.LENGTH_SHORT).show();
                    mediaPlayer.prepare();
                    Toast.makeText(ListeningAndRememberActivity.this,"test",Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

*/