package com.dmchoull.daybreak.ui;

import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import com.dmchoull.daybreak.BuildConfig;
import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowListView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.mockAlarm;
import static com.dmchoull.daybreak.TestHelper.assertActivityStarted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
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
        assertThat(alarmList.getAdapter().getCount()).isEqualTo(2);
    }

    @Test
    public void launchesNewAlarmActivityWhenNewAlarmButtonClicked() {
        Button addAlarm = (Button) activity.findViewById(R.id.new_alarm_button);
        addAlarm.performClick();

        Intent startedActivity = getStartedActivity();
        assertActivityStarted(activity, startedActivity, EditAlarmActivity.class);
    }

    private Intent getStartedActivity() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        return shadowActivity.getNextStartedActivity();
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
        ShadowListView shadowListView = Shadows.shadowOf(alarmList);
        shadowListView.performItemClick(0);

        Intent startedActivity = getStartedActivity();
        assertActivityStarted(activity, startedActivity, EditAlarmActivity.class);
        assertThat(startedActivity.getLongExtra(EditAlarmActivity.EXTRA_ALARM_ID, -1L)).isEqualTo(1L);
    }
}
