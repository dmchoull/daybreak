package com.dmchoull.daybreak.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.services.AlarmService;

import javax.inject.Inject;


public class NewAlarmActivity extends DaybreakBaseActivity {
    @Inject AlarmService alarmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void newAlarm(View view) {
        TimePicker time = (TimePicker) findViewById(R.id.timePicker);
        Integer hour = time.getCurrentHour();
        Integer minute = time.getCurrentMinute();

        alarmService.create(hour, minute);
    }
}
