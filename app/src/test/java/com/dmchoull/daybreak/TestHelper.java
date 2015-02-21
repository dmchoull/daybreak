package com.dmchoull.daybreak;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import org.robolectric.Robolectric;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestHelper {
    public static void init(Object testCase) {
        initMocks(testCase);
        ((Injector) Robolectric.application).inject(testCase);
    }

    public static void assertActivityStarted(Context context, Intent startedActivity, Class expectedClass) {
        assertThat(startedActivity).as("No activity was started").isNotNull();
        assertThat(startedActivity.getComponent()).isEqualTo(new ComponentName(context, expectedClass));
    }
}
