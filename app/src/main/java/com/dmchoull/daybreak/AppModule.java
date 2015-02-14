package com.dmchoull.daybreak;

import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.ui.AlarmListActivity;
import com.dmchoull.daybreak.ui.EditAlarmActivity;

import dagger.Module;

@Module(
        injects = {
                EditAlarmActivity.class,
                AlarmListActivity.class,
                AlarmHelper.class
        },
        complete = false
)
public class AppModule {
}