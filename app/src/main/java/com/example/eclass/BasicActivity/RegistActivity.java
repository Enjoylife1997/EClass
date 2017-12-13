package com.example.eclass.BasicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eclass.R;
import com.example.eclass.Tools.ActivityCollector;
import com.example.eclass.tableClass.User;



import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegistActivity extends AppCompatActivity {


    public String strusername;
    public String strpassword;
    public String strstudentid="";
    public String strteacherid="";
    public boolean flag1 = false;
    public boolean flag2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将一个正在创建的活动添加到活动管理器中
        ActivityCollector.addActivity(this);
        setContentView(R.layout.regist_layout);

        //默认初始化
        Bmob.initialize(this, "8790673e88e63fbe66a2a39a9339d15a");

        Button yes = (Button) findViewById(R.id.YES);
        final Button no =(Button) findViewById(R.id.NO);



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                final EditText studentid = (EditText)findViewById(R.id.studentid);
                EditText teacherid = (EditText)findViewById(R.id.teacherid);
                strusername = username.getText().toString();
                strpassword = password.getText().toString();
                strstudentid = studentid.getText().toString();
                strteacherid = teacherid.getText().toString();

                //判断用户注册是否输入了密码和用户名,以及学号
                //首先学会如何判断EditText中内容为空，要包含trim（去掉首部空格）
                if ((strusername.length()!=0 && strpassword.length()!=0 && strstudentid.length()!=0)||
                        (strusername.length()!=0 && strpassword.length()!=0 && strteacherid.length()!=0)
                        ){
                    //若为学生
                    if(strstudentid.length() != 0) {
                        BmobQuery<User> userBmobQuery = new BmobQuery<User>();
                        userBmobQuery.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                for (User user : list) {
                                    //遍历User表，看是否含有相同的用户名和学号
                                    if (strusername.equals(user.getUsername()) || strstudentid.equals(user.getStudentId()))
                                    {
                                        flag1 = true;
                                    }
                                }
                                if(flag1) {
                                    Toast.makeText(RegistActivity.this, "用户名或学号已经被注册", Toast.LENGTH_SHORT).show();
                                }else {
                                    createUser();
                                }
                            }
                        });
                    }else if (strteacherid.length() != 0){//若为教师
                        BmobQuery<User> userBmobQuery = new BmobQuery<User>();
                        userBmobQuery.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                for (User user : list) {
                                    //遍历User表，看是否有相同用户名或教师号存在
                                    if (strusername.equals(user.getUsername()) || strteacherid.equals(user.getTeacherId()))
                                    {
                                        flag2 =true;
                                    }
                                }
                                if(flag2){
                                    Toast.makeText(RegistActivity.this, "用户名或教师号已经被注册", Toast.LENGTH_SHORT).show();
                                }else {
                                    createUser();
                                }
                            }

                        });
                    }

                }else {
                    Toast.makeText(RegistActivity.this,"用户名和密码不能为空，且学号和教师号必须输入一个" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //添加用户信息
    public void createUser() {
        User user1 = new User();
        //存储用户名密码
        user1.setUsername(strusername);
        user1.setPassword(strpassword);
        user1.setStudentId(strstudentid);
        user1.setTeacherId(strteacherid);
        user1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Toast.makeText(RegistActivity.this, "创建用户成功,即将跳转回登录页面重新登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    //表明一个要销毁的活动从活动管理器里移除
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    //创建Option菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    //定义菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit://当点击了退出时，退出程序
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
        }
        return true;
    }
}
