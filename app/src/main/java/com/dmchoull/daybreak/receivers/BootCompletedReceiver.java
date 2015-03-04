package com.dmchoull.daybreak.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dmchoull.daybreak.Injector;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import javax.inject.Inject;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Inject AlarmHelper alarmHelper;

    @Override public void onReceive(Context context, Intent intent) {
        Log.d("BootCompletedReceiver", "Resetting all alarms");
        ((Injector) context.getApplicationContext()).inject(this);

        for (Alarm alarm : alarmHelper.getAll()) {
            Log.d("BootCompletedReceiver", "Setting alarm " + alarm.getId());
            alarmHelper.set(alarm);
        }
    }
}
