package com.example.eclass.no_use;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Enjoy life on 2017/10/8.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "password text, "
            + "studentId text)";
    public static final String CREATE_STUDENT = "create table Student ("
            + "id integer primary key autoincrement, "
            + "studentId text, "
            + "studentName text)";

    public static final String CREATE_COURSE = "create table Course ("
            + "id integer primary key autoincrement, "
            + "courseId text, "
            + "courseName text, "
            + "teacherId text)";

    public static final String CREATE_ISCOME = "create table IsCome ("
            + "id integer primary key autoincrement, "
            + "courseId text, "
            + "studentId text, "
            + "comeNumber integer, "
            + "callNumber integer)";

    public static final String CREATE_TEACHER = "create table Teacher ("
            + "id integer primary key autoincrement, "
            + "teacherId text, "
            + "teacherName text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_ISCOME);
        db.execSQL(CREATE_TEACHER);
        // Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Student");
        db.execSQL("drop table if exists Course");
        db.execSQL("drop table if exists IsCome");
        db.execSQL("drop table if exists Teacher");
        onCreate(db);
    }
}
