package com.example.x2y.englishapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x2y.englishapp.User.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public EditText name;
    public EditText password;
    public CheckBox checkBox;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
   private   SharedPreferences prefUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      //  View decorView = getWindow().getDecorView();//获取屏幕的decorView
       // decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        LitePal.getDatabase();
        Button login = (Button)findViewById(R.id.login);
        Button register=(Button)findViewById(R.id.register);
        password=(EditText)findViewById(R.id.pw_etext);
        name=(EditText)findViewById(R.id.name_etext) ;
        //用于实现记住密码功能的shared
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        //用于实现将用户挂在后台的shared
        prefUser=getSharedPreferences("user",MODE_PRIVATE);
        checkBox=(CheckBox) findViewById(R.id.check);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember)
        {
            String account = pref.getString("name","");
            String password1=pref.getString("password","");
            name.setText(account);
            password.setText(password1);
            checkBox.setChecked(true);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = name.getText().toString();
                String password1= password.getText().toString();
                if(!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(password1) )
                {
                    //查询数据库
                    List<User> userlist = DataSupport
                            .where("name =?",new String(name.getText().toString()))
                            .find(User.class);
                    //若不为空则说明由此账户
                    if(!userlist.isEmpty())
                    {

                        Log.d("LoginActivity",userlist.get(0).getPassword()) ;
                        //验证密码
                        if(userlist.get(0).getPassword().equals(password1))
                        {
                            editor=pref.edit();
                            if(checkBox.isChecked())
                            {
                                editor.putBoolean("remember_password",true);
                                editor.putString("name",account);
                                editor.putString("password",password1);

                            }
                            else {
                                editor.clear();
                            }
                            editor.apply();
                            //以下是将当前登录用户的信息存入shared
                            SharedPreferences.Editor editorUser =prefUser.edit();
                            if(!prefUser.getAll().isEmpty())
                            {
                                editorUser.clear();
                                editorUser.commit();
                            }

                                editorUser.putString("name",account);
                                editorUser.putString("password",password1);
                                editorUser.putInt("id",userlist.get(0).getId());
                                editorUser.putInt("gold",userlist.get(0).getGold());
                                editorUser.apply();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);


                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"账号或密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"不存在此账号",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"账号或密码未输入",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    //对注册页面返回的数据进行处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK)
                {

                    String name1 = data.getStringExtra("name");
                    String password1 = data.getStringExtra("password");
                    name.setText(name1);
                    password.setText(password1);
                } 
                break;
                default:
        }
    }
}
