package com.dmchoull.daybreak;

import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.services.AlarmService;
import com.dmchoull.daybreak.ui.AlarmActivity;
import com.dmchoull.daybreak.ui.AlarmListActivity;
import com.dmchoull.daybreak.ui.EditAlarmActivity;

import dagger.Module;

@Module(
        injects = {
                EditAlarmActivity.class,
                AlarmListActivity.class,
                AlarmActivity.class,
                AlarmHelper.class,
                AlarmService.class
        },
        complete = false
)
public class AppModule {
}