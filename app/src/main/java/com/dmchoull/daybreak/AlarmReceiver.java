package com.dmchoull.daybreak;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarm triggered");
        displayNotification(context);
    }

    private void displayNotification(Context context) {
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Alarm Triggered")
                .setWhen(System.currentTimeMillis())
                .getNotification();

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
        notification.setLatestEventInfo(context, "Alarm", "Alarm was triggered", notificationIntent);

        notification.defaults |= (Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
