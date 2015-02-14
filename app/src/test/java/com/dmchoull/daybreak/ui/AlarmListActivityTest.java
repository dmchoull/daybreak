package com.dmchoull.daybreak.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

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

import static com.dmchoull.daybreak.TestFactory.mockAlarm;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AlarmListActivityTest {
    @Inject AlarmHelper alarmHelper;

    private AlarmListActivity activity;
    private ListView alarmList;

    @Before
    public void setUp() {
        TestHelper.init(this);

        List<Alarm> alarms = Arrays.asList(mockAlarm(1L, null), mockAlarm(2L, null));
        when(alarmHelper.getAll()).thenReturn(alarms);

        activity = Robolectric.buildActivity(AlarmListActivity.class).create().start().resume().visible().get();
        alarmList = (ListView) activity.findViewById(R.id.alarm_list);
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
        assertActivityStarted(startedActivity, EditAlarmActivity.class);
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
        view.setTag(mockAlarm(1234L, null));

        activity.deleteAlarm(view);

        verify(alarmHelper).delete(1234L);
    }

    @Test
    public void launchesActivityToEditExistingAlarm() {
        ShadowListView shadowListView = shadowOf(alarmList);
        shadowListView.performItemClick(0);

        Intent startedActivity = getStartedActivity();
        assertActivityStarted(startedActivity, EditAlarmActivity.class);
        assertEquals(1L, startedActivity.getLongExtra(EditAlarmActivity.EXTRA_ALARM_ID, -1L));
    }
}
