package com.dmchoull.daybreak.ui;

import android.media.RingtoneManager;
import android.view.WindowManager;

import com.dmchoull.daybreak.BuildConfig;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.helpers.AlarmHelper;

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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AlarmActivityTest {
    @Inject AlarmHelper alarmHelper;

    private AlarmActivity activity;

    @Before
    public void setUp() {
        TestHelper.init(this);

        ActivityController<AlarmActivity> controller = Robolectric.buildActivity(AlarmActivity.class);
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

        verify(alarmHelper).set(currentTime.plusSeconds(10).getMillis());
    }
}
