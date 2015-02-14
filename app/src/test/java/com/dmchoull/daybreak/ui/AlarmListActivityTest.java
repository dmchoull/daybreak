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
import org.robolectric.shadows.ShadowListView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AlarmListActivityTest {
    @Inject AlarmService alarmService;

    private AlarmListActivity activity;
    private ListView alarmList;

    @Before
    public void setUp() {
        TestHelper.init(this);

        List<Alarm> alarms = Arrays.asList(mockAlarm(1L), mockAlarm(2L));
        when(alarmService.getAll()).thenReturn(alarms);

        activity = Robolectric.buildActivity(AlarmListActivity.class).create().start().resume().visible().get();
        alarmList = (ListView) activity.findViewById(R.id.alarm_list);
    }

    private Alarm mockAlarm(Long id) {
        Alarm alarm = mock(Alarm.class);
        when(alarm.getId()).thenReturn(id);
        return alarm;
    }

    @Test
    public void displaysAllAlarms() {
        assertEquals(2, alarmList.getAdapter().getCount());
    }

    @Test
    public void launchesNewAlarmActivityWhenNewAlarmButtonClicked() {
        Button addAlarm = (Button) activity.findViewById(R.id.new_alarm_button);
        addAlarm.performClick();

        Intent startedActivity = getStartedActivity();
        assertActivityStarted(startedActivity, NewAlarmActivity.class);
    }

    private Intent getStartedActivity() {
        ShadowActivity shadowActivity = shadowOf(activity);
        return shadowActivity.getNextStartedActivity();
    }

    private void assertActivityStarted(Intent startedActivity, Class activityClass) {
        assertEquals(startedActivity.getComponent(), new ComponentName(activity, activityClass));
    }

    @Test
    public void deletesAlarm() {
        Button view = new Button(activity);
        view.setTag(mockAlarm(1234L));

        activity.deleteAlarm(view);

        verify(alarmService).delete(1234L);
    }

    @Test
    public void launchesActivityToEditExistingAlarm() {
        ShadowListView shadowListView = shadowOf(alarmList);
        shadowListView.performItemClick(0);

        Intent startedActivity = getStartedActivity();
        assertActivityStarted(startedActivity, NewAlarmActivity.class);
        assertEquals(1L, startedActivity.getLongExtra(NewAlarmActivity.EXTRA_ALARM_ID, -1L));
    }
}
