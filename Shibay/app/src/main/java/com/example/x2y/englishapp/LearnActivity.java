package com.example.x2y.englishapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.x2y.englishapp.Adapter.CardAdapter;
import com.example.x2y.englishapp.bean.CardMode;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {
    private ArrayList<CardMode> cardlist;
    private CardAdapter adapter;
    private int i;
    private SwipeFlingAdapterView flingContainer;
    private List<List<String>> list = new ArrayList<>();
    private ImageView left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        View backview = (View)findViewById(R.id.word_learn_back);
        backview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initLearnCard();

    }


    //初始化学习单词卡片
    private void initLearnCard(){
        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);
        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
        cardlist = new ArrayList<>();

        for (int i = 0; i < imageUrls.length; i++) {
            List<String> s = new ArrayList<>();
            s.add(imageUrls[i]);
            list.add(s);
        }
        List<String> yi;

        //初始化卡片数据数组
        initWord(cardlist);
        // cardlist.add(new CardMode("hu", "Apple","","",21, list.get(0)));

        adapter = new CardAdapter(this, cardlist);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.SwipFrame);
        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

                //最后因该先储存在移除
                cardlist.remove(0);
                adapter.notifyDataSetChanged();
            }

            //判定经过两边边界时执行的操作
            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(LearnActivity.this, "记住了");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //  makeToast(MainActivity.this, "没记住");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                cardlist.add(new CardMode("循环测试", "Lemon","柠檬","我请你吃柠檬吧",18, list.get(itemsInAdapter % imageUrls.length - 1)));
                adapter.notifyDataSetChanged();
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = flingContainer.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(LearnActivity.this, "点击图片");
            }
        });


    }

    //设置Toast方法方便调用
    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    //右边按键
    private void right() {
        makeToast(LearnActivity.this, "没记住");
        flingContainer.getTopCardListener().selectRight();
    }

    //左边按键
    private void left() {
        flingContainer.getTopCardListener().selectLeft();
    }


    private final String[] imageUrls = new String[]{
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3378004326,2233636958&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119948043,3375194617&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3164029131,1240243468&fm=26&gp=0.jpg",};

    //在这里初始化单词卡片数据 加入50组数据测试
    private void initWord(List<CardMode> cardList){
        for(int i = 0;i<50;i++){
            cardList.add(new CardMode("单词"+i,"Apple","苹果"+i,"这是苹果啊苹果苹果苹果",21,list.get(i)));
        }
    }
}
