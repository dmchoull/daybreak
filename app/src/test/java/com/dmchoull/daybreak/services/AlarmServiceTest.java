package com.dmchoull.daybreak.services;

import android.app.AlarmManager;
import android.app.PendingIntent;

import com.dmchoull.daybreak.TestHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class AlarmServiceTest {
    @Inject AlarmManager alarmManager;

    private AlarmService alarmService;

    @Before
    public void setUp() {
        TestHelper.init(this);
        alarmService = new AlarmService(Robolectric.application.getBaseContext(), alarmManager);
    }

    @Test
    public void createsAnAlarm() {
        Alarm alarm = alarmService.create(8, 30);

        assertNotNull("Alarm was not created", Alarm.findById(Alarm.class, alarm.getId()));
    }

    @Test
    public void setsAlarm() {
        Alarm alarm = new Alarm(1423445044113L);
        alarmService.set(alarm);

        verify(alarmManager).set(eq(AlarmManager.RTC_WAKEUP), eq(1423445044113L), any(PendingIntent.class));
    }

    @Test
    public void returnsAllAlarms() {
        createAlarm();
        createAlarm();

        assertEquals(2, alarmService.getAll().size());
    }

    private void createAlarm() {
        new Alarm(System.currentTimeMillis()).save();
    }
}
