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

    int count = 0;                                                                          //count tracks the number of CheckBoxes at any given moment in time
    int countCopy = 0;                                                                      //countCopy is never reduced- we need this to ensure that even if CheckBoxes in the middle are deleted, we still reach the latest CheckBox when checking to see which tasks are completed
    boolean[] arrayx = new boolean[100];                                                    //arrayx stores the existence state of any task (i.e. if the task has been created, set to true...if the task has been completed and cleared, set to false)- NOTE that the max number of tasks created is set to 100
    
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

    public void parseNewTask(View view) {                                                   //gets the due date and description of a new task and stores them as strings
        String date;
        String task;

        EditText newTaskDate = (EditText) findViewById(R.id.newDate);                       //find the EditText view in which the user has entered a due date for the new task
        EditText newTaskDescription = (EditText) findViewById(R.id.newDescription);         //find the EditText view in which the user has entered a description for the new task
        date = newTaskDate.getText().toString();                                            //convert the contents of the EditText view into "string" format
        task = newTaskDescription.getText().toString();                                     //convert the contents of the EditText view into "string" format
        addNewTask(date, task);                                                             //sends the strings containing the due date and task description to the addNewTask method + calls this method
        newTaskDate.setText("");                                                            //clears the content of the EditText view to ready the field for a new task to be entered
        newTaskDescription.setText("");                                                     //clears the content of the EditText view to ready the field for a new task to be entered
    }

    public void addNewTask(String date, String task) {

        if (countCopy == 100) {                                                             //IF the max number of tasks created allowable has been reached (this is defined by the size of arrayx)...
            Toast.makeText(this, "Too many tasks added", Toast.LENGTH_SHORT).show();        //create a toast (little pop-up black box) with text giving the user information
        }
        else {

            count++;                                                                        //increment count by 1
            countCopy++;                                                                    //increment countCopy by 1
            
            TextView newDateTextView = new TextView(this);                                  //create a new TextView which will contain the due date of the new task to be added
            newDateTextView.setText(date);                                                  //set this due date to be the date passed to this function from the parseNewTask function
            //newDateTextView.setId((1 + count));
            newDateTextView.setGravity(Gravity.CENTER);                                     //set the gravity of this TextView to: "center"

            TextView newTaskTextView = new TextView(this);                                  //create a new TextView which will contain the description of the new task to be added
            newTaskTextView.setText(task);                                                  //set this description to be the date passed to this function from the parseNewTask function
            newTaskTextView.setGravity(Gravity.CENTER);                                     //set the gravity of this TextView to: "center"
            //newTaskTextView.setId((2 + count));

            CheckBox newCheckBox = new CheckBox(this);                                      //create a new CheckBox which will contain information on whether or not the task has been completed
            newCheckBox.setGravity(Gravity.CENTER);                                         //set the gravity of this CheckBox to: "center"
            newCheckBox.setId((3 + count));                                                 //issue this CheckBox a unique id, so that we can later scan all CheckBoxes to detect which ones are checked
            arrayx[count] = true;                                                           //set the component of arrayx corresponding to this new task to "true", indicating that this task is live and HAS NOT been completed and cleared
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

    public void clearCompletedTasks(View view) {


        if (count == 0) {
            Toast.makeText(this, "There are no tasks to clear", Toast.LENGTH_SHORT).show(); //create a toast (little pop-up black box) with text giving the user information
        }
        else {
            LinearLayout newTask = (LinearLayout) findViewById(R.id.verticalLL);            //find the parent vertical linear layout that contains each of the tasks (this is the root vertical linear layout containing everything)

            for (int i = 1; i <= countCopy; i++)                                            //this FOR loop cycles through all the horizontal linear layouts containing different tasks and searches for the tasks that are checked "completed"
            {
                //LinearLayout maskLayout = (LinearLayout) findViewById(count);
                if (arrayx[i] == true) {                                                    //IF the state of the first task is true (true if the CheckBox for that task still exists i.e. hasn't been completed and removed yet)
                    CheckBox mask = (CheckBox) findViewById((3 + i));                       //find the first check box

                    if (mask.isChecked()) {                                                 //IF check box is checked...
                        LinearLayout maskLayout = (LinearLayout) mask.getParent();          //find the horizontal linear layout containing the first check box
                        maskLayout.removeAllViews();                                        //remove all the views from the horizontal linear layout containing the 2 TextBoxes and CheckBox for that particular task
                        newTask.removeView(maskLayout);                                     //remove the physical horizontal linear layout from the parent vertical linear layout
                        //((ViewGroup) maskLayout.getParent()).removeView(maskLayout);
                        //((ViewManager)maskLayout.getParent()).removeView(maskLayout);
                        arrayx[i] = false;
                        count--;
                    }

                }
            }
        }
    }
}
