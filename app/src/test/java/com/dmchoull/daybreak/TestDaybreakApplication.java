package com.dmchoull.daybreak;

import android.app.Application;

import dagger.ObjectGraph;

@SuppressWarnings("UnusedDeclaration")
public class TestDaybreakApplication extends Application implements Injector {
    private ObjectGraph graph;

    @Override public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(new TestModule(this));
    }

    @Override public void inject(Object object) {
        graph.inject(object);
    }
}
