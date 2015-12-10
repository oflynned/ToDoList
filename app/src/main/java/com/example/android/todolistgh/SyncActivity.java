package com.example.android.todolistgh;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class SyncActivity extends AppCompatActivity {

    Connectivity connectivity = new Connectivity(this);

    EditText memo, newTaskDate, newTaskDescription, sendEmailText;
    Button sendEmail;

    String date, message;
}
