package com.example.android.todolistgh;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Diarmuid on 30/11/2015.
 */
public class Notification {
    Context context;

    public void Notification(Context context)
    {
        this.context = context;
    }

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("To Do List")
            .setContentText("Something is due");

    Intent resultIntent = new Intent (context, MainActivity.class);

    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    //stackBuilder.addParentStack(MainActivity.class);
    //stackBuilder.addNextIntent(resultIntent);
    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    //mBuilder.setContentIntent(resultPendingIntent);
    //NotificationManager mNotificationManager =
    //        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    //mNotificationManager.notify(mId, mBuilder.build());
}
