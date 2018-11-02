package com.example.x2y.englishapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import com.example.x2y.englishapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchActivity extends AppCompatActivity {
    //搜索框元素
    private TextView mSearchBGTxt;
    private EditText mHintTxt;
    private TextView mSearchTxt;
    private TextView mSearchContent;
    private FrameLayout mContentFrame;
    private ImageView mArrowImg;
    private boolean finishing;
    private float originX;

    //实时搜索
    private String YouDaoBaseUrl = "http://openapi.youdao.com/api";
    private String YouDaoFrom = "En";
    private String YouDaoTo = "zh_CHS";
    private String YouDaoappKey = "530a6449cf2e8dc3";
    private String YouDaoSecretKey = "cns95Vxm9V7qFnxRLLzCUczUE56SD9mh";
    private String YouDaoSalt = "1";
    private String YouDaoSign ;

    private TranslateHandler handler;

    private static final int SUCCEE_RESULT = 200;
    private static final int ERROR_TEXT_TOO_LONG = 103;
    private static final int ERROR_CANNOT_TRANSLATE = 107;
    private static final int ERROR_UNSUPPORT_LANGUAGE = 102;
    private static final int ERROR_WRONG_KEY = 108;
    private static final int ERROR_WRONG_RESULT = 60;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        initView();
        execute();
    }

    private void initView() {
        mSearchBGTxt = (TextView) findViewById(R.id.tv_search_bg);
        mHintTxt = (EditText) findViewById(R.id.tv_hint);
        mContentFrame = (FrameLayout) findViewById(R.id.frame_content_bg);
        mSearchTxt = (TextView) findViewById(R.id.tv_search);
        mArrowImg = (ImageView) findViewById(R.id.iv_arrow);
        mSearchContent = (TextView)findViewById(R.id.tv_serachContent);

        mSearchTxt.setOnClickListener(new searchListener());//设置搜索tv的动作监听
        handler = new TranslateHandler(this,mSearchContent);

        mArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//调用返回键功能
            }
        });

    }




    //hander处理者
    private class TranslateHandler extends Handler {
        private Context mContext;
        private TextView mTextview;

        public TranslateHandler(Context context,TextView textView){
            this.mContext = context;
            this.mTextview = textView;
        }
        //处理传递过来的msg,匹配相应操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCEE_RESULT:
                    mSearchContent.setText((String)msg.obj);
                    closeInput();
                    break;
                case ERROR_TEXT_TOO_LONG:
                    Toast.makeText(mContext, "要翻译的文本过长", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_CANNOT_TRANSLATE:
                    Toast.makeText(mContext, "无法进行有效的翻译", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_UNSUPPORT_LANGUAGE:
                    Toast.makeText(mContext, "不支持的语言类型", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_WRONG_KEY:
                    Toast.makeText(mContext, "无效的key", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_WRONG_RESULT:
                    Toast.makeText(mContext, "提取异常", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    mSearchContent.setText("未查到内容！");
                    break;
            }
            super.handleMessage(msg);
        }
    }
    //关闭文本输入
    public void closeInput(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if((inputMethodManager != null)&&(this.getCurrentFocus()!=null)){
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),inputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //设置搜索动作监听
    private class searchListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String content = mHintTxt.getText().toString().trim();
            if (content == null || "".equals(content)) {
                Toast.makeText(getApplicationContext(), "请输入要翻译的内容", Toast.LENGTH_SHORT).show();
                return;
            }

            //获取签名码
            YouDaoSign = md5(YouDaoappKey+content+YouDaoSalt+YouDaoSecretKey);

            //拼接链接
            final String YouDaoUrl = YouDaoBaseUrl + "?q=" + content + "&from=" + YouDaoFrom + "&to=" + YouDaoTo + "&appKey=" + YouDaoappKey
                    + "&salt=" + YouDaoSalt + "&sign=" + YouDaoSign;

            Log.d("请求连接：", YouDaoUrl);

            new Thread() {
                public void run() {
                    try {
                        AnalyzingOfJson(YouDaoUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
    }

    private void AnalyzingOfJson(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        if (response!=null) {
            // 第三步，使用getEntity方法活得返回结果
            String result = response.body().string();
            System.out.println("result:" + result);
            JSONArray jsonArray = new JSONArray("[" + result + "]");
            String message = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    String errorCode = jsonObject.getString("errorCode");
                    if (errorCode.equals("103")) {
                        handler.sendEmptyMessage(ERROR_TEXT_TOO_LONG);
                    } else if (errorCode.equals("107")) {
                        handler.sendEmptyMessage(ERROR_CANNOT_TRANSLATE);
                    } else if (errorCode.equals("102")) {
                        handler.sendEmptyMessage(ERROR_UNSUPPORT_LANGUAGE);
                    } else if (errorCode.equals("108")) {
                        handler.sendEmptyMessage(ERROR_WRONG_KEY);
                    } else {
                        Message msg = new Message();
                        msg.what = SUCCEE_RESULT;
                        // 要翻译的内容
                        String query = jsonObject.getString("query");
                        message = "翻译结果：";
                        // 翻译内容
                        Gson gson = new Gson();
                        Type lt = new TypeToken<String[]>() {
                        }.getType();
                        String[] translations = gson.fromJson(jsonObject.getString("translation"), lt);
                        for (String translation : translations) {
                            message += "\t" + translation;
                        }
                        // 有道词典-基本词典
                        if (jsonObject.has("basic")) {
                            JSONObject basic = jsonObject.getJSONObject("basic");
                            if (basic.has("phonetic")) {
                                String phonetic = basic.getString("phonetic");
                                message += "\n" +"发音："+ phonetic;
                            }
                            if (basic.has("explains")) {
                                String explains = basic.getString("explains");
                                 message += "\n"+"释义：" + explains;
                            }
                        }
                        // 有道词典-网络释义
                        if (jsonObject.has("web")) {
                            String web = jsonObject.getString("web");
                            JSONArray webString = new JSONArray("[" + web + "]");
                            message += "\n网络释义：";
                            JSONArray webArray = webString.getJSONArray(0);
                            int count = 0;
                            while (!webArray.isNull(count)) {

                                if (webArray.getJSONObject(count).has("key")) {
                                    String key = webArray.getJSONObject(count).getString("key");
                                    message += "\n（" + (count + 1) + "）" + key + "\n";
                                }
                                if (webArray.getJSONObject(count).has("value")) {
                                    String[] values = gson.fromJson(webArray.getJSONObject(count).getString("value"),
                                            lt);
                                    for (int j = 0; j < values.length; j++) {
                                        String value = values[j];
                                        message += value;
                                        if (j < values.length - 1) {
                                            message += "，";
                                        }
                                    }
                                }
                                count++;
                            }
                        }
                      //  Log.d("查询信息：", message);
                        msg.obj = message;
                        handler.sendMessage(msg);
                    }
                }
            }
           // mSearchContent.setText(message);
        } else {
            handler.sendEmptyMessage(ERROR_WRONG_RESULT);
        }
    }


    //MD5签名码生成函数
    private String md5(String content){
        byte[]hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        }

        StringBuilder hex = new StringBuilder(hash.length*2);
        for(byte b:hash){
            if((b & 0xFF)< 0x10){
                hex.append(0);
            }
            hex.append(Integer.toHexString(b & 0xff));
        }

        return hex.toString();
    }


    //下面为搜索框特效绘制
    private void execute() {
        mSearchBGTxt.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override public void onGlobalLayout() {
                        mSearchBGTxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        performEnterAnimation();
                    }
                });
    }

    private void performEnterAnimation() {
        initLocation();
        final float top = getResources().getDisplayMetrics().density * 20;
        final ValueAnimator translateVa = translateVa(mSearchBGTxt.getY(), top);
        final ValueAnimator scaleVa = scaleVa(1, 0.8f);
        final ValueAnimator alphaVa = alphaVa(0, 1f);
        originX = mHintTxt.getX();
        final float leftSpace = mArrowImg.getRight() * 2;
        final ValueAnimator translateVaX = translateVax(originX, leftSpace);

        setDuration(translateVa, scaleVa, translateVaX, alphaVa);
        star(translateVa, scaleVa, translateVaX, alphaVa);
    }

    private void initLocation() {
        final float translateY = getTranslateY();
        //放到前一个页面的位置
        mSearchBGTxt.setY(mSearchBGTxt.getY() + translateY);
        mHintTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mHintTxt.getHeight()) / 2);
        mSearchTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mSearchTxt.getHeight()) / 2);
    }

    private float getTranslateY() {
        float originY = getIntent().getIntExtra("y", 0);
        int[] location = new int[2];
        mSearchBGTxt.getLocationOnScreen(location);
        return originY - (float) location[1];
    }

    @Override public void onBackPressed() {
        if (!finishing) {
            finishing = true;
            performExitAnimation();
        }
    }

    private void performExitAnimation() {
        final float translateY = getTranslateY();
        final ValueAnimator translateVa = translateVa(mSearchBGTxt.getY(), mSearchBGTxt.getY() + translateY);
        final ValueAnimator scaleVa = scaleVa(0.8f, 1f);
        final ValueAnimator alphaVa = alphaVa(1f, 0f);
        exitListener(translateVa);
        final float currentX = mHintTxt.getX();
        ValueAnimator translateVaX = translateVax(currentX, originX);

        setDuration(translateVa, scaleVa, translateVaX, alphaVa);
        star(translateVa, scaleVa, translateVaX, alphaVa);
    }

    @NonNull private ValueAnimator translateVax(float from, float to) {
        ValueAnimator translateVaX = ValueAnimator.ofFloat(from, to);
        translateVaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mHintTxt.setX(value);
            }
        });
        return translateVaX;
    }

    @NonNull private ValueAnimator translateVa(float from, float to) {
        ValueAnimator translateVa = ValueAnimator.ofFloat(from, to);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSearchBGTxt.setY((Float) valueAnimator.getAnimatedValue());
                mArrowImg.setY(
                        mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mArrowImg.getHeight()) / 2);
                mHintTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mHintTxt.getHeight()) / 2);
                mSearchTxt.setY(
                        mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mSearchTxt.getHeight()) / 2);
            }
        });
        return translateVa;
    }

    @NonNull private ValueAnimator scaleVa(float from, float to) {
        ValueAnimator scaleVa = ValueAnimator.ofFloat(from, to);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSearchBGTxt.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        return scaleVa;
    }

    @NonNull private ValueAnimator alphaVa(float from, float to) {
        ValueAnimator alphaVa = ValueAnimator.ofFloat(from, to);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
                mSearchTxt.setAlpha((Float) valueAnimator.getAnimatedValue());
                mArrowImg.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        return alphaVa;
    }

    private void exitListener(ValueAnimator translateVa) {
        translateVa.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setDuration(ValueAnimator translateVa, ValueAnimator scaleVa,
                             ValueAnimator translateVaX, ValueAnimator alphaVa) {
        alphaVa.setDuration(350);
        translateVa.setDuration(350);
        scaleVa.setDuration(350);
        translateVaX.setDuration(350);
    }

    private void star(ValueAnimator translateVa, ValueAnimator scaleVa, ValueAnimator translateVaX,
                      ValueAnimator alphaVa) {
        alphaVa.start();
        translateVa.start();
        scaleVa.start();
        translateVaX.start();
    }
}
