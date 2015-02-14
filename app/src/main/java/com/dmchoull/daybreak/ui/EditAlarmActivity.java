package com.dmchoull.daybreak.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.models.Alarm;
import com.dmchoull.daybreak.services.AlarmService;

import javax.inject.Inject;


public class EditAlarmActivity extends DaybreakBaseActivity {
    public static final String EXTRA_ALARM_ID = "ALARM_ID";

    @Inject AlarmService alarmService;
    private long alarmId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm);

        if (getIntent() != null) {
            alarmId = getIntent().getLongExtra(EXTRA_ALARM_ID, 0L);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveAlarm(View view) {
        TimePicker time = (TimePicker) findViewById(R.id.alarm_time);
        Integer hour = time.getCurrentHour();
        Integer minute = time.getCurrentMinute();

        Alarm alarm = createOrUpdateAlarm(hour, minute);

        alarmService.set(alarm);

        Intent intent = new Intent(this, AlarmListActivity.class);
        startActivity(intent);
    }

    private Alarm createOrUpdateAlarm(Integer hour, Integer minute) {
        Alarm alarm;

        if (alarmId > 0) {
            alarm = alarmService.update(alarmId, hour, minute);
        } else {
            alarm = alarmService.create(hour, minute);
        }

        return alarm;
    }
}
