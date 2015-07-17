package com.dmchoull.daybreak.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dmchoull.daybreak.R;

import java.io.IOException;

public class AlarmActivity extends DaybreakBaseActivity {
    private MediaPlayer mediaPlayer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
    }

    @Override protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

//        playAlarm();
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
}
