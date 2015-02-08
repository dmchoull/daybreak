package com.dmchoull.daybreak;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DaybreakBaseActivity extends ActionBarActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Injector) getApplication()).inject(this);
    }
}
