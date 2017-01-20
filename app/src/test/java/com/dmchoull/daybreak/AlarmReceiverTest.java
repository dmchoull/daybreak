package com.dmchoull.daybreak;

import android.content.Intent;

import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.receivers.AlarmReceiver;
import com.dmchoull.daybreak.ui.AlarmActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static com.dmchoull.daybreak.TestHelper.assertActivityStarted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AlarmReceiverTest {
    @Inject AlarmHelper alarmHelper;

    private Alarm alarm;

    @Before
    public void setUp() throws Exception {
        TestHelper.init(this);
        alarm = createAlarm(9, 0);
    }

    private void triggerWithId() {
        Intent intent = new Intent();
        intent.putExtra(AlarmReceiver.EXTRA_ALARM_ID, alarm.getId());
        new AlarmReceiver().onReceive(RuntimeEnvironment.application, intent);
    }

    private void triggerWithoutId() {
        new AlarmReceiver().onReceive(RuntimeEnvironment.application, new Intent());
    }

    @Test
    public void startsAlarmActivityWhenStarted() {
        triggerWithId();

        Intent startedIntent = Shadows.shadowOf(RuntimeEnvironment.application).peekNextStartedActivity();
        assertActivityStarted(RuntimeEnvironment.application, startedIntent, AlarmActivity.class);
        assertThat(startedIntent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
        assertThat(startedIntent.getFlags() & Intent.FLAG_ACTIVITY_NO_USER_ACTION).isEqualTo(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
    }

    @Test
    public void resetsAlarmWhenStartedWithAlarmIdExtra() {
        triggerWithId();

        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        verify(alarmHelper).set(alarm);
    }

    @Test
    public void doesNotResetWhenStartedWithoutAlarmIdExtra() {
        triggerWithoutId();

        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        verify(alarmHelper, never()).set(alarm);
    }
}
