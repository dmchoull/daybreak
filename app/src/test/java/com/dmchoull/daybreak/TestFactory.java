package com.dmchoull.daybreak;

import com.dmchoull.daybreak.models.Alarm;

import java.util.Calendar;

public class TestFactory {
    public static Alarm createAlarm(int hour, int minute) {
        Alarm alarm = new Alarm(getTime(hour, minute));
        alarm.save();
        return alarm;
    }

    private static long getTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
