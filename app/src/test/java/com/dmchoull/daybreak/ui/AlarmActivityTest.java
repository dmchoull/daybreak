package com.dmchoull.daybreak.ui;

import android.content.Intent;
import android.media.RingtoneManager;
import android.view.WindowManager;

import com.dmchoull.daybreak.BuildConfig;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowMediaPlayer;
import org.robolectric.shadows.util.DataSource;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AlarmActivityTest {
    @Inject AlarmHelper alarmHelper;

    private AlarmActivity activity;
    private Alarm alarm;

    @Before
    public void setUp() {
        TestHelper.init(this);

        alarm = createAlarm(9, 0);

        Intent intent = new Intent();
        intent.putExtra(AlarmActivity.EXTRA_ALARM_ID, alarm.getId());

        ActivityController<AlarmActivity> controller = Robolectric.buildActivity(AlarmActivity.class)
                .withIntent(intent);

        activity = controller.get();

        DataSource dataSource = DataSource.toDataSource(activity, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        ShadowMediaPlayer.addMediaInfo(dataSource, new ShadowMediaPlayer.MediaInfo(1, 0));

        controller.create().start().resume();
    }

    @Test
    public void setsWindowFlags() {
        int windowFlags = activity.getWindow().getAttributes().flags;
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON) == WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) == WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED) == WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD) == WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    @Test
    public void snoozesAlarm() {
        DateTime currentTime = DateTime.now();
        DateTimeUtils.setCurrentMillisFixed(currentTime.getMillis());

        activity.snoozeAlarm(null);

        verify(alarmHelper).snooze(alarm, currentTime.plusSeconds(10));
    }
}
