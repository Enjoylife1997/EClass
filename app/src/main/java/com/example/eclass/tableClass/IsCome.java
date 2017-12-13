package com.example.eclass.tableClass;

import org.litepal.crud.DataSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by Enjoy life on 2017/10/3.
 */

public class IsCome extends BmobObject {
    private String courseId;
    private String studentId;
    private int comeNumber;//签到次数
    private int callNumber;//点名次数


    public int getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(int callNumber) {
        this.callNumber = callNumber;
    }

    public int getComeNumber() {
        return comeNumber;
    }

    public void setComeNumber(int comeNumber) {
        this.comeNumber = comeNumber;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String course) {
        this.courseId = course;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
