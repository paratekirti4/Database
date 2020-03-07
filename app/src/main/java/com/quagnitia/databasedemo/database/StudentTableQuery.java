package com.quagnitia.databasedemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quagnitia.databasedemo.Student;

import java.util.ArrayList;

/**
 * Created by varsha on 7/27/2017.
 */

public class StudentTableQuery {
    Context context;
    public static final String TABLE_NAME = "Student";
    public static final String COL_ROLL_NO = "rollno";
    public static final String COL_NAME = "name";
    public static final String COL_CITY = "city";
    static DBHelper dbHelper;


    public StudentTableQuery(Context context, DBHelper dbHelper) {
        this.context=context;
        this.dbHelper=dbHelper;
    }

    public static String createStudentTable() {
        String createTableQuery="create table  If Not Exists "+TABLE_NAME+"("+COL_ROLL_NO+" INTEGER,"+COL_NAME+" TEXT,"+COL_CITY+" TEXT);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery="DROP TABLE IF EXISTS "+TABLE_NAME;
        return dropTableQuery;
    }

    public static boolean insertStudentData(int roll, String name, String city) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_ROLL_NO,roll);
        cv.put(COL_NAME,name);

        cv.put(COL_CITY,city);

        long rowid=db.insert(TABLE_NAME,null,cv);

        if (rowid==-1)
        {
            flag=false;
        }
        else
        {
            flag=true;
        }

        return flag;
    }

    public static ArrayList<Student> fetchAllStudentDetails() {
        ArrayList studentList=new ArrayList();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME,null);
        if (c.moveToFirst())//move to first record first time at start
        {
            do{
                int roll=c.getInt(c.getColumnIndex(COL_ROLL_NO));
                String name=c.getString(c.getColumnIndex(COL_NAME));
                String city=c.getString(c.getColumnIndex(COL_CITY));

                Student student=new Student();
                student.setRoll(roll);
                student.setName(name);
                student.setCity(city);

                studentList.add(student);

            }while(c.moveToNext());
        }

        return studentList;
    }

    public static boolean deleteSelectedRecord(int roll) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + COL_ROLL_NO + "=" + roll);
        return true;
    }

    public static boolean updateStudentData(int oldRoll, int roll, String name, String city) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_ROLL_NO,roll);
        cv.put(COL_NAME,name);
        cv.put(COL_CITY,city);

        int i=db.update(TABLE_NAME,cv,COL_ROLL_NO+"="+oldRoll,null);
             if (i==0)
             {
                 flag=false;
             }
             else
             {
                 flag=true;
             }
             return flag;
    }
}
