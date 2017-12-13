package com.example.eclass.tableClass;

import org.litepal.crud.DataSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by Enjoy life on 2017/10/5.
 */

public class Teacher extends BmobObject{
    private String teacherId;
    private String teacherName;


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherNumber) {
        this.teacherId = teacherNumber;
    }
}
