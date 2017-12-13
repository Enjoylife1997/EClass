package com.example.eclass.Tools;

import com.example.eclass.tableClass.User;



import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Enjoy life on 2017/10/10.
 */

/*
    用于判断账户登录人是否为教师
 */
public class IsTeacher {
    public String studentid;

    public boolean isteacher(String Username){
        //通过User的用户名获取学号
        BmobQuery<User> userBmobQuery = new BmobQuery<User>();
        userBmobQuery.addWhereEqualTo("username",Username);
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for(User user : list) {
                    studentid = user.getStudentId();
                }
            }
        });

        if (studentid.toString().length()==0){//如果该用户学号部分为空，说明其有教师编号，不是学生
            return true;
        }
        return false;
    }
}
