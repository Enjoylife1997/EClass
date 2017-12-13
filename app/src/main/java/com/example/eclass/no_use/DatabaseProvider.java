package com.example.eclass.no_use;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.eclass.no_use.MyDatabaseHelper;

/*
   内容提供器用于向教师端提供访问学生端数据库
 */


public class DatabaseProvider extends ContentProvider {

    //对各表访问所有数据和单条数据定义为常量
    public static final int USER_DIR = 0;
    public static final int USER_ITEM = 1;
    public static final int STUDENT_DIR = 2;
    public static final int STUDENT_ITEM = 3;
    public static final int COURSE_DIR = 4;
    public static final int COURSE_ITEM = 5;
    public static final int ISCOME_DIR = 6;
    public static final int ISCOME_ITEM = 7;
    public static final int TEACHER_DIR = 8;
    public static final int TEACHER_ITEM = 9;
    public static final String AUTHORITY = "com.example.eclass.provider";
    private static UriMatcher uriMatcher;

    private MyDatabaseHelper dbHelper;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"user",USER_DIR);
        uriMatcher.addURI(AUTHORITY,"user/#",USER_ITEM);
        uriMatcher.addURI(AUTHORITY,"student",STUDENT_DIR);
        uriMatcher.addURI(AUTHORITY,"student/#",STUDENT_ITEM);
        uriMatcher.addURI(AUTHORITY,"course/",COURSE_DIR);
        uriMatcher.addURI(AUTHORITY,"course/#",COURSE_ITEM);
        uriMatcher.addURI(AUTHORITY,"iscome/",ISCOME_DIR);
        uriMatcher.addURI(AUTHORITY,"iscome/#",ISCOME_ITEM);
        uriMatcher.addURI(AUTHORITY,"teacher/",TEACHER_DIR);
        uriMatcher.addURI(AUTHORITY,"teacher/#",TEACHER_ITEM);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MyDatabaseHelper(getContext(),"EClass.db",null,1);
        return true;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                deletedRows =db.delete("User",selection,selectionArgs);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);//此处Id为表默认id，非学生号id等等
                deletedRows = db.delete("User","id = ?",new String[]{userId});
                break;
            case STUDENT_DIR:
                deletedRows =db.delete("Student",selection,selectionArgs);
                break;
            case STUDENT_ITEM:
                String studentId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Student","id = ?",new String[]{studentId});
                break;
            case COURSE_DIR:
                deletedRows = db.delete("Course",selection,selectionArgs);
                break;
            case COURSE_ITEM:
                String courseId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Course","id = ?",new String[]{courseId});
                break;
            case ISCOME_DIR:
                deletedRows = db.delete("IsCome",selection,selectionArgs);
                break;
            case ISCOME_ITEM:
                String IsComeId = uri.getPathSegments().get(1);
                deletedRows = db.delete("IsCome","id = ?",new String[]{IsComeId});
                break;
            case TEACHER_DIR:
                deletedRows = db.delete("Teacher",selection,selectionArgs);
                break;
            case TEACHER_ITEM:
                String teacherId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Teacher","id = ?",new String[]{teacherId});
                break;
            default:
                break;
        }

        return deletedRows;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
            case USER_ITEM:
                long newUserId = db.insert("User",null,values);//此处new的id为表的默认主键id,非学号等
                uriReturn = Uri.parse("content://"+ AUTHORITY +"/user/" +newUserId);
                break;
            case STUDENT_DIR:
            case STUDENT_ITEM:
                long newStudentId = db.insert("Student",null,values);
                uriReturn = Uri.parse("content://"+ AUTHORITY +"/student/" + newStudentId);
                break;
            case COURSE_DIR:
            case COURSE_ITEM:
                long newCourseId = db.insert("Course",null,values);
                uriReturn = Uri.parse("content://" +AUTHORITY + "/course/" + newCourseId);
                break;
            case ISCOME_DIR:
            case ISCOME_ITEM:
                long newIsComeId = db.insert("IsCome",null,values);
                uriReturn = Uri.parse("content://" +AUTHORITY + "/iscome/" + newIsComeId);
                break;
            case TEACHER_DIR:
            case TEACHER_ITEM:
                long newTeacherId = db.insert("Teacher",null,values);
                uriReturn  = Uri.parse("content://" +AUTHORITY + "/teacher/" +newTeacherId);
                break;
                default:
                    break;
        }
        return uriReturn;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                cursor = db.query("User",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);//此处id为表单默认id，非自定义的学号id等
                cursor = db.query("User",projection,"id = ?",new String[]{userId},null,null,null,sortOrder);
                break;
            case STUDENT_DIR:
                cursor = db.query("Student",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case STUDENT_ITEM:
                String studentId = uri.getPathSegments().get(1);
                cursor = db.query("Student",projection,"id = ?",new String[]{studentId},null,null,null,sortOrder);
                break;
            case COURSE_DIR:
                cursor = db.query("Course",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case COURSE_ITEM:
                String courseId = uri.getPathSegments().get(1);
                cursor = db.query("Course",projection,"id = ?",new String[]{courseId},null,null,null,sortOrder);
                break;
            case ISCOME_DIR:
                cursor = db.query("IsCome",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ISCOME_ITEM:
                String IsComeId = uri.getPathSegments().get(1);
                cursor = db.query("IsCome",projection,"id = ?",new String[]{IsComeId},null,null,null,sortOrder);
                break;
            case TEACHER_DIR:
                cursor = db.query("Teacher",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TEACHER_ITEM:
                String teacherId = uri.getPathSegments().get(1);
                cursor = db.query("Teacher",projection,"id = ?",new String[]{teacherId},null,null,null,sortOrder);
                break;
            default:
                break;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //更新数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                updateRows =db.update("User",values,selection,selectionArgs);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                updateRows = db.update("User",values,"id = ?",new String[]{userId});
                break;
            case STUDENT_DIR:
                updateRows =db.update("Student",values,selection,selectionArgs);
                break;
            case STUDENT_ITEM:
                String studentId = uri.getPathSegments().get(1);
                updateRows = db.update("Student",values,"id = ?",new String[]{studentId});
                break;
            case COURSE_DIR:
                updateRows = db.update("Course",values,selection,selectionArgs);
                break;
            case COURSE_ITEM:
                String courseId = uri.getPathSegments().get(1);
                updateRows = db.update("Course",values,"id = ?",new String[]{courseId});
                break;
            case ISCOME_DIR:
                updateRows = db.update("IsCome",values,selection,selectionArgs);
                break;
            case ISCOME_ITEM:
                String IsComeId = uri.getPathSegments().get(1);
                updateRows = db.update("IsCome",values,"id = ?",new String[]{IsComeId});
                break;
            case TEACHER_DIR:
                updateRows = db.update("Teacher",values,selection,selectionArgs);
                break;
            case TEACHER_ITEM:
                String teacherId = uri.getPathSegments().get(1);
                updateRows = db.update("Teacher",values,"id=?",new String[]{teacherId});
                break;
            default:
                break;
        }
        return updateRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.eclass.provider.user";
            case USER_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.eclass.provider.user";
            case STUDENT_DIR:
                return  "vnd.android.cursor.dir/vnd.com.example.eclass.provider.student";
            case STUDENT_ITEM:
                return  "vnd.android.cursor.item/vnd.com.example.eclass.provider.student";
            case COURSE_DIR:
                return  "vnd.android.cursor.dir/vnd.com.example.eclass.provider.course";
            case COURSE_ITEM:
                return  "vnd.android.cursor.item/vnd.com.example.eclass.provider.course";
            case ISCOME_DIR:
                return  "vnd.android.cursor.dir/vnd.com.example.eclass.provider.iscome";
            case ISCOME_ITEM:
                return  "vnd.android.cursor.item/vnd.com.example.eclass.provider.iscome";
            case TEACHER_DIR:
                return  "vnd.android.cursor.dir/vnd.com.example.eclass.provider.teacher";
            case TEACHER_ITEM:
                return  "vnd.android.cursor.item/vnd.com.example.eclass.provider.teacher";
        }
        return null;
    }

}
