package com.example.x2y.englishapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.Util.GetUrl;
import com.example.x2y.englishapp.Util.HttpUtil;
import com.example.x2y.englishapp.bean.JavaBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListeningAndRememberActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_and_remember);
        textView = (TextView) findViewById(R.id.word_infor) ;
        String word ="hello";
        String url= GetUrl.getUrl(word);
        requestForHttp(url);

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
    public void show(JavaBean javaBean)
    {
        javaBean.save();
        List<JavaBean> j = DataSupport.where("query=hello").find(JavaBean.class);
        //textView.setText(javaBean.getErrorCode().toString()+"\n"+javaBean.getQuery()+"\n"+javaBean.getBasic().getExplains().get(0));
        textView.setText(j.get(0).getBasic().getExplains().get(0));
        j.get(0).
    }
    public static JavaBean hanndleWordResponse(String response) {
//        try {
//            //JSONObject jsonObject = new JSONObject(response);
//            //JSONArray jsonArray = jsonObject.getJSONArray("JavaBean");
//            //String wordContent =jsonArray.getJSONObject(0).toString();
     return new Gson().fromJson(response,JavaBean.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;

    }
}
