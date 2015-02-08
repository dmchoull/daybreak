package com.dmchoull.daybreak.ui;

import com.dmchoull.daybreak.ui.NewAlarmActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NewAlarmActivityTest {
    private NewAlarmActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(NewAlarmActivity.class).create().get();
    }

    @Test
    public void testSomething() {
    }
}
