package com.example.eclass.BasicActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eclass.R;
import com.example.eclass.tableClass.IsCome;
import com.example.eclass.tableClass.Message;
import com.example.eclass.tableClass.Teacher;
import com.example.eclass.tableClass.User;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherActivity extends AppCompatActivity {

    public String CourseId2="";
    public String TeacherId1="";
    public String TeacherName1="";
    public String message;
    public String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_layout);

        //默认初始化
        Bmob.initialize(this, "8790673e88e63fbe66a2a39a9339d15a");

        Button CALL = (Button)findViewById(R.id.call);
        Button SHOW_ISCOME = (Button)findViewById(R.id.show_iscome);
        Button SEND = (Button)findViewById(R.id.send);
        Button MESSAGE = (Button)findViewById(R.id.show_message);
        //Button UPLOAD = (Button)findViewById(R.id.upload);


        //进行点名
        CALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出对话框确认
                AlertDialog.Builder dialog = new  AlertDialog.Builder(TeacherActivity.this);
                dialog.setTitle("注意");
                dialog.setMessage("是否要进行点名");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText CALL_C = (EditText)findViewById(R.id.call_course);
                        String CourseId = CALL_C.getText().toString();

                        BmobQuery<IsCome> isComeBmobQuery = new BmobQuery<IsCome>();
                        isComeBmobQuery.addWhereEqualTo("courseId",CourseId);
                        isComeBmobQuery.findObjects(new FindListener<IsCome>() {
                            @Override
                            public void done(List<IsCome> list, BmobException e) {
                                if (e == null){
                                    //给点名的课程的callNumber全部加一
                                    for (IsCome isCome : list) {
                                        isCome.setCallNumber(isCome.getCallNumber() + 1);
                                        isCome.update(isCome.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                            }
                                        });
                                    }

                                    //发出点名通知,用户点击则跳转到登录页面
                                    Intent intent = new Intent(TeacherActivity.this,LoginActivity.class);
                                    PendingIntent pi = PendingIntent.getActivity(TeacherActivity.this,0,intent,0);
                                    NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                    Notification notification = new NotificationCompat.Builder(TeacherActivity.this)
                                            .setContentTitle("EClass消息通知")
                                            .setContentText("发起了点名")
                                            .setWhen(System.currentTimeMillis())
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                                            .setContentIntent(pi)
                                            .setAutoCancel(true)
                                            .setPriority(NotificationCompat.PRIORITY_MAX)
                                            .build();
                                    manager.notify(1,notification);

                                    //跳转二维码生成页面
                                    Intent intent4 = new Intent(TeacherActivity.this,QRCodeActivity.class);
                                    startActivity(intent4);

                                }else {
                                    Toast.makeText(TeacherActivity.this, "输入的课程号有误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });



        //查看课程点名情况
        SHOW_ISCOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText iscome_Course = (EditText)findViewById(R.id.iscome_course);
                CourseId2 = iscome_Course.getText().toString();


                BmobQuery<IsCome> isComeBmobQuery = new BmobQuery<IsCome>();
                isComeBmobQuery.addWhereEqualTo("courseId",CourseId2);
                isComeBmobQuery.findObjects(new FindListener<IsCome>() {
                    @Override
                    public void done(List<IsCome> list, BmobException e) {
                        if (e == null) {
                            Intent intent = new Intent(TeacherActivity.this, ShowIsComeActivity.class);
                            //传递数据到下一个活动
                            intent.putExtra("course_id",CourseId2);
                            startActivity(intent);
                        }else {
                            Toast.makeText(TeacherActivity.this, "输入的课程号有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        //发布消息通知
        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //弹出对话框确认
                AlertDialog.Builder dialog = new  AlertDialog.Builder(TeacherActivity.this);
                dialog.setTitle("注意");
                dialog.setMessage("是否要发布这条信息");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //获取发送消息用户教师ID和Name信息
                        Intent intent = getIntent();
                        String username = intent.getStringExtra("user_name");




                        //获取系统当前时间
                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        time = format.format(date);

                        EditText MESSAGE = (EditText)findViewById(R.id.message);
                        message = MESSAGE.getText().toString();

                        if(message.length()!=0){
                            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            Notification notification = new NotificationCompat.Builder(TeacherActivity.this)
                                    .setContentTitle("EClass消息通知")
                                    .setContentText("发布通知:"+ message)
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_MAX)
                                    .build();
                            manager.notify(1,notification);


                            //通过用户名获取当前教师号
                            BmobQuery<User> userBmobQuery = new BmobQuery<User>();
                            userBmobQuery.addWhereEqualTo("username",username);
                            userBmobQuery.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> list, BmobException e) {
                                    for (User user : list){
                                        TeacherId1 = user.getTeacherId();
                                    }
                                    //通过教师号获取当前教师姓名
                                    BmobQuery<Teacher> teacherBmobQuery = new BmobQuery<Teacher>();
                                    teacherBmobQuery.addWhereEqualTo("teacherId",TeacherId1);
                                    teacherBmobQuery.findObjects(new FindListener<Teacher>() {
                                        @Override
                                        public void done(List<Teacher> list, BmobException e) {
                                            for (Teacher teacher : list){
                                                TeacherName1 = teacher.getTeacherName();
                                            }
                                            //将消息具体内容存入Message表中
                                            Message message1 = new Message();
                                            message1.setText(message);
                                            message1.setSender(TeacherName1);
                                            message1.setTime(time);
                                            message1.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    Toast.makeText(TeacherActivity.this,"消息发布成功",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            });


                        }else {
                            Toast.makeText(TeacherActivity.this,"请输入要发布的信息",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

        //查看已发布的消息通知
        MESSAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });

        /*
        //文件上传操作
        UPLOAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText file_dir = (EditText)findViewById(R.id.upload_fileDir);
                String filedir = file_dir.getText().toString();
                UploadFile uploadFile = new UploadFile();
                uploadFile.uploadMultiFile(filedir);
            }
        });
        */
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
