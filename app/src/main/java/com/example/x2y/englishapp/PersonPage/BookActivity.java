package com.example.x2y.englishapp.PersonPage;

//词典里面的具体内容
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.x2y.englishapp.R;

import org.apache.commons.lang3.ArrayUtils;
import com.show.api.ShowApiRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_NAME="book_name";
    public static final String BOOK_IMAGE_ID="book_image_id";
    protected Handler mHandler = new Handler();

    final String data[]=new String[20];
    String dataAll[]=new String[0];

    //数据来源，这个地方应该改成数据库的来源，根据传入的词典名字（BOOK_NAME）查询数据库

    private String[] datao={"ooop","ddd","dddd","gded","dfds","sdfs"
            ,"sf","dsfd","sdfsd","kjh","yuity","uytu"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent=getIntent();
        String bookName=intent.getStringExtra(BOOK_NAME);
        int bookImageId=intent.getIntExtra(BOOK_IMAGE_ID,0);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)findViewById(R.id.collasing_toolbar);
        ImageView bookImageView=(ImageView)findViewById(R.id.book_image_view);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(bookName);
        Glide.with(this).load(bookImageId).into(bookImageView);




      final   ListView bookContentText=(ListView)findViewById(R.id.book_content_text);
if(bookName.equals("四级词汇")){
    new Thread() {
        //在新线程中发送网络请求
        public void run() {
            //  j为请求的次数（1-226），每次请求就会得到20个
            for(int j=1;j<=3;j++) {
                String appid = "81452";//要替换成自己的
                String secret = "7a70115f47e1405e9644b274c7af6d7f";//要替换成自己的
                final String res;
                res = new ShowApiRequest("http://route.showapi.com/8-10", appid, secret)
                        .addTextPara("class_id", "45090")
                        .addTextPara("course", Integer.toString(j))
                        .post();

                //System.out.println(res);
//把返回内容通过handler对象更新到界面
//                        Message
                JSONObject jsStr = JSONObject.parseObject(res);
                final JSONObject listJ = JSONObject.parseObject(jsStr.getString("showapi_res_body"));
                final JSONArray listJ2 = JSONArray.parseArray(listJ.getString("list")); //.parseObject(listJ.getString("list"));
               // System.out.println(listJ2);
                for (int i = 0; i < listJ2.size(); i++) {
                    JSONObject jsonObject = (JSONObject) listJ2.get(i);
                   // System.out.println(i + jsonObject.getString("name"));
                    data[i] = jsonObject.getString("name");
                                    /*TextView txt=(TextView)findViewById(R.id.textview1);
                                    txt.setText(i + jsonObject.getString("name"));*/
                }
                dataAll=(String[])ArrayUtils.addAll(dataAll,data);
            }
            System.out.println(dataAll[50]);
            mHandler.post(new Thread() {
                public void run() {

                    ArrayAdapter<String> madapter=new ArrayAdapter<String>(BookActivity.this,android.R.layout.simple_list_item_1,dataAll);
                    bookContentText.setAdapter(madapter);
                    setListViewHeightBasedOnChildren(bookContentText);
                }
            });
        }
    }.start();


}
    else {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                BookActivity.this,android.R.layout.simple_list_item_1,datao);
        bookContentText.setAdapter(adapter);
}
        setListViewHeightBasedOnChildren(bookContentText);

    }



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


}
