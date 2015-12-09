package com.example.android.todolistgh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

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
     * Function which shows a notification at 12 the day before a task is due.
     * @param date the date the task being investigated is due
     * @param description description of task due
     */
    public void showNotification(int date, String description)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);

        n.createNotification(pi, date, description);
    }

}
