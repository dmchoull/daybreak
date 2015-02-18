package com.dmchoull.daybreak;

import com.dmchoull.daybreak.models.Alarm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestFactory {
    public static Alarm createAlarm(int hour, int minute) {
        Alarm alarm = new Alarm(hour, minute);
        alarm.save();
        return alarm;
    }

    public static Alarm mockAlarm(Long id, Long time) {
        Alarm alarm = mock(Alarm.class);
        when(alarm.getId()).thenReturn(id);

        if (time == null) {
            time = System.currentTimeMillis();
        }

        when(alarm.getNextAlarmTime()).thenReturn(time);

        return alarm;
    }
}
