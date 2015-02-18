package com.dmchoull.daybreak.helpers;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.dmchoull.daybreak.AlarmReceiver;
import com.dmchoull.daybreak.models.Alarm;

import java.util.List;

import javax.inject.Inject;

public class AlarmHelper {
    private final Context context;
    private final AlarmManager alarmManager;

    @Inject
    public AlarmHelper(Context context, AlarmManager alarmManager) {
        this.context = context;
        this.alarmManager = alarmManager;
    }

    public Alarm create(Integer hour, Integer minute) {
        Alarm alarm = new Alarm(hour, minute);
        alarm.save();
        return alarm;
    }

    public Alarm update(Long id, Integer hour, Integer minute) {
        Alarm alarm = Alarm.findById(Alarm.class, id);
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.save();
        return alarm;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void set(Alarm alarm) {
        PendingIntent alarmIntent = createPendingIntent(alarm.getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getNextAlarmTime(), alarmIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getNextAlarmTime(), alarmIntent);
        }
    }

    private PendingIntent createPendingIntent(Long id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, id.intValue(), intent, 0);
    }

    public void delete(Long id) {
        PendingIntent alarmIntent = createPendingIntent(id);
        alarmManager.cancel(alarmIntent);

        Alarm.deleteAll(Alarm.class, "id = ?", id.toString());
    }

    public List<Alarm> getAll() {
        return Alarm.listAll(Alarm.class);
    }
}
