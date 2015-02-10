package com.dmchoull.daybreak.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.models.Alarm;

public class AlarmListActivity extends DaybreakBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);

        ListView alarmList = (ListView) findViewById(R.id.alarm_list);
        ArrayAdapter<Alarm> adapter = new ArrayAdapter<>(this, R.layout.alarm_list_item, Alarm.listAll(Alarm.class));
        alarmList.setAdapter(adapter);
    }
}
