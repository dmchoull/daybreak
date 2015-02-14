package com.dmchoull.daybreak.helpers;

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

import static com.dmchoull.daybreak.TestFactory.createAlarm;
import static com.dmchoull.daybreak.TestFactory.mockAlarm;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

        assertNotNull("Alarm was not created", alarm.getId());
    }

    @Test
    public void updatesAlarm() {
        Long id = createAlarm(12, 30).getId();
        alarmHelper.update(id, 5, 15);

        Alarm alarm = Alarm.findById(Alarm.class, id);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTime());

        assertEquals(5, calendar.get(Calendar.HOUR));
        assertEquals(15, calendar.get(Calendar.MINUTE));
    }

    @Test
    public void setsTheCorrectTime() {
        Alarm alarm = alarmHelper.create(8, 30);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTime());

        assertEquals(8, calendar.get(Calendar.HOUR));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
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
        assertNull("Alarm was not deleted", Alarm.findById(Alarm.class, id));
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

        assertEquals(2, alarmHelper.getAll().size());
    }
}
