package com.dmchoull.daybreak;

import com.dmchoull.daybreak.services.AlarmService;
import com.dmchoull.daybreak.ui.AlarmListActivity;
import com.dmchoull.daybreak.ui.NewAlarmActivity;

import dagger.Module;

@Module(
        injects = {
                NewAlarmActivity.class,
                AlarmListActivity.class,
                AlarmService.class
        },
        complete = false
)
public class AppModule {
}