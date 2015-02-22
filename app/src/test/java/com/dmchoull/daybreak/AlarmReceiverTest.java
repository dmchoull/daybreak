package com.dmchoull.daybreak;


import android.content.Intent;

import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.receivers.AlarmReceiver;
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
public class AlarmReceiverTest {
    @Inject AlarmHelper alarmHelper;

    private Alarm alarm;

    @Before
    public void setUp() throws Exception {
        TestHelper.init(this);

        alarm = createAlarm(9, 0);
        Intent intent = new Intent();
        intent.putExtra(AlarmReceiver.EXTRA_ALARM_ID, alarm.getId());
        new AlarmReceiver().onReceive(Robolectric.application, intent);
    }

    @Test
    public void startsAlarmActivityWhenStarted() {
        Intent startedIntent = shadowOf(Robolectric.application).peekNextStartedActivity();
        assertActivityStarted(Robolectric.application, startedIntent, AlarmActivity.class);
        assertThat(startedIntent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Test
    public void resetsAlarmWhenStarted() {
        Robolectric.runUiThreadTasksIncludingDelayedTasks();
        verify(alarmHelper).set(alarm);
    }
}