package com.dmchoull.daybreak;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.dmchoull.daybreak.services.AlarmService;
import com.dmchoull.daybreak.services.AlarmServiceTest;
import com.dmchoull.daybreak.ui.AlarmListActivity;
import com.dmchoull.daybreak.ui.AlarmListActivityTest;
import com.dmchoull.daybreak.ui.NewAlarmActivity;
import com.dmchoull.daybreak.ui.NewAlarmActivityTest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
        injects = {
                NewAlarmActivity.class,
                NewAlarmActivityTest.class,
                AlarmListActivity.class,
                AlarmListActivityTest.class,
                AlarmService.class,
                AlarmServiceTest.class
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

    @Provides @Singleton AlarmService provideAlarmService() {
        return mock(AlarmService.class);
    }
}
