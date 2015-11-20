package com.example.android.todolistgh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void parseNewTask(View view) {                                                   //gets the due date and description of a new task and stores them as strings
        String memo;
        String task;

        EditText newTaskDate = (EditText) findViewById(R.id.newDate);                       //find the EditText view in which the user has entered a due date for the new task
        EditText newTaskDescription = (EditText) findViewById(R.id.newDescription);         //find the EditText view in which the user has entered a description for the new task
        memo = newTaskDate.getText().toString();                                            //convert the contents of the EditText view into "string" format
        task = newTaskDescription.getText().toString();                                     //convert the contents of the EditText view into "string" format
                                                                                           //sends the strings containing the due date and task description to the addNewTask method + calls this method
        newTaskDate.setText("");                                                            //clears the content of the EditText view to ready the field for a new task to be entered
        newTaskDescription.setText("");                                                     //clears the content of the EditText view to ready the field for a new task to be entered
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void sendToEmail(View view) {

        EditText memo = (EditText) findViewById(R.id.sendToEmail);
        String memoContent = memo.getText().toString();

        Intent sendEmailSummary = new Intent(Intent.ACTION_SENDTO);

        sendEmailSummary.setData(Uri.parse("mailto:")); // only email apps should handle this
        sendEmailSummary.putExtra(Intent.EXTRA_EMAIL, "guptas@tcd.ie");
        sendEmailSummary.putExtra(Intent.EXTRA_SUBJECT, ("MEMO- To-Do List App"));
        sendEmailSummary.putExtra(Intent.EXTRA_TEXT, memoContent);
        if (sendEmailSummary.resolveActivity(getPackageManager()) != null) {
                startActivity(sendEmailSummary);
        }
    }

}
