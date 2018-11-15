package com.example.x2y.englishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.User.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    public EditText name;
    public EditText password;
    public  EditText password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button = (Button)findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=(EditText)findViewById(R.id.pw_etext);
                password2=(EditText)findViewById(R.id.pw_etext2);
                name=(EditText)findViewById(R.id.name_etext);
                List<User> userlist = DataSupport
                        .where("name ="+name.getText().toString())
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


}
