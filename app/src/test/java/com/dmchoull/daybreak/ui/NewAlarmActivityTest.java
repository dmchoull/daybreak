package com.dmchoull.daybreak.ui;

import android.widget.Button;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.models.Alarm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class NewAlarmActivityTest {
    private NewAlarmActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(NewAlarmActivity.class).create().get();
    }

    @Test
    public void createsAlarmWhenNewAlarmButtonClicked() {
        Button newAlarmButton = (Button) activity.findViewById(R.id.new_alarm_button);
        newAlarmButton.performClick();

        assertEquals(1, Alarm.count(Alarm.class));
    }
}
