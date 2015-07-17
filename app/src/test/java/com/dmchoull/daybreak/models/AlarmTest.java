package com.dmchoull.daybreak.models;

import com.dmchoull.daybreak.BuildConfig;

import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AlarmTest {

    @Before
    public void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1424229441000L); // 2/17/2015, 10:17:21 PM
    }

    @Test
    public void returnsTimeForTodayIfAlarmTimeHasNotOccurredYet() throws Exception {
        Alarm alarm = new Alarm(22, 30);
        assertThat(alarm.getNextAlarmTime()).isEqualTo(1424230200000L); // 2/17/2015, 10:30:00 PM
    }

    @Test
    public void returnsTimeForTomorrowIfAlarmTimeHasPassed() throws Exception {
        Alarm alarm = new Alarm(14, 30);
        assertThat(alarm.getNextAlarmTime()).isEqualTo(1424287800000L); // 2/18/2015, 2:30:00 PM
    }
}