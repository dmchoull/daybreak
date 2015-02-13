package com.dmchoull.daybreak.services;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.dmchoull.daybreak.AlarmReceiver;
import com.dmchoull.daybreak.models.Alarm;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class AlarmService {
    private final Context context;
    private final AlarmManager alarmManager;

    @Inject
    public AlarmService(Context context, AlarmManager alarmManager) {
        this.context = context;
        this.alarmManager = alarmManager;
    }

    public Alarm create(Integer hour, Integer minute) {
        Calendar calendar = getCalendar(hour, minute);

        Alarm alarm = new Alarm(calendar.getTimeInMillis());
        alarm.save();

        return alarm;
    }

    private Calendar getCalendar(Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void set(Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTime(), alarmIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), alarmIntent);
        }
    }

    public List<Alarm> getAll() {
        return Alarm.listAll(Alarm.class);
    }
}
