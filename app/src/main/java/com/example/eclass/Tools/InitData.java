package com.example.eclass.Tools;

import com.example.eclass.tableClass.Course;
import com.example.eclass.tableClass.IsCome;
import com.example.eclass.tableClass.Student;
import com.example.eclass.tableClass.Teacher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Enjoy life on 2017/10/3.
 */

/*
    该类用于初始化课程表数据和教师数据进行测试用
    * 若之后可从服务器获取相关信息再进行代码修改 *
 */

public class InitData {

    private String ip = "192.168.43.238";

    public void InitStudent(){

        HttpUtil.sendOkHttpRequest("http://"+ip+"/get_student_data.json",new okhttp3.Callback(){
            public void onResponse(Call call,Response response)throws IOException{
                //得到服务器返回的具体内容，进行JSON数据解析
                String responseData = response.body().string();
                parseJSONWithGSON_S(responseData);
            }
            public void onFailure(Call call,IOException e){
                //在这里对异常情况进行处理
                System.out.println("获取学生信息出错");
            }

        });

    }

    public void InitCourse(){

        HttpUtil.sendOkHttpRequest("http://"+ip+"/get_course_data.json",new okhttp3.Callback(){
            public void onResponse(Call call,Response response)throws IOException{
                //得到服务器返回的具体内容，进行JSON数据解析
                String responseData = response.body().string();
                parseJSONWithGSON_C(responseData);
            }
            public void onFailure(Call call,IOException e){
                //在这里对异常情况进行处理
                System.out.println("获取课程信息出错");
            }

        });

    }

    public void InitIsCome(){

        HttpUtil.sendOkHttpRequest("http://"+ip+"/get_iscome_data.json",new okhttp3.Callback(){
            public void onResponse(Call call,Response response)throws IOException{
                //得到服务器返回的具体内容，进行JSON数据解析
                String responseData = response.body().string();
                parseJSONWithGSON_I(responseData);
            }
            public void onFailure(Call call,IOException e){
                //在这里对异常情况进行处理
                System.out.println("获取签到信息出错");
            }

        });


    }

    public void InitTeacher(){
        HttpUtil.sendOkHttpRequest("http://"+ip+"/get_teacher_data.json",new okhttp3.Callback(){
            public void onResponse(Call call,Response response)throws IOException{
                //得到服务器返回的具体内容，进行JSON数据解析
                String responseData = response.body().string();
                parseJSONWithGSON_T(responseData);
            }
            public void onFailure(Call call,IOException e){
                //在这里对异常情况进行处理
                System.out.println("获取教师信息出错");
            }

        });
    }




    //解析Student信息的JSON格式数据,并存入数据库
    private void parseJSONWithGSON_S(String jsonData){
        Gson gson = new Gson();
        List<Student> studentList = gson.fromJson(jsonData,new TypeToken<List<Student>>(){}.getType());
        for (Student student : studentList){
            Student student1 = new Student();
            student1.setStudentId(student.getStudentId());
            student1.setStudentName(student.getStudentName());
            student1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                }
            });
        }
    }

    //解析Course信息的JSON格式数据,并存入数据库
    private void parseJSONWithGSON_C(String jsonData){
        Gson gson = new Gson();
        List<Course> courseList = gson.fromJson(jsonData,new TypeToken<List<Course>>(){}.getType());
        for (Course course : courseList){

            Course course1 = new Course();
            course1.setCourseId(course.getCourseId());
            course1.setCourseName(course.getCourseName());
            course1.setTeacherId(course.getTeacherId());
            course1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                }
            });
        }
    }

    //解析IsCome信息的JSON格式数据,并存入数据库
    private void parseJSONWithGSON_I(String jsonData){
        Gson gson = new Gson();
        List<IsCome> isComeList = gson.fromJson(jsonData,new TypeToken<List<IsCome>>(){}.getType());
        for (IsCome isCome : isComeList){

            IsCome isCome1 = new IsCome();
            isCome1.setCourseId(isCome.getCourseId());
            isCome1.setStudentId(isCome.getStudentId());
            isCome1.setComeNumber(isCome.getComeNumber());
            isCome1.setCallNumber(isCome.getCallNumber());
            isCome1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                }
            });
        }
    }

    //解析Teacher信息的JSON格式数据，并存入数据库
    private void parseJSONWithGSON_T(String jsonData){
        Gson gson = new Gson();
        List<Teacher> teacherList = gson.fromJson(jsonData,new TypeToken<List<Teacher>>(){}.getType());
        for (Teacher teacher : teacherList){
            Teacher teacher1 = new Teacher();
            teacher1.setTeacherId(teacher.getTeacherId());
            teacher1.setTeacherName(teacher.getTeacherName());
            teacher1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                }
            });
        }
    }

}
