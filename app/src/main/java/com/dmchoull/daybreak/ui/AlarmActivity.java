package com.dmchoull.daybreak.ui;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dmchoull.daybreak.R;

public class AlarmActivity extends DaybreakBaseActivity {
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
    }

    public void dismissAlarm(View view) {
        finish();
    }
}
