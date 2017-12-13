package com.example.eclass.Tools;

import com.example.eclass.tableClass.Course;
import com.example.eclass.tableClass.IsCome;
import com.example.eclass.tableClass.Student;
import com.example.eclass.tableClass.Teacher;



import java.util.List;

/**
 * Created by Enjoy life on 2017/10/4.
 */

/*
   由于云端数据库可设置唯一值，此部分不用
 */


/*
    该类中方法主要用于判断表中自定义“主码”是否唯一
    唯一则记录新增，用save()；
    不唯一则用update()更新
 */

public class IsOnly {
    /*

    public void OnlyStudent(String studentid,String studentname){

        boolean flag = true;
        List<Student> students=DataSupport.findAll(Student.class);
        for (Student student : students){
            //学生表中学号为主码
            if(studentid.equals(student.getStudentId())) {
                flag = false;
            }
        }
        if(flag) {
            Student student2 = new Student();
            student2.setStudentId(studentid);
            student2.setStudentName(studentname);
            student2.save();
        }else {
            Student student3 = new Student();
            student3.setStudentName(studentname);
            student3.updateAll("studentId= ?",studentid);
        }
    }


    public void OnlyCourse(String courseid,String coursename,String teacherid){
        //判断自定义的“主键”是否有重复数据，无重复数据则用save()新增，有则用update更新
        boolean flag = true;
        List<Course> courses= DataSupport.findAll(Course.class);
        for(Course course : courses ) {
            //课程表中课程号与教师号共同构成主码
            if (teacherid.equals(course.getTeacherId()) && courseid.equals(course.getCourseId())) {
                flag = false;
            }
        }
        if(flag){
            Course course2 = new Course();
            course2.setCourseId(courseid);
            course2.setCourseName(coursename);
            course2.setTeacherId(teacherid);
            course2.save();}
        else {
            Course course3 = new Course();
            course3.setCourseName(coursename);
            //用“主码”来约束更新部分
            course3.updateAll("teacherId = ? and courseId = ?" ,teacherid,courseid);
        }
    }

    public void OnlyIsCome(String courseid,String studentid,int comeNumber,int callNumber){
        boolean flag = true;
        List<IsCome> isComes = DataSupport.findAll(IsCome.class);
        for(IsCome isCome : isComes){
            //签到表中学号与课程名共同构成主码
            if (studentid.equals(isCome.getStudentId()) && courseid.equals(isCome.getCourseId())){
                flag = false;
            }
        }

        if (flag){
            IsCome isCome2 = new IsCome();
            isCome2.setCourseId(courseid);
            isCome2.setStudentId(studentid);
            isCome2.setComeNumber(comeNumber);
            isCome2.setCallNumber(callNumber);
            isCome2.save();
        }else {
            IsCome isCome3 = new IsCome();
            isCome3.setComeNumber(comeNumber);
            isCome3.setCallNumber(callNumber);
            isCome3.updateAll("studentId=? and courseId=?",studentid,courseid);
        }
    }

    public void OnlyTeacher(String teacherId,String teacherName){
        boolean flag = true;
        List<Teacher> teachers = DataSupport.findAll(Teacher.class);
        for(Teacher teacher : teachers){
            //签到表中学号与课程名共同构成主码
            if (teacher.getTeacherId().equals(teacherId)){
                flag = false;
            }
        }

        if (flag){
            Teacher teacher = new Teacher();
            teacher.setTeacherId(teacherId);
            teacher.setTeacherName(teacherName);
            teacher.save();

        }else {
            Teacher teacher = new Teacher();
            teacher.setTeacherName(teacherName);
            teacher.updateAll("teacherId = ?",teacherId);
        }
    }
    */

}
