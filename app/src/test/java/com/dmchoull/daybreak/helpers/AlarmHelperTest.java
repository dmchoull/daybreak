package com.dmchoull.daybreak.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;

import com.dmchoull.daybreak.BuildConfig;
import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.receivers.AlarmReceiver;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowPendingIntent;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static com.dmchoull.daybreak.TestFactory.mockAlarm;
import static com.dmchoull.daybreak.TestHelper.assertActivityStarted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AlarmHelperTest {
    @Inject AlarmManager alarmManager;
    @Captor ArgumentCaptor<PendingIntent> pendingIntentCaptor;

    private AlarmHelper alarmHelper;

    @Before
    public void setUp() {
        TestHelper.init(this);
        alarmHelper = new AlarmHelper(RuntimeEnvironment.application.getBaseContext(), alarmManager);
    }

    @Test
    public void createsAlarm() {
        Alarm alarm = alarmHelper.create(8, 30);

        assertThat(alarm.getId()).isNotNull();
    }

    @Test
    public void setsTheCorrectTime() {
        Alarm alarm = alarmHelper.create(16, 30);

        DateTime time = new DateTime(alarm.getNextAlarmTime());

        assertThat(time.getHourOfDay()).isEqualTo(16);
        assertThat(time.getMinuteOfHour()).isEqualTo(30);
        assertThat(time.getSecondOfMinute()).isEqualTo(0);
    }

    @Test
    public void updatesAlarm() {
        Long id = createAlarm(12, 30).getId();
        Alarm alarm = alarmHelper.update(id, 5, 15);

        assertThat(alarm.getHour()).isEqualTo(5);
        assertThat(alarm.getMinute()).isEqualTo(15);
    }

    @Test
    public void setsAlarmGivenAlarm() {
        Alarm alarm = mockAlarm(1L, 1423445044113L);
        alarmHelper.set(alarm);

        verify(alarmManager).setExact(eq(AlarmManager.RTC_WAKEUP), eq(1423445044113L), any(PendingIntent.class));
    }

    @Test
    public void setCreatesPendingIntentForAlarmService() {
        Long alarmId = 1L;
        Alarm alarm = mockAlarm(alarmId, 1423445044113L);
        alarmHelper.set(alarm);

        verify(alarmManager).setExact(anyInt(), anyLong(), pendingIntentCaptor.capture());

        ShadowPendingIntent pendingIntent = Shadows.shadowOf(pendingIntentCaptor.getValue());
        assertTrue(pendingIntent.isBroadcastIntent());
        assertThat(pendingIntent.getRequestCode()).as("request code").isEqualTo(alarmId.intValue());
        assertThat(pendingIntent.getSavedIntent().getLongExtra(AlarmReceiver.EXTRA_ALARM_ID, -1L)).isEqualTo(alarmId);
        assertActivityStarted(RuntimeEnvironment.application, pendingIntent.getSavedIntent(), AlarmReceiver.class);
    }

    @Test
    public void snoozesAlarmUntilGivenTime() {
        Alarm alarm = mockAlarm(1L, 1423445044113L);
        DateTime snoozeTime = DateTime.now().plusSeconds(10);

        alarmHelper.snooze(alarm, snoozeTime);

        verify(alarmManager).setExact(eq(AlarmManager.RTC_WAKEUP), eq(snoozeTime.getMillis()), any(PendingIntent.class));
    }

    @Test
    public void snoozeCreatesPendingIntentForAlarmService() {
        Long alarmId = 1L;
        Alarm alarm = mockAlarm(alarmId, 1423445044113L);
        alarmHelper.snooze(alarm, DateTime.now().plusSeconds(10));

        verify(alarmManager).setExact(anyInt(), anyLong(), pendingIntentCaptor.capture());

        ShadowPendingIntent pendingIntent = Shadows.shadowOf(pendingIntentCaptor.getValue());
        assertTrue(pendingIntent.isBroadcastIntent());
        assertThat(pendingIntent.getRequestCode()).as("request code").isEqualTo(alarmId.intValue());
        assertThat(pendingIntent.getSavedIntent().getLongExtra(AlarmReceiver.EXTRA_ALARM_ID, -1L)).isEqualTo(alarmId);
        assertActivityStarted(RuntimeEnvironment.application, pendingIntent.getSavedIntent(), AlarmReceiver.class);
    }

    @Test
    public void deletesAlarm() {
        Long id = createAlarm(12, 30).getId();
        alarmHelper.delete(id);
        assertThat(Alarm.findById(Alarm.class, id)).isNull();
    }

    @Test
    public void cancelsDeletedAlarm() {
        Long id = createAlarm(12, 30).getId();
        alarmHelper.delete(id);
        verify(alarmManager).cancel(any(PendingIntent.class));
    }

    @Test
    public void returnsAllAlarms() {
        createAlarm(12, 30);
        createAlarm(17, 0);

        assertThat(alarmHelper.getAll().size()).isEqualTo(2);
    }
}
