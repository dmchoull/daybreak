package com.dmchoull.daybreak.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;

import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static com.dmchoull.daybreak.TestFactory.mockAlarm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class AlarmHelperTest {
    @Inject AlarmManager alarmManager;

    private AlarmHelper alarmHelper;

    @Before
    public void setUp() {
        TestHelper.init(this);
        alarmHelper = new AlarmHelper(Robolectric.application.getBaseContext(), alarmManager);
    }

    @Test
    public void createsAlarm() {
        Alarm alarm = alarmHelper.create(8, 30);

        assertThat(alarm.getId()).isNotNull();
    }

    @Test
    public void setsTheCorrectTime() {
        Alarm alarm = alarmHelper.create(16, 30);

        DateTime time = new DateTime(alarm.getTimeInMillis());

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
    public void setsAlarm() {
        Alarm alarm = mockAlarm(1L, 1423445044113L);
        alarmHelper.set(alarm);

        verify(alarmManager).set(eq(AlarmManager.RTC_WAKEUP), eq(1423445044113L), any(PendingIntent.class));
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
