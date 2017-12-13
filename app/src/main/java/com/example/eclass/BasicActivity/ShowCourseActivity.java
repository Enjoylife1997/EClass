package com.example.eclass.BasicActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eclass.R;
import com.example.eclass.tableClass.IsCome;



import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/*
   该类用于查看学生所选课程
 */


public class ShowCourseActivity extends AppCompatActivity {

    public ArrayList<String> courses;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_course_layout);

        //默认初始化
        Bmob.initialize(this, "8790673e88e63fbe66a2a39a9339d15a");

        //获取index活动传来的用户信息
        Intent intent = getIntent();
        String student_id = intent.getStringExtra("student_id");

        courses = new ArrayList<String>();  //声明一个list，动态存储要显示的信息


        BmobQuery<IsCome> isComeBmobQuery = new BmobQuery<IsCome>();
        isComeBmobQuery.addWhereEqualTo("studentId",student_id);
        isComeBmobQuery.findObjects(new FindListener<IsCome>() {
            @Override
            public void done(List<IsCome> list, BmobException e) {
                for (IsCome isCome : list){
                    courses.add(isCome.getCourseId());//存储相应学号的课程号到list中，类型为String
                }
                listView=(ListView)findViewById(R.id.list_course);//将listView与布局对象关联
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowCourseActivity.this,android.R.layout.simple_list_item_1,courses);

                listView.setAdapter(adapter);
            }
        });

    }
}
