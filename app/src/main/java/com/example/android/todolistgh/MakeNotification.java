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
public class MakeNotification {
    Context context;

    public MakeNotification(Context context)
    {
        this.context = context;
    }

    /**
     * Creates the notification which directs you to
     * Main Activity when clicked
     * @param description The description of the task
     *                    due
     */
    public void createNotification (String description) {
        int mId = 1;
        Intent resultIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_new)
                .setContentTitle("To Do List")
                .setContentText("To do tomorrow: " + description);


        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mId, mBuilder.build());
    }
}
