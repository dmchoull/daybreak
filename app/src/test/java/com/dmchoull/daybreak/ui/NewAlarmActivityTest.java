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
public class NewAlarmActivityTest {
    @Inject AlarmService alarmService;

    private NewAlarmActivity activity;
    private Button newAlarmButton;
    private TimePicker timePicker;

    @Before
    public void setUp() {
        TestHelper.init(this);
        activity = Robolectric.buildActivity(NewAlarmActivity.class).create().start().resume().get();
        newAlarmButton = (Button) activity.findViewById(R.id.add_alarm_button);
        timePicker = (TimePicker) activity.findViewById(R.id.timePicker);
    }

    @Test
    public void createsAlarmWhenAddAlarmButtonClicked() {
        newAlarmButton.performClick();

        verify(alarmService).create(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Test
    public void setsAlarmWhenAddAlarmButtonClicked() {
        Alarm alarm = mock(Alarm.class);
        when(alarmService.create(anyInt(), anyInt())).thenReturn(alarm);

        newAlarmButton.performClick();

        verify(alarmService).set(alarm);
    }

    @Test
    public void launchesAlarmListActivityAfterAlarmCreated() {
        newAlarmButton.performClick();

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedActivity = shadowActivity.getNextStartedActivity();
        assertEquals(startedActivity.getComponent(), new ComponentName(activity, AlarmListActivity.class));
    }
}
