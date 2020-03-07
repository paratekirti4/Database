package com.quagnitia.databasedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.quagnitia.databasedemo.database.StudentTableQuery;

import java.util.ArrayList;

public class ShowActivity extends Activity {
    Context context=this;
    ListView list;
    ArrayList<Student> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initUI();
        getData();
        setData();
    }

    private void setData() {
        StudentAdapter sd=new StudentAdapter(context,studentList);
        list.setAdapter(sd);
    }

    private void getData() {
        studentList=new ArrayList<>();
        studentList= StudentTableQuery.fetchAllStudentDetails();
    }

    private void initUI() {
        list= (ListView) findViewById(R.id.list);
    }


    public void deleteRecord(int roll) {
        boolean flag=StudentTableQuery.deleteSelectedRecord(roll);
        if (flag==true)
        {
            Toast.makeText(context,"Record is deleted",Toast.LENGTH_SHORT).show();
            getData();
            setData();
        }
    }
}
