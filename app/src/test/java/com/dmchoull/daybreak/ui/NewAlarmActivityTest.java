package com.dmchoull.daybreak.ui;

import android.widget.Button;
import android.widget.TimePicker;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.services.AlarmService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;


@RunWith(RobolectricTestRunner.class)
public class NewAlarmActivityTest {
    @Inject AlarmService alarmService;

    private NewAlarmActivity activity;

    @Before
    public void setUp() {
        TestHelper.init(this);
        activity = Robolectric.buildActivity(NewAlarmActivity.class).create().start().resume().get();
    }

    @Test
    public void createsAlarmWhenNewAlarmButtonClicked() {
        TimePicker timePicker = (TimePicker) activity.findViewById(R.id.timePicker);
        Button newAlarmButton = (Button) activity.findViewById(R.id.new_alarm_button);

        newAlarmButton.performClick();

        verify(alarmService).create(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }
}
