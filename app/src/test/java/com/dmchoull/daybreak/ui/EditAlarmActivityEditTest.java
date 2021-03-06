package com.dmchoull.daybreak.ui;

import android.content.Intent;
import android.widget.Button;
import android.widget.TimePicker;

import com.dmchoull.daybreak.BuildConfig;
import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static com.dmchoull.daybreak.TestHelper.assertActivityStarted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditAlarmActivityEditTest {
    @Inject AlarmHelper alarmHelper;

    private Button saveButton;
    private Button testButton;
    private TimePicker timePicker;
    private Alarm alarm;

    @Before
    public void setUp() {
        TestHelper.init(this);

        alarm = createAlarm(6, 30);
        Intent intent = new Intent();
        intent.putExtra(EditAlarmActivity.EXTRA_ALARM_ID, alarm.getId());

        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class)
                .withIntent(intent).create().start().resume().get();
        saveButton = (Button) activity.findViewById(R.id.save_alarm_button);
        testButton = (Button) activity.findViewById(R.id.test_alarm_button);
        timePicker = (TimePicker) activity.findViewById(R.id.alarm_time);
    }

    @Test
    public void displaysCurrentAlarmTimeWhenLaunched() {
        assertThat(timePicker.getCurrentHour().intValue()).isEqualTo(6);
        assertThat(timePicker.getCurrentMinute().intValue()).isEqualTo(30);
    }

    @Test
    public void updatesAlarmWhenSaveAlarmButtonClicked() {
        saveButton.performClick();

        verify(alarmHelper).update(alarm.getId(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Test
    public void setsAlarmWhenSaveAlarmButtonClicked() {
        Alarm alarm = mock(Alarm.class);
        when(alarmHelper.update(anyLong(), anyInt(), anyInt())).thenReturn(alarm);

        saveButton.performClick();

        verify(alarmHelper).set(alarm);
    }

    @Test
    public void startsAlarmActivityWhenTestAlarmButtonClicked() {
        testButton.performClick();

        Intent startedIntent = Shadows.shadowOf(RuntimeEnvironment.application).peekNextStartedActivity();
        assertActivityStarted(RuntimeEnvironment.application, startedIntent, AlarmActivity.class);
        assertThat(startedIntent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
        assertThat(startedIntent.getFlags() & Intent.FLAG_ACTIVITY_NO_USER_ACTION).isEqualTo(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        assertThat(startedIntent.getLongExtra(AlarmActivity.EXTRA_ALARM_ID, 0L)).isEqualTo(alarm.getId());
    }
}
