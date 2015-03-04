package com.dmchoull.daybreak;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.helpers.AlarmHelperTest;
import com.dmchoull.daybreak.receivers.AlarmReceiver;
import com.dmchoull.daybreak.receivers.BootCompletedReceiver;
import com.dmchoull.daybreak.receivers.BootCompletedReceiverTest;
import com.dmchoull.daybreak.ui.AlarmActivity;
import com.dmchoull.daybreak.ui.AlarmActivityTest;
import com.dmchoull.daybreak.ui.AlarmListActivity;
import com.dmchoull.daybreak.ui.AlarmListActivityTest;
import com.dmchoull.daybreak.ui.EditAlarmActivity;
import com.dmchoull.daybreak.ui.EditAlarmActivityEditTest;
import com.dmchoull.daybreak.ui.EditAlarmActivityNewTest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
        injects = {
                EditAlarmActivity.class,
                EditAlarmActivityNewTest.class,
                EditAlarmActivityEditTest.class,
                AlarmListActivity.class,
                AlarmListActivityTest.class,
                AlarmActivity.class,
                AlarmActivityTest.class,
                AlarmHelper.class,
                AlarmHelperTest.class,
                AlarmReceiver.class,
                AlarmReceiverTest.class,
                BootCompletedReceiver.class,
                BootCompletedReceiverTest.class
        },
        overrides = true
)
public class TestModule {
    private final Application application;

    public TestModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton AlarmManager provideAlarmManager() {
        return mock(AlarmManager.class);
    }

    @Provides @Singleton AlarmHelper provideAlarmHelper() {
        return mock(AlarmHelper.class);
    }
}
