package com.dmchoull.daybreak.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AlarmTest {

    @Test
    public void returnsHour() throws Exception {
        Alarm alarm = new Alarm(1423936500000L);
        assertThat(alarm.getHour()).isEqualTo(12);
    }

    @Test
    public void returnsMinute() throws Exception {
        Alarm alarm = new Alarm(1423936500000L);
        assertThat(alarm.getMinute()).isEqualTo(55);
    }
}