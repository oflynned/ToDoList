package com.example.android.todolistgh;

import android.provider.BaseColumns;

/**
 * Created by ed on 27/11/15.
 */
public class Database {

    public static final String DATABASE_NAME = "to_do_db";

    public Database(){

    }

    public static abstract class TasksTable implements BaseColumns {
        //column names starting from 0

        //table name - string
        public static final String TABLE_NAME = "tasks_table";
        //id for each row - int
        public static final String ID = "id";
        //category or subject encapsulated - string
        public static final String CATEGORY = "category";
        //task description - string
        public static final String TASK = "task";
        //time the task was added in millis - long
        public static final String TIME_ADDED = "time_added";
        //time in millis - long
        public static final String DUE_DATE = "due_date";
        //has the task been completed? boolean
        public static final String COMPLETED = "completed";
    }

}
