package com.example.eclass.tableClass;

import org.litepal.crud.DataSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by Enjoy life on 2017/10/3.
 */

public class Course extends BmobObject{

    private String courseId;
    private String courseName;
    private String teacherId;


    public String getCourseName() {return courseName;}

    public void setCourseName(String courseName) {this.courseName = courseName;}

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
