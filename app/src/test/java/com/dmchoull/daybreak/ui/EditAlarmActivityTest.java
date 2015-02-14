package com.dmchoull.daybreak.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;
import android.widget.TimePicker;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.services.AlarmService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
public class EditAlarmActivityTest {
    @Inject AlarmService alarmService;

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
    public void createsAlarmWhenAddAlarmButtonClicked() {
        saveButton.performClick();

        verify(alarmService).create(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Test
    public void setsAlarmWhenAddAlarmButtonClicked() {
        Alarm alarm = mock(Alarm.class);
        when(alarmService.create(anyInt(), anyInt())).thenReturn(alarm);

        saveButton.performClick();

        verify(alarmService).set(alarm);
    }

    @Test
    public void launchesAlarmListActivityAfterAlarmCreated() {
        saveButton.performClick();

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedActivity = shadowActivity.getNextStartedActivity();
        assertEquals(startedActivity.getComponent(), new ComponentName(activity, AlarmListActivity.class));
    }
}
