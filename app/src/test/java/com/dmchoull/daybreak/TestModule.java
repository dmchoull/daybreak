package com.dmchoull.daybreak;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.dmchoull.daybreak.ui.NewAlarmActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                NewAlarmActivity.class,
                NewAlarmActivityTest.class
        },
        overrides = true
)
public class TestModule {
    private final Application application;

    public TestModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton AlarmManager provideAlarmManager() {
        return (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
    }
}
