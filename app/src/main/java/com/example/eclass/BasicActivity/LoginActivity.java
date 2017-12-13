package com.example.eclass.BasicActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclass.R;
import com.example.eclass.Tools.ActivityCollector;

import com.example.eclass.Tools.TransitionView;
import com.example.eclass.tableClass.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


import java.util.List;


/*
程序启动时显示的第一个活动界面,即为登陆页面
 */
public class LoginActivity extends AppCompatActivity {


    //用户名文本编辑框
    private EditText username;
    //密码文本编辑框
    private EditText password;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private CheckBox rememberPass;

    private TransitionView mAnimView;


    //用来判断是否成功匹配的标志（很重要，若直接for循环内加上if会输出多次结果）
    public boolean flag1 = false;

    public String studentid2 = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将正在创建的活动添加到活动管理器里
        ActivityCollector.addActivity(this);
        //设置布局
        setContentView(R.layout.login_layout);

        //默认初始化
        Bmob.initialize(this, "8790673e88e63fbe66a2a39a9339d15a");

        //获取SharedPreferences对象
        pref = PreferenceManager.getDefaultSharedPreferences(this);



        //得到记住密码的勾选框
        rememberPass = (CheckBox)findViewById(R.id.remember_pass);

        //判断是否选中记住密码
        boolean isRemember = pref.getBoolean("remember_password",false);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);


        if (isRemember){
            //将用户名和密码都设置到文本框中
            String Username = pref.getString("Username","");
            String Password = pref.getString("Password","");
            username.setText(Username);
            password.setText(Password);
            rememberPass.setChecked(true);
        }




        TextView login = (TextView) findViewById(R.id.tv_sign_up);
        //给登录设置监听器
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {


                //Bmob遍历查询所有数据的方法
                BmobQuery<User> query = new BmobQuery<User>();
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        for (User user : list) {
                            //判断用户输入的用户名和密码是否与数据库中相同,必须要有toString()
                            if (user.getUsername().equals(username.getText().toString()) &&
                                    user.getPassword().equals(password.getText().toString())) {
                                //匹配成功,flag为true
                                flag1 = true;
                                if(flag1){


                                    editor = pref.edit();


                                    if (rememberPass.isChecked()) {//检查复选框是否被选中
                                        editor.putBoolean("remember_password", true);
                                        editor.putString("Username", username.getText().toString());
                                        editor.putString("Password", password.getText().toString());
                                    }else {
                                        editor.clear();
                                    }

                                    if (isteacher(username.getText().toString())){//判断用户是否为教师，是跳转到教师页面
                                        editor.apply();
                                        //动画启动并跳转教师界面
                                        mAnimView.startAnimation();
                                    }
                                    else {//跳转学生页面
                                        editor.apply();
                                        //动画启动并跳转学生界面
                                        mAnimView.startAnimation();
                                    }
                                }else{
                                    //登录信息错误，通过Toast显示提示信息
                                    Toast.makeText(LoginActivity.this,"用户登录信息错误" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });

        TextView regist = (TextView) findViewById(R.id.regist);
        regist.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        regist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });

        //动画实例
        mAnimView = (TransitionView) findViewById(R.id.ani_view);
        mAnimView.setOnAnimationEndListener(new TransitionView.OnAnimationEndListener()
        {
            @Override
            public void onEnd()
            {

                if (isteacher(username.getText().toString())){//判断用户是否为教师
                    gotoTActivity();
                }else {
                    gotoSActivity();
                }
            }
        });
    }


    //判断是否为教师
    public boolean isteacher(String Username){

        //通过User的用户名获取学号
        BmobQuery<User> userBmobQuery = new BmobQuery<User>();
        userBmobQuery.addWhereEqualTo("username",Username);
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User user : list) {
                    studentid2 = user.getStudentId();
                }
            }
        });

        if (studentid2.length()==0){//如果该用户学号部分为空，说明其有教师编号，不是学生
            return true;
        }
        return false;
    }

    private void gotoTActivity()//跳转教师
    {
        String data2 = username.getText().toString();
        Intent intent = new Intent(LoginActivity.this,TeacherActivity.class);
        intent.putExtra("user_name",data2);
        startActivity(intent);
        finish();
    }

    private void gotoSActivity()//跳转学生
    {
        String datax = username.getText().toString();
        Intent intent = new Intent(LoginActivity.this,IndexActivity.class);
        intent.putExtra("user_name",datax);
        startActivity(intent);
        finish();
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
                //销毁所有活动
                ActivityCollector.finishAll();
                //杀掉当前进程
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
        }
        return true;
    }
}
