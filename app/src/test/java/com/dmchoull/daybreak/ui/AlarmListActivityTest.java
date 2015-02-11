package com.dmchoull.daybreak.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.TestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AlarmListActivityTest {
    private AlarmListActivity activity;

    @Before
    public void setUp() {
        TestHelper.init(this);
        activity = Robolectric.buildActivity(AlarmListActivity.class).create().start().resume().get();
    }

    @Test
    public void launchesNewAlarmActivityWhenNewAlarmButtonClicked() {
        Button addAlarm = (Button) activity.findViewById(R.id.new_alarm_button);
        addAlarm.performClick();

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedActivity = shadowActivity.getNextStartedActivity();
        assertEquals(startedActivity.getComponent(), new ComponentName(activity, NewAlarmActivity.class));
    }
}
