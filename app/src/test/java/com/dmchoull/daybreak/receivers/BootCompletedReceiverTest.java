package com.dmchoull.daybreak.receivers;

import com.dmchoull.daybreak.BuildConfig;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.Arrays;

import javax.inject.Inject;

import static com.dmchoull.daybreak.TestFactory.mockAlarm;
import static com.dmchoull.daybreak.TestHelper.init;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class BootCompletedReceiverTest {
    @Inject AlarmHelper alarmHelper;
    private Alarm alarm1;
    private Alarm alarm2;

    @Before
    public void setUp() {
        init(this);
        ShadowLog.stream = System.out;

        alarm1 = mockAlarm(1L, 1423445044113L);
        alarm2 = mockAlarm(2L, 1423445055549L);

        when(alarmHelper.getAll()).thenReturn(Arrays.asList(alarm1, alarm2));
    }

    @Test
    public void setsAllAlarmsWhenTriggered() {
        BootCompletedReceiver receiver = new BootCompletedReceiver();
        receiver.onReceive(RuntimeEnvironment.application, null);

        verify(alarmHelper).set(alarm1);
        verify(alarmHelper).set(alarm2);
    }
}