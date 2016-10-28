package com.dmchoull.daybreak.ui;

import android.content.ComponentName;
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
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditAlarmActivityNewTest {
    @Inject AlarmHelper alarmHelper;

    private EditAlarmActivity activity;
    private Button saveButton;
    private TimePicker timePicker;

    @Before
    public void setUp() {
        TestHelper.init(this);
        activity = Robolectric.buildActivity(EditAlarmActivity.class).create().start().resume().get();
        saveButton = (Button) activity.findViewById(R.id.save_alarm_button);
        timePicker = (TimePicker) activity.findViewById(R.id.alarm_time);
    }

    @Test
    public void createsAlarmWhenSaveAlarmButtonClicked() {
        saveButton.performClick();

        verify(alarmHelper).create(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Test
    public void setsAlarmWhenSaveAlarmButtonClicked() {
        Alarm alarm = mock(Alarm.class);
        when(alarmHelper.create(anyInt(), anyInt())).thenReturn(alarm);

        saveButton.performClick();

        verify(alarmHelper).set(alarm);
    }

    @Test
    public void launchesAlarmListActivityAfterAlarmSaved() {
        saveButton.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedActivity = shadowActivity.getNextStartedActivity();
        assertThat(startedActivity.getComponent()).isEqualTo(new ComponentName(activity, AlarmListActivity.class));
    }
}
