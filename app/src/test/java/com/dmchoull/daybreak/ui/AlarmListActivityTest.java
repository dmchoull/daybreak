package com.dmchoull.daybreak.ui;

import com.dmchoull.daybreak.TestHelper;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AlarmListActivityTest {
    @Before
    public void setUp() {
        TestHelper.init(this);
        AlarmListActivity activity = Robolectric.buildActivity(AlarmListActivity.class).create().start().resume().get();
    }
}
