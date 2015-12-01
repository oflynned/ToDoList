package com.example.android.todolistgh;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    boolean dateOrdered;
    String data;

    DatabaseHelper databaseHelper;

    Button addButton, memoButton, clearButton;
    FloatingActionButton floatingMenu;
    TableLayout tableLayout;
    ArrayList<CheckBox> checkBoxes;
    CheckBox totalCheckBox;
    TextView orderByDate;

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

        addButton = (Button) findViewById(R.id.addButton);
        floatingMenu = (FloatingActionButton) findViewById(R.id.floatingMenu);
        memoButton = (Button) findViewById(R.id.memoButton);
        clearButton = (Button) findViewById(R.id.clearCompletedTasks);
        tableLayout = (TableLayout) findViewById(R.id.list_table);
        totalCheckBox = (CheckBox) findViewById(R.id.select_all);
        orderByDate = (TextView) findViewById(R.id.dateTitle);
        checkBoxes = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);

        populateTable(DatabaseHelper.SELECT_ALL_QUERY);
        databaseHelper.printTableContents(Database.TasksTable.TABLE_NAME);

        orderByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDateOrdered()){
                    setDateOrdered(true);
                    tableLayout.invalidate();
                    populateTable(DatabaseHelper.SELECT_BY_DATE_ASCENDING);
                    Toast.makeText(getBaseContext(), "Ordered by ascending date", Toast.LENGTH_SHORT).show();
                } else {
                    setDateOrdered(false);
                    tableLayout.invalidate();
                    populateTable(DatabaseHelper.SELECT_BY_DATE_DESCENDING);
                    Toast.makeText(getBaseContext(), "Ordered by descending date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddTaskDialog addTaskDialog = new AddTaskDialog();
                addTaskDialog.show(MainActivity.this.getFragmentManager(), "setAddDialogListener");
                addTaskDialog.setAddDialogListener(new AddTaskDialog.setAddTaskListener() {
                    @Override
                    public void onDoneClick(DialogFragment dialogFragment) {
                        if (addTaskDialog.getDate().equals("")) {
                            Toast.makeText(MainActivity.this, "Please add a date!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (addTaskDialog.getCategory().equals("")) {
                            Toast.makeText(MainActivity.this, "Please add a category!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (addTaskDialog.getDescription().equals("")) {
                            Toast.makeText(MainActivity.this, "Please add a description!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (!addTaskDialog.getDate().equals("")
                                && !addTaskDialog.getCategory().equals("")
                                && !addTaskDialog.getDescription().equals("")) {
                            databaseHelper.insertTask(addTaskDialog.getCategory(),
                                    addTaskDialog.getDescription(),
                                    addTaskDialog.getDate(),
                                    addTaskDialog.getRawDate(),
                                    false);
                            databaseHelper.printTableContents(Database.TasksTable.TABLE_NAME);
                            tableLayout.invalidate();
                            populateTable(DatabaseHelper.SELECT_ALL_QUERY);
                            Toast.makeText(MainActivity.this, "Task Added Successfully!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        floatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddTaskDialog addTaskDialog = new AddTaskDialog();
                addTaskDialog.show(MainActivity.this.getFragmentManager(), "setAddDialogListener");
                addTaskDialog.setAddDialogListener(new AddTaskDialog.setAddTaskListener() {
                    @Override
                    public void onDoneClick(DialogFragment dialogFragment) {
                        if (addTaskDialog.getDate().equals("")) {
                            Toast.makeText(MainActivity.this, "Please add a date!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (addTaskDialog.getCategory().equals("")) {
                            Toast.makeText(MainActivity.this, "Please add a category!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (addTaskDialog.getDescription().equals("")) {
                            Toast.makeText(MainActivity.this, "Please add a description!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (!addTaskDialog.getDate().equals("")
                                && !addTaskDialog.getCategory().equals("")
                                && !addTaskDialog.getDescription().equals("")) {
                            databaseHelper.insertTask(addTaskDialog.getCategory(),
                                    addTaskDialog.getDescription(),
                                    addTaskDialog.getDate(),
                                    addTaskDialog.getRawDate(),
                                    false);
                            databaseHelper.printTableContents(Database.TasksTable.TABLE_NAME);
                            tableLayout.invalidate();
                            populateTable(DatabaseHelper.SELECT_ALL_QUERY);
                            Toast.makeText(MainActivity.this, "Task Added Successfully!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        memoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SyncActivity.class));
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.removeAllTasks();
                tableLayout.invalidate();
                populateTable(DatabaseHelper.SELECT_ALL_QUERY);
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

    public void setDateOrdered(boolean dateOrdered){this.dateOrdered=dateOrdered;}
    public boolean isDateOrdered(){return dateOrdered;}

    /**
     * populates the database rows and columns into a programmatically added layout
     */
    public void populateTable(String query) {
        SQLiteDatabase readDb = databaseHelper.getReadableDatabase();

        final int currNumRows = tableLayout.getChildCount();
        if (currNumRows > 1)
            tableLayout.removeViewsInLayout(1, currNumRows - 1);

        final Cursor cursor = readDb.rawQuery(query, null);
        int numRows = cursor.getCount();
        System.out.println("Row count " + numRows);
        cursor.moveToFirst();

        for (int i = 0; i < numRows; i++) {
            final TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams tableRowParams =
                    new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(tableRowParams);

            //assign row via ID instantiation
            final int row = cursor.getInt(DatabaseHelper.COL_ID);

            //date
            final TextView dateField = new TextView(this);
            dateField.setText(cursor.getString(DatabaseHelper.COL_DUE_DATE));
            dateField.setGravity(Gravity.CENTER);

            //category
            final TextView categoryField = new TextView(this);
            categoryField.setText(cursor.getString(DatabaseHelper.COL_CATEGORY));
            categoryField.setGravity(Gravity.CENTER);

            //description
            final TextView descField = new TextView(this);
            descField.setText(cursor.getString(DatabaseHelper.COL_DESCRIPTION));
            descField.setGravity(Gravity.CENTER);

            if (i % 2 == 0) {
                tableRow.setBackgroundColor(ContextCompat
                        .getColor(getBaseContext(), R.color.colorPrimary));
                dateField.setTextColor(Color.WHITE);
                categoryField.setTextColor(Color.WHITE);
                descField.setTextColor(Color.WHITE);
            }

            final CheckBox checkBox = new CheckBox(this);
            checkBox.setId(cursor.getInt(0));
            checkBoxes.add(checkBox);
            if(cursor.getInt(6) == 1){
                checkBox.setChecked(true);
                strikeThrough(dateField, categoryField, descField, true);
                tableLayout.invalidate();
            } else {
                checkBox.setChecked(false);
                strikeThrough(dateField, categoryField, descField, false);
                tableLayout.invalidate();
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    databaseHelper.editChecked(row, isChecked);
                    strikeThrough(dateField, categoryField, descField, isChecked);
                    databaseHelper.printTableContents(Database.TasksTable.TABLE_NAME);
                }
            });

            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            new ContextThemeWrapper(MainActivity.this, R.style.LongClickDialog));
                    builder.setMessage("Would you like to alter your to-do list?")
                            .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseHelper.removeTask(row);
                                    tableLayout.invalidate();
                                    populateTable(DatabaseHelper.SELECT_ALL_QUERY);
                                    databaseHelper.printTableContents(Database.TasksTable.TABLE_NAME);
                                }
                            })
                            .setNegativeButton("Modify", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final EditTaskDialog editTaskDialog = new EditTaskDialog();

                                    setData(row, DatabaseHelper.COL_DUE_DATE);
                                    editTaskDialog.setDate(getData());

                                    setData(row, DatabaseHelper.COL_CATEGORY);
                                    editTaskDialog.setCategory(getData());

                                    setData(row, DatabaseHelper.COL_DESCRIPTION);
                                    editTaskDialog.setDescription(getData());

                                    editTaskDialog.show(MainActivity.this.getFragmentManager(), "setEditDialogListener");
                                    editTaskDialog.setEditDialogListener(new EditTaskDialog.setEditTaskListener() {
                                        @Override
                                        public void onDoneClick(DialogFragment dialogFragment) {

                                            if (editTaskDialog.getDate().equals("")) {
                                                Toast.makeText(MainActivity.this, "Please add a date!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            if (editTaskDialog.getCategory().equals("")) {
                                                Toast.makeText(MainActivity.this, "Please add a category!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            if (editTaskDialog.getDescription().equals("")) {
                                                Toast.makeText(MainActivity.this, "Please add a description!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            if (!editTaskDialog.getDate().equals("")
                                                    && !editTaskDialog.getCategory().equals("")
                                                    && !editTaskDialog.getDescription().equals("")) {

                                                databaseHelper.editTask(
                                                        row,
                                                        editTaskDialog.getCategory(),
                                                        editTaskDialog.getDescription(),
                                                        editTaskDialog.getDate(),
                                                        editTaskDialog.getRawDate(),
                                                        checkBox.isChecked());
                                                databaseHelper.printTableContents(Database.TasksTable.TABLE_NAME);
                                                tableLayout.invalidate();
                                                populateTable(DatabaseHelper.SELECT_ALL_QUERY);
                                                Toast.makeText(MainActivity.this, "Task modified successfully!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                    builder.create().show();
                    Toast.makeText(MainActivity.this, "id: " + row, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            //add views
            tableRow.addView(dateField);
            tableRow.addView(categoryField);
            tableRow.addView(descField);
            tableRow.addView(checkBox);

            tableLayout.addView(tableRow);

            cursor.moveToNext();
        }

        readDb.close();
        cursor.close();
    }

    /**
     * given the data set in the row/col mix, the value is given in string format
     * @return the value for the field as a string
     */
    public String getData(){return data;}

    /**
     * retrieves the appropriate data for the edit record dialog box
     * in order to populate the given fields with the appropriate values
     * as a generic function
     * @param row the given row to be accessed with respect to ID
     *            which is always the row number + 1
     * @param col the given column to be accessed where the column
     *            corresponds to the queries in the database helper class
     */
    public void setData(int row, int col){
        SQLiteDatabase readDb = databaseHelper.getReadableDatabase();
        Cursor cursor = readDb.rawQuery(DatabaseHelper.SELECT_ALL_QUERY, null);
        cursor.moveToFirst();
        cursor.move(row - 1);
        this.data = cursor.getString(col);
        readDb.close();
        cursor.close();
    }

    /**
     * strikes through the appropriate field by adding or removing bitwise operator flags
     * @param dateField the date field in the given row to be struck through or unstruck
     * @param categoryField the category field in the given row to be struck/unstruck
     * @param descField the desc field in the given row to be struck/unstruck
     * @param completed toggles whether or not the row is to be struck through or not
     */
    public void strikeThrough(TextView dateField, TextView categoryField,
                              TextView descField, boolean completed){
        if(completed) {
            dateField.setPaintFlags(dateField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            categoryField.setPaintFlags(dateField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            descField.setPaintFlags(dateField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            dateField.setPaintFlags(dateField.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            categoryField.setPaintFlags(dateField.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            descField.setPaintFlags(dateField.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
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
