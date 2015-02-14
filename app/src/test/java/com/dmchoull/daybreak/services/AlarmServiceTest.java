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

import java.util.Calendar;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
    public void createsAlarm() {
        Alarm alarm = alarmService.create(8, 30);

        assertNotNull("Alarm was not created", alarm.getId());
    }

    @Test
    public void updatesAlarm() {
        Long id = createAlarm(12, 30);
        alarmService.update(id, 5, 15);

        Alarm alarm = Alarm.findById(Alarm.class, id);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTime());

        assertEquals(5, calendar.get(Calendar.HOUR));
        assertEquals(15, calendar.get(Calendar.MINUTE));
    }

    @Test
    public void setsTheCorrectTime() {
        Alarm alarm = alarmService.create(8, 30);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTime());

        assertEquals(8, calendar.get(Calendar.HOUR));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
    }

    @Test
    public void setsAlarm() {
        Alarm alarm = new Alarm(1423445044113L);
        alarmService.set(alarm);

        verify(alarmManager).set(eq(AlarmManager.RTC_WAKEUP), eq(1423445044113L), any(PendingIntent.class));
    }

    @Test
    public void deletesAlarm() {
        Long id = createAlarm(12, 30);
        alarmService.delete(id);
        assertNull("Alarm was not deleted", Alarm.findById(Alarm.class, id));
    }

    @Test
    public void returnsAllAlarms() {
        createAlarm(12, 30);
        createAlarm(17, 0);

        assertEquals(2, alarmService.getAll().size());
    }

    private Long createAlarm(int hour, int minute) {
        return new Alarm(getTime(hour, minute)).save();
    }

    private long getTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
