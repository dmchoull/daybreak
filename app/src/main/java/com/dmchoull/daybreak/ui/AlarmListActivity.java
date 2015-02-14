package com.dmchoull.daybreak.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.services.AlarmService;

import javax.inject.Inject;

public class AlarmListActivity extends DaybreakBaseActivity {
    @Inject AlarmService alarmService;
    private AlarmListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);

        ListView alarmList = (ListView) findViewById(R.id.alarm_list);
        adapter = new AlarmListAdapter(this, R.layout.alarm_list_item, alarmService.getAll());
        alarmList.setAdapter(adapter);
    }

    public void addAlarm(View view) {
        Intent intent = new Intent(this, NewAlarmActivity.class);
        startActivity(intent);
    }

    public void deleteAlarm(View view) {
        Alarm alarm = (Alarm) view.getTag();
        alarmService.delete(alarm.getId());
        adapter.remove(alarm);
    }
}
