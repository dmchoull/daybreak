package com.dmchoull.daybreak.helpers;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.receivers.AlarmReceiver;

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
        PendingIntent alarmIntent = createPendingIntent(alarm.getId(), true);
        setAlarm(alarm.getNextAlarmTime(), alarmIntent);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void set(long alarmTimeInMillis) {
        PendingIntent alarmIntent = createPendingIntent(alarmTimeInMillis, false);
        setAlarm(alarmTimeInMillis, alarmIntent);
    }

    private PendingIntent createPendingIntent(Long id, boolean includeIdInExtras) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setPackage(getClass().getPackage().toString());

        if (includeIdInExtras) {
            intent.putExtra(AlarmReceiver.EXTRA_ALARM_ID, id);
        }

        return PendingIntent.getBroadcast(context, id.intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setAlarm(long alarmTimeInMillis, PendingIntent alarmIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, alarmIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, alarmIntent);
        }
    }

    public void delete(Long id) {
        PendingIntent alarmIntent = createPendingIntent(id, true);
        alarmManager.cancel(alarmIntent);

        Alarm.deleteAll(Alarm.class, "id = ?", id.toString());
    }

    public List<Alarm> getAll() {
        return Alarm.listAll(Alarm.class);
    }
}
