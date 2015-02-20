package com.dmchoull.daybreak.ui;

import android.view.WindowManager;

import com.dmchoull.daybreak.TestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AlarmActivityTest {
    private AlarmActivity activity;

    @Before
    public void setUp() {
        TestHelper.init(this);

        ActivityController<AlarmActivity> controller = Robolectric.buildActivity(AlarmActivity.class).create().start().resume();
        activity = controller.get();
    }

    @Test
    public void setsWindowFlags() {
        int windowFlags = activity.getWindow().getAttributes().flags;
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON) == WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) == WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED) == WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        assertTrue((windowFlags & WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD) == WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }
}