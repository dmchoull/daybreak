package com.dmchoull.daybreak.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

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

import java.util.Arrays;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class AlarmListActivityTest {
    @Inject AlarmService alarmService;

    private AlarmListActivity activity;

    @Before
    public void setUp() {
        TestHelper.init(this);

        when(alarmService.getAll()).thenReturn(Arrays.asList(new Alarm(System.currentTimeMillis()), new Alarm(System.currentTimeMillis())));

        activity = Robolectric.buildActivity(AlarmListActivity.class).create().start().resume().visible().get();
    }

    @Test
    public void displaysAllAlarms() {
        ListView alarmList = (ListView) activity.findViewById(R.id.alarm_list);
        assertEquals(2, alarmList.getAdapter().getCount());
    }

    @Test
    public void launchesNewAlarmActivityWhenNewAlarmButtonClicked() {
        Button addAlarm = (Button) activity.findViewById(R.id.new_alarm_button);
        addAlarm.performClick();

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedActivity = shadowActivity.getNextStartedActivity();
        assertEquals(startedActivity.getComponent(), new ComponentName(activity, NewAlarmActivity.class));
    }

    @Test
    public void deletesAlarm() {
        Button view = new Button(activity);
        view.setTag(1234L);
        activity.deleteAlarm(view);

        verify(alarmService).delete(1234L);
    }
}
