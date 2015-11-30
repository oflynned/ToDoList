package com.example.android.todolistgh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    int count = 0;
    int countCopy = 0;
    boolean[] arrayx = new boolean[100];

    public void openMemo ()
    {
        Intent intent = new Intent(this, SyncActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    /**
     * function reads the task date and description entered
     * passes this data to the addNewTask function
     *
     * @param view "add" Button view
     */
    public void parseNewTask(View view) {                                                   //gets the due date and description of a new task and stores them as strings
        String date;
        String task;

        EditText newTaskDate = (EditText) findViewById(R.id.newDate);                       //find the EditText view in which the user has entered a due date for the new task
        EditText newTaskDescription = (EditText) findViewById(R.id.newDescription);         //find the EditText view in which the user has entered a description for the new task
        date = newTaskDate.getText().toString();                                            //convert the contents of the EditText view into "string" format
        task = newTaskDescription.getText().toString();                                     //convert the contents of the EditText view into "string" format
        if(validateNewTask(date, task)) {
            addNewTask(date, task);                                                         //sends the strings containing the due date and task description to the addNewTask method + calls this method
            newTaskDate.setText("");                                                        //clears the content of the EditText view to ready the field for a new task to be entered
            newTaskDescription.setText("");                                                 //clears the content of the EditText view to ready the field for a new task to be entered
        }
    }

    /**
     * function checks that the date is valid
     * function checks that the description has some content
     *
     * @param date this holds the due date of the new task being added
     * @param task this holds the description of the new task being added
     * @return valid or not valid
     */
    public Boolean validateNewTask(String date, String task) {
        Boolean check = true;
        if (date.isEmpty()) {
            Toast.makeText(this, "Please enter a due date", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if (!date.isEmpty() && date.charAt(2) != '/') {
            Toast.makeText(this, "Incorrect date format", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if (task.isEmpty()) {
            Toast.makeText(this, "Please enter a task description", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    /**
     * function reads the task date and description entered
     * passes this data to the addNewTask function
     * @param date this holds the due date of the new task being added
     * @param task this holds the description of the new task being added
     */
    public void addNewTask(String date, String task) {

        if (countCopy == 100) {                                                             //IF the max number of tasks created allowable has been reached (this is defined by the size of arrayx)...
            Toast.makeText(this, "Too many tasks added", Toast.LENGTH_SHORT).show();        //create a toast (little pop-up black box) with text giving the user information
        }
        else {

            count++;                                                                        //increment count by 1
            countCopy++;                                                                    //increment countCopy by 1
            
            TextView newDateTextView = new TextView(this);                                  //create a new TextView which will contain the due date of the new task to be added
            newDateTextView.setText(date);                                                  //set this due date to be the date passed to this function from the parseNewTask function
            newDateTextView.setGravity(Gravity.CENTER);                                     //set the gravity of this TextView to: "center"

            TextView newTaskTextView = new TextView(this);                                  //create a new TextView which will contain the description of the new task to be added
            newTaskTextView.setText(task);                                                  //set this description to be the date passed to this function from the parseNewTask function
            newTaskTextView.setGravity(Gravity.CENTER);                                     //set the gravity of this TextView to: "center"
            /*
            newTaskTextView.setClickable(true);
            newTaskTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // do your work here
                }
            });
            */

            CheckBox newCheckBox = new CheckBox(this);                                      //create a new CheckBox which will contain information on whether or not the task has been completed
            newCheckBox.setGravity(Gravity.CENTER);                                         //set the gravity of this CheckBox to: "center"
            newCheckBox.setId((3 + countCopy));                                             //issue this CheckBox a unique id, so that we can later scan all CheckBoxes to detect which ones are checked
            arrayx[countCopy] = true;                                                       //set the component of arrayx corresponding to this new task to "true", indicating that this task is live and HAS NOT been completed and cleared
            /*
            LinearLayout horizontalLL = new LinearLayout(this);

            LinearLayout newTask = (LinearLayout) findViewById(R.id.verticalLL);

            newTask.addView(horizontalLL);
            horizontalLL.addView(newDateTextView);
            horizontalLL.addView(newTaskTextView);
            horizontalLL.addView(newCheckBox);
            */
            LinearLayout newTask = (LinearLayout) findViewById(R.id.verticalLL);            //find the parent vertical linear layout that contains all the tasks created (NOTE: this is the root vertical linear layout)
            LinearLayout horizontalLL = new LinearLayout(this);                             //create a new horizontal linear layout which will contain the details of the new task to be added (in 3 Views, 2 TextBoxes and 1 CheckBox)
            horizontalLL.setId(count);
            horizontalLL.setWeightSum(1f);
            newTask.addView(horizontalLL);

            LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p1.weight = 0f;
            p1.leftMargin = 64;

            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p2.weight = 1f;

            LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p3.weight = 0f;
            p3.rightMargin = 64;

            horizontalLL.addView(newDateTextView, p1);
            horizontalLL.addView(newTaskTextView, p2);
            horizontalLL.addView(newCheckBox, p3);
        }
    }

    /**
     * function cycles through all created tasks
     * searches for all tasks with checked CheckBoxes
     * removes all views and the linear layout containing details of each completed task
     * @param view "clear" Button view
     */
    public void clearCompletedTasks(View view) {


        if (count == 0) {
            Toast.makeText(this, "There are no tasks to clear", Toast.LENGTH_SHORT).show(); //create a toast (little pop-up black box) with text giving the user information
        }
        else {
            LinearLayout newTask = (LinearLayout) findViewById(R.id.verticalLL);            //find the parent vertical linear layout that contains each of the tasks (this is the root vertical linear layout containing everything)

            for (int i = 1; i <= countCopy; i++)                                            //this FOR loop cycles through all the horizontal linear layouts containing different tasks and searches for the tasks that are checked "completed"
            {
                //LinearLayout maskLayout = (LinearLayout) findViewById(count);
                if (arrayx[i]) {                                                    //IF the state of the first task is true (true if the CheckBox for that task still exists i.e. hasn't been completed and removed yet)
                    CheckBox mask = (CheckBox) findViewById((3 + i));                       //find the first check box

                    if (mask.isChecked()) {                                                 //IF check box is checked...
                        LinearLayout maskLayout = (LinearLayout) mask.getParent();          //find the horizontal linear layout containing the first check box
                        maskLayout.removeAllViews();                                        //remove all the views from the horizontal linear layout containing the 2 TextBoxes and CheckBox for that particular task
                        newTask.removeView(maskLayout);                                     //remove the physical horizontal linear layout from the parent vertical linear layout
                        arrayx[i] = false;
                        count--;
                    }
                }
            }
            if (count == 0) {
                Toast.makeText(this, "All tasks completed!", Toast.LENGTH_SHORT).show();    //if all tasks have been cleared, notify user that all tasks have been completed
            }
        }
    }
}
