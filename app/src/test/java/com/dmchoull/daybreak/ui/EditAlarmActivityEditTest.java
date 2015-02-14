package com.dmchoull.daybreak.ui;

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

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
public class EditAlarmActivityEditTest {
    @Inject AlarmService alarmService;

    private Button saveButton;
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
        timePicker = (TimePicker) activity.findViewById(R.id.alarm_time);
    }

    @Test
    public void displaysCurrentAlarmTimeWhenLaunched() {
        assertEquals(6, timePicker.getCurrentHour().intValue());
        assertEquals(30, timePicker.getCurrentMinute().intValue());
    }

    @Test
    public void updatesAlarmWhenSaveAlarmButtonClicked() {
        saveButton.performClick();

        verify(alarmService).update(alarm.getId(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Test
    public void setsAlarmWhenSaveAlarmButtonClicked() {
        Alarm alarm = mock(Alarm.class);
        when(alarmService.update(anyLong(), anyInt(), anyInt())).thenReturn(alarm);

        saveButton.performClick();

        verify(alarmService).set(alarm);
    }
}
