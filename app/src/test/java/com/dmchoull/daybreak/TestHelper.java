package com.dmchoull.daybreak;

import org.robolectric.Robolectric;

import static org.mockito.MockitoAnnotations.initMocks;

public class TestHelper {
    public static void init(Object testCase) {
        initMocks(testCase);
        ((Injector) Robolectric.application).inject(testCase);
    }
}
