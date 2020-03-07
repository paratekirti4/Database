package com.quagnitia.databasedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quagnitia.databasedemo.database.DBHelper;
import com.quagnitia.databasedemo.database.StudentTableQuery;

public class MainActivity extends Activity implements View.OnClickListener {
    Context context = this;
    Button btnInsert, btnShow;
    EditText etRoll, etName, etCity;
    DBHelper dbHelper;
    boolean isUpdate = false;
    int roll;
    String name, city;
    int oldRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();//to initialize database
        initUI();
        initListner();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            roll = intent.getExtras().getInt("ROLL");
            name = intent.getExtras().getString("NAME");
            city = intent.getExtras().getString("CITY");
            isUpdate = intent.getExtras().getBoolean("IsUpdate");

            oldRoll = roll;
        }

        if (isUpdate == true)//update
        {
            btnInsert.setText("UPDATE");

            etRoll.setText("" + roll);
            etName.setText(name);
            etCity.setText(city);
        } else { //insert
            btnInsert.setText("Insert");

            etRoll.setText("");
            etName.setText("");
            etCity.setText("");
        }

    }

    private void initComponent() {
        dbHelper = new DBHelper(context);
        StudentTableQuery s = new StudentTableQuery(context, dbHelper);
    }

    private void initListner() {
        btnInsert.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    private void initUI() {
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnShow = (Button) findViewById(R.id.btnShow);
        etRoll = (EditText) findViewById(R.id.etRoll);
        etName = (EditText) findViewById(R.id.etName);
        etCity = (EditText) findViewById(R.id.etCity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsert:
                int roll = Integer.parseInt(etRoll.getText().toString().trim());
                String name = etName.getText().toString().trim();
                String city = etCity.getText().toString().trim();

                if (isUpdate == true)///update
                {
                    boolean flag = StudentTableQuery.updateStudentData(oldRoll, roll, name, city);
                    if (flag == true) {
                        Toast.makeText(context, "Updated SuccessFully", Toast.LENGTH_SHORT).show();
                        clearEditText();
                    } else {
                        Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else { //Insert
                    boolean flag = StudentTableQuery.insertStudentData(roll, name, city);
                    if (flag == true) {
                        Toast.makeText(context, "Inserted SuccessFully", Toast.LENGTH_SHORT).show();
                        clearEditText();
                    } else {
                        Toast.makeText(context, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;


            case R.id.btnShow:
                Intent intent = new Intent(context, ShowActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void clearEditText() {
        etRoll.setText("");
        etName.setText("");
        etCity.setText("");
    }
}
