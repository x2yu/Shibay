package com.example.x2y.englishapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.User.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    public EditText name;
    public EditText password;
    public  EditText password2;
    private CardView cvAdd;
    private FloatingActionButton fab;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initAimation();
        initRegiste();
    }


    //初始化注册逻辑
    private void initRegiste(){
        Button button = (Button)findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=(EditText)findViewById(R.id.pw_etext);
                password2=(EditText)findViewById(R.id.pw_etext2);
                name=(EditText)findViewById(R.id.name_etext);
                List<User> userlist = DataSupport
                        .where("name =?",new String(name.getText().toString()))
                        .find(User.class);
                //若查询结果为空，则提醒用户并清空输入框
                if(userlist.size()!=0)
                {
                    Toast.makeText(RegisterActivity.this,"账号已存在，请重新输入",Toast.LENGTH_SHORT).show();
                    password.setText("");
                    password2.setText("");
                    name.setText("");
                }
                //若查询结果不为空进一步验证
                else
                {
                    //若两次输入密码不同，提示用户并清空密码输入框
                    if(!password.getText().toString().equals(password2.getText().toString()))
                    {
                        Toast.makeText(RegisterActivity.this,"两次输入密码错误请重新输入",Toast.LENGTH_SHORT).show();
                        password.setText("");
                        password2.setText("");
                    }
                    else
                    {
                        Intent intent = new Intent();
                        //注册成功将账号密码返回给登陆界面
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("password",password.getText().toString());
                        setResult(RESULT_OK,intent);
                        //保存用户数据进入数据库
                        User user= new User();
                        user.setName(name.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.save();
                        finish();
                    }
                }

            }
        });
    }

    //初始化动画效果
    private void initAimation(){

        cvAdd = findViewById(R.id.cv_add);

        //判断版本 5.0以上使用
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //设置进度过渡特效并且设置监听
            ShowEnterAnimation();
        }
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationRevealClose();//关闭窗口特效
            }
        });

    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animationRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }


    //进入过渡特效逻辑
    private void animationRevealShow(){
        //设置圆形展开特效
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    //关闭窗口特效逻辑
    private void animationRevealClose(){
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0,cvAdd.getHeight(),fab.getHeight()/2);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.registe);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animationRevealClose();
    }
}
