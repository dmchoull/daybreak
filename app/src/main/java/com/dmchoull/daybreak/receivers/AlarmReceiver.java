package com.dmchoull.daybreak.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.dmchoull.daybreak.Injector;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.ui.AlarmActivity;

import javax.inject.Inject;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String EXTRA_ALARM_ID = "AlarmId";

    @Inject AlarmHelper alarmHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((Injector) context.getApplicationContext()).inject(this);

        startAlarmActivity(context);

        resetAlarm(intent.getLongExtra(EXTRA_ALARM_ID, 0L));
    }

    private void startAlarmActivity(Context context) {
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        context.startActivity(alarmIntent);
    }

    private void resetAlarm(final long alarmId) {
        if (alarmId < 1L) {
            return;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                Alarm alarm = Alarm.findById(Alarm.class, alarmId);
                alarmHelper.set(alarm);
            }
        }, 2000);
    }
}
