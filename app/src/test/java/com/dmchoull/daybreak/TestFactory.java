package com.dmchoull.daybreak;

import com.dmchoull.daybreak.models.Alarm;

import org.joda.time.MutableDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestFactory {
    public static Alarm createAlarm(int hour, int minute) {
        Alarm alarm = new Alarm(getTime(hour, minute));
        alarm.save();
        return alarm;
    }

    private static long getTime(int hour, int minute) {
        MutableDateTime time = MutableDateTime.now();
        time.setHourOfDay(hour);
        time.setMinuteOfHour(minute);
        time.setSecondOfMinute(0);
        return time.getMillis();
    }

    public static Alarm mockAlarm(Long id, Long time) {
        Alarm alarm = mock(Alarm.class);
        when(alarm.getId()).thenReturn(id);

        if (time == null) {
            time = System.currentTimeMillis();
        }

        when(alarm.getTime()).thenReturn(time);

        return alarm;
    }
}
