package com.dmchoull.daybreak;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(library = true)
public class AndroidModule {
    private final Application application;

    public AndroidModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton AlarmManager provideAlarmManager() {
        return (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
    }
}