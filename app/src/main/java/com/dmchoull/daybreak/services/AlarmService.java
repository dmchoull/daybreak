package com.dmchoull.daybreak.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dmchoull.daybreak.Injector;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.ui.AlarmActivity;

import javax.inject.Inject;

public class AlarmService extends Service {
    public static final String EXTRA_ALARM_ID = "AlarmId";

    @Inject AlarmHelper alarmHelper;

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        ((Injector) getApplication()).inject(this);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        startAlarmActivity();

        resetAlarm(intent.getLongExtra(EXTRA_ALARM_ID, 0L));

        return super.onStartCommand(intent, flags, startId);
    }

    private void startAlarmActivity() {
        Intent alarmIntent = new Intent(this, AlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(alarmIntent);
    }

    private void resetAlarm(long alarmId) {
        Alarm alarm = Alarm.findById(Alarm.class, alarmId);
        alarmHelper.set(alarm);
    }
}
