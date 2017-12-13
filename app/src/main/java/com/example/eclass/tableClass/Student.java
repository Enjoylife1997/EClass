package com.example.eclass.tableClass;

import org.litepal.crud.DataSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by Enjoy life on 2017/10/3.
 */

public class Student extends BmobObject {
    private String studentId;
    private String studentName;


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
