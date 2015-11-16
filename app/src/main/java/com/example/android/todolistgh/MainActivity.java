package com.example.android.todolistgh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int count = 0;
    int countCopy = 0;
    boolean[] arrayx = new boolean[100];
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void parseNewTask(View view) {
        String date;
        String task;

        EditText newTaskDate = (EditText) findViewById(R.id.newDate);
        EditText newTaskDescription = (EditText) findViewById(R.id.newDescription);
        date = newTaskDate.getText().toString();
        task = newTaskDescription.getText().toString();
        addNewTask(date, task);
        newTaskDate.setText("");
        newTaskDescription.setText("");
    }

    public void addNewTask(String date, String task) {

        if (countCopy == 100) {
            Toast.makeText(this, "Too many tasks added", Toast.LENGTH_SHORT).show();
        }
        else {

            count++;
            countCopy++;

            TextView newDateTextView = new TextView(this);
            newDateTextView.setText(date);
            //newDateTextView.setId((1 + count));
            newDateTextView.setGravity(Gravity.CENTER);

            TextView newTaskTextView = new TextView(this);
            newTaskTextView.setText(task);
            newTaskTextView.setGravity(Gravity.CENTER);
            //newTaskTextView.setId((2 + count));

            CheckBox newCheckBox = new CheckBox(this);
            newCheckBox.setGravity(Gravity.CENTER);
            newCheckBox.setId((3 + count));
            arrayx[count] = true;
            /*
            LinearLayout horizontalLL = new LinearLayout(this);

            LinearLayout newTask = (LinearLayout) findViewById(R.id.verticalLL);

            newTask.addView(horizontalLL);
            horizontalLL.addView(newDateTextView);
            horizontalLL.addView(newTaskTextView);
            horizontalLL.addView(newCheckBox);
            */
            LinearLayout newTask = (LinearLayout) findViewById(R.id.verticalLL);
            LinearLayout horizontalLL = new LinearLayout(this);
            horizontalLL.setId(count);
            horizontalLL.setWeightSum(1f);
            newTask.addView(horizontalLL);

            LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p1.weight = 0.33f;

            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p2.weight = 0.33f;

            LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p3.weight = 0.33f;

            horizontalLL.addView(newDateTextView, p1);
            horizontalLL.addView(newTaskTextView, p2);
            horizontalLL.addView(newCheckBox, p3);
        }
    }
}
