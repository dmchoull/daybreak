package com.dmchoull.daybreak.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dmchoull.daybreak.R;
import com.dmchoull.daybreak.helpers.AlarmHelper;
import com.dmchoull.daybreak.models.Alarm;

import org.joda.time.DateTime;

import java.io.IOException;

import javax.inject.Inject;

public class AlarmActivity extends DaybreakBaseActivity {
    public static final String EXTRA_ALARM_ID = "AlarmId";

    @Inject AlarmHelper alarmHelper;

    private MediaPlayer mediaPlayer;
    private Alarm alarm;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        long alarmId = getIntent().getLongExtra(EXTRA_ALARM_ID, 0L);
        alarm = Alarm.findById(Alarm.class, alarmId);
    }

    @Override protected void onStart() {
        super.onStart();
        TextView alarmIdTextView = (TextView) findViewById(R.id.alarm_id_text);
        alarmIdTextView.setText(String.valueOf(alarm.getId()));
    }

    @Override protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        playAlarm();
    }

    private void playAlarm() {
        mediaPlayer = new MediaPlayer();
        Uri toneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        try {
            mediaPlayer.setDataSource(this, toneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override protected void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }

    public void dismissAlarm(View view) {
        finish();
    }

    public void snoozeAlarm(View view) {
        // TODO: make snooze time configurable
        DateTime snoozeTime = DateTime.now().plusSeconds(10);
        alarmHelper.snooze(alarm, snoozeTime);
        finish();
    }
}
