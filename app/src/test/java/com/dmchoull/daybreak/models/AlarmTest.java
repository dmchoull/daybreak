package com.dmchoull.daybreak.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AlarmTest {

    @Test
    public void returnsHour() throws Exception {
        Alarm alarm = new Alarm(1423936500000L);
        assertEquals(12, alarm.getHour());
    }

    @Test
    public void returnsMinute() throws Exception {
        Alarm alarm = new Alarm(1423936500000L);
        assertEquals(55, alarm.getMinute());
    }
}