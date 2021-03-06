package com.dmchoull.daybreak.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import javax.inject.Inject;

public class AlarmListActivity extends DaybreakBaseActivity {
    @Inject AlarmHelper alarmHelper;
    private AlarmListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);
    }

    @Override protected void onStart() {
        super.onStart();

        ListView alarmList = (ListView) findViewById(R.id.alarm_list);
        adapter = new AlarmListAdapter(this, alarmHelper.getAll());
        alarmList.setAdapter(adapter);

        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAlarm(id);
            }
        });
    }

    public void addAlarm(View view) {
        Intent intent = new Intent(this, EditAlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void deleteAlarm(View view) {
        Alarm alarm = (Alarm) view.getTag();
        alarmHelper.delete(alarm.getId());
        adapter.remove(alarm);
    }

    public void showAlarm(long id) {
        Intent intent = new Intent(this, EditAlarmActivity.class);
        intent.putExtra(EditAlarmActivity.EXTRA_ALARM_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
