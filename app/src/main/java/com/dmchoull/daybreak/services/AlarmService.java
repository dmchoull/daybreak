package com.dmchoull.daybreak.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dmchoull.daybreak.ui.AlarmActivity;

public class AlarmService extends Service {
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Intent alarmIntent = new Intent(this, AlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(alarmIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}
