package com.dmchoull.daybreak.services;

import android.content.Intent;

import com.dmchoull.daybreak.ui.AlarmActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static com.dmchoull.daybreak.TestHelper.assertActivityStarted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AlarmServiceTest {
    private AlarmService alarmService;

    @Before
    public void setUp() throws Exception {
        alarmService = Robolectric.buildService(AlarmService.class).create().startCommand(0, 0).get();
    }

    @Test
    public void startsAlarmActivityWhenStarted() throws Exception {
        Intent intent = shadowOf(alarmService).getNextStartedActivity();
        assertActivityStarted(alarmService, intent, AlarmActivity.class);
        assertThat(intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}