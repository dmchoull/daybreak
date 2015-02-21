package com.dmchoull.daybreak.services;

import android.content.Intent;

import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.ui.AlarmActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static com.dmchoull.daybreak.TestHelper.assertActivityStarted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AlarmServiceTest {
    @Inject AlarmHelper alarmHelper;

    private AlarmService alarmService;
    private Alarm alarm;

    @Before
    public void setUp() throws Exception {
        TestHelper.init(this);
        alarm = createAlarm(9, 0);

        Intent intent = new Intent();
        intent.putExtra(AlarmService.EXTRA_ALARM_ID, alarm.getId());

        alarmService = Robolectric.buildService(AlarmService.class).withIntent(intent).create().startCommand(0, 0).get();
    }

    @Test
    public void startsAlarmActivityWhenStarted() {
        Intent intent = shadowOf(alarmService).getNextStartedActivity();
        assertActivityStarted(alarmService, intent, AlarmActivity.class);
        assertThat(intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Test
    public void resetsAlarmWhenStarted() {
        verify(alarmHelper).set(alarm);
    }
}