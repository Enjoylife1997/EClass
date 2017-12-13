package com.example.eclass.BasicActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclass.R;
import com.example.eclass.Tools.ActivityCollector;
import com.example.eclass.Tools.InitData;
import com.example.eclass.tableClass.IsCome;
import com.example.eclass.tableClass.User;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class IndexActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    public boolean isSCAN = false;//用于判断是否进行了扫描，先扫码才可签到
    public String returnedData = "NOSCAN";//用于获取扫描活动传回的数据

    private Toolbar toolbar;//标题栏

    public String student_id1 = null;
    public String student_id2 = null;
    public String course_id1;
    public boolean flag2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);

        //默认初始化
        Bmob.initialize(this, "8790673e88e63fbe66a2a39a9339d15a");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }


        View headerView = navigationView.getHeaderView(0);//获得滑动菜单头部布局
        TextView Username = (TextView) headerView.findViewById(R.id.username_head);//滑动菜单头的用户名显示
        //获取登录活动传来的用户信息
        Intent intent = getIntent();
        String USERNAME = intent.getStringExtra("user_name");
        Username.setText("欢迎用户："+USERNAME);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.init_data://从网络上加载JSON文件解析并初始化数据
                        InitData initData = new InitData();
                        initData.InitStudent();
                        initData.InitCourse();
                        initData.InitIsCome();
                        initData.InitTeacher();
                        Toast.makeText(IndexActivity.this,"初始化数据成功",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.show_course://显示该学生所修的所有课程
                        //不要在方法中定义student_id


                        //获取登录活动传来的用户信息
                        Intent intent1 = getIntent();
                        String USERNAME = intent1.getStringExtra("user_name");

                        //通过User的用户名获取学号
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("username",USERNAME);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                for(User user : list){
                                        student_id1 = user.getStudentId();
                                }
                                //创建Intent对象，传入源Activity和目的Activity的类对象
                                Intent intent2 = new Intent(IndexActivity.this, ShowCourseActivity.class);
                                //传递数据到下一个活动
                                intent2.putExtra("student_id",student_id1);
                                //启动Activity
                                startActivity(intent2);
                            }
                        });

                        break;

                    case R.id.show_test://查看测试题
                        Intent intent = new Intent(IndexActivity.this,DownloadActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.show_message://查看已发布消息
                        Intent intent3 = new Intent(IndexActivity.this,MessageActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });




        Button come = (Button)findViewById(R.id.Come);
        //点击按钮完成签到，之后可加上条件约束，如什么情况下可以签到
        come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //弹出对话框确认
                AlertDialog.Builder dialog = new  AlertDialog.Builder(IndexActivity.this);
                dialog.setTitle("注意");
                dialog.setMessage("是否要进行签到");
                dialog.setCancelable(false);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取登录活动传来的用户信息
                        Intent intent = getIntent();
                        String USERNAME = intent.getStringExtra("user_name");


                        /*
                        Intent intent1 = getIntent();
                        isSCAN_CANCEL = intent1.getStringExtra("isSCANcancel");
                        */

                        //获取输入课程号
                        EditText COURSE= (EditText)findViewById(R.id.Course);
                        course_id1 = COURSE.getText().toString();


                        //通过User的用户名获取学号
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("username",USERNAME);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                for(User user : list){
                                    student_id2 = user.getStudentId();
                                }
                            }
                        });




                        if(isSCAN){//先判断是否进行了扫码
                            if(returnedData.equals("qrcodetest")) {//如果扫描结果和教师端二维码相同，可以签到
                                BmobQuery<IsCome> isComeBmobQuery = new BmobQuery<IsCome>();
                                isComeBmobQuery.addWhereEqualTo("courseId",course_id1);
                                BmobQuery<IsCome> isComeBmobQuery1 = new BmobQuery<IsCome>();
                                isComeBmobQuery1.addWhereEqualTo("studentId",student_id2);
                                List<BmobQuery<IsCome>> queries = new ArrayList<BmobQuery<IsCome>>();
                                queries.add(isComeBmobQuery);
                                queries.add(isComeBmobQuery1);
                                BmobQuery<IsCome> mainQuery = new BmobQuery<IsCome>();
                                mainQuery.and(queries);
                                mainQuery.findObjects(new FindListener<IsCome>() {
                                    @Override
                                    public void done(List<IsCome> list, BmobException e) {
                                        if(e==null) {
                                            for (IsCome isCome : list) {
                                                //对其签到数进行更新
                                                isCome.setComeNumber(isCome.getComeNumber() + 1);
                                                isCome.update(isCome.getObjectId(), new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        Toast.makeText(IndexActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                                                        isSCAN = false;
                                                    }
                                                });
                                            }
                                        }else {
                                            Toast.makeText(IndexActivity.this, "课程号不存在，签到失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                    Toast.makeText(IndexActivity.this,"所扫二维码不正确",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(IndexActivity.this,"请先进行扫码",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        Button scanQR = (Button)findViewById(R.id.scanQR);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(IndexActivity.this,ScanActivity.class);
                //用于获取SCAN活动传回的扫描数据
                startActivityForResult(intent2,1);
                isSCAN = true;

            }
        });

        //收缩工具栏动画
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {
                startAni();
            }
        });

    }

    private void startAni()
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(toolbar.getHeight(), 150);
        valueAnimator.setInterpolator(new AccelerateInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                toolbar.getLayoutParams().height = (int) animation.getAnimatedValue();
                toolbar.requestLayout();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator,
                ObjectAnimator.ofFloat(R.drawable.ic_menu, "Alpha", 0.1f, 1.0f),
                ObjectAnimator.ofFloat(R.drawable.ic_menu, "Alpha", 0.1f, 1.0f));
        animatorSet.setDuration(1200);
        animatorSet.start();
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
            case android.R.id.home://导航栏滑动菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return true;
    }

    //重写方法用于获取上一个活动返回的数据
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    returnedData = data.getStringExtra("data_return");
                }
                break;
            default:
        }
    }


    //back键退出时添加对话框确认
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {

             AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 设置标题
            builder.setTitle("温馨提醒");
            // 设置提示内容
            builder.setMessage("确定要退出么");
            // 设置确定按钮
            builder.setPositiveButton("确定", listener);
            // 设置否定按钮
            builder.setNegativeButton("取消", null);
            AlertDialog dlg = builder.create();
            // 显示AlertDialog
            dlg.show();

        }
        return false;
    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

}
