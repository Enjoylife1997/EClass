package com.example.eclass.tableClass;

import org.litepal.crud.DataSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by Enjoy life on 2017/10/2.
 */

public class User extends BmobObject{
    private String username;
    private String password;
    private String studentId;
    private String teacherId;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentId() {

        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
