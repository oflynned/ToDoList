package com.example.android.todolistgh;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Diarmuid on 07/12/2015.
 */
public class NotificationHandler {

    Context context;
    MakeNotification n;

    NotificationHandler(Context context){
        this.context = context;
    }

    /**
     * Function which shows a notification at 12 the day before a task is due
     * More parameters still to be added
     * @param description description of task due
     */
    public void showNotification(String description)
    {
        final Calendar cld = Calendar.getInstance();

        int day = cld.get(Calendar.DATE);
        int time = cld.get(Calendar.HOUR_OF_DAY);
        if(time==12)
        {
            n.createNotification(description);

        }
    }

}
