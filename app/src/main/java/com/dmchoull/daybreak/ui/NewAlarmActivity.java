package com.dmchoull.daybreak.ui;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.dmchoull.daybreak.AlarmReceiver;
import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.models.Alarm;

import java.util.Calendar;

import javax.inject.Inject;


public class NewAlarmActivity extends DaybreakBaseActivity {
    @Inject AlarmManager alarmManager;

    private PendingIntent alarmIntent;

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

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);

        setAlarm(hour, minute);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setAlarm(Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Alarm alarm = new Alarm(calendar.getTimeInMillis());
        alarm.save();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }
}
