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
    private int i = 10;
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
                cardlist.add(new CardMode(" ", "完成学习！请返回！"," "," ", 777, list.get(itemsInAdapter % imageUrls.length - 1)));
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
            "file:///android_asset/img/ability_picture.jpg",
            "file:///android_asset/img/abundance_picture.jpg",
            "file:///android_asset/img/butterfly_picture.jpg",
            "file:///android_asset/img/budget_picture.jpg",
            "file:///android_asset/img/carbon_picture.jpg",
            "file:///android_asset/img/centimeter_picture.jpg",
            "file:///android_asset/img/diamond_picture.jpg",
            "file:///android_asset/img/donation_picture.jpg",
            "file:///android_asset/img/eliminate_picture.jpg",
            "file:///android_asset/img/emotional_picture.jpg"
    };

    //在这里初始化单词卡片数据 加入20组数据测试
    private void initWord(List<CardMode> cardList){

            cardList.add(new CardMode("单词","ability","n. 能力，技能，本领,资格","The public never had faith in his ability to handle the job.",1,list.get(0)));
            cardList.add(new CardMode("单词","abundance","n. 大量；丰富 ;大量，极多"," There is an abundance of commodity supplies on the markets.",2,list.get(1)));
            cardList.add(new CardMode("单词","butterfly","n. 蝴蝶； 蝶泳;轻浮的人;","Butterfly is my favorite insect — its wings are so beautiful.",3,list.get(2)));
            cardList.add(new CardMode("单词","budget","n. 预算；v安排；adj.廉价的","The amount on the shopping list is over my budget.",4,list.get(3)));
            cardList.add(new CardMode("单词","carbon","n. 碳；复写纸；adj. 碳的","Coal burns so easily because it contains lots of carbon.",5,list.get(4)));
            cardList.add(new CardMode("单词","centimeter","n. 厘米，公分","Meter, centimeter and millimeter units used to measure length.",6,list.get(5)));
            cardList.add(new CardMode("单词","diamond","n. 钻石；方块牌；棒球内场","Diamond rings are very popular among young couples.",7,list.get(6)));
            cardList.add(new CardMode("单词","donation","n. 捐款，捐赠物；捐赠物","She made a donation to the earthquake-stricken area.",8,list.get(7)));
            cardList.add(new CardMode("单词","eliminate","v. 消除，根除；消灭，淘汰","This device will eliminate the weeds in my garden.",9,list.get(8)));
            cardList.add(new CardMode("单词","emotional","adj. 感情的，情绪的；沮丧的","Sometimes we are emotional; our mood changes from time to time.",10,list.get(9)));

    }
}
