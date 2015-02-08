package com.dmchoull.daybreak;

import com.orm.SugarApp;

import dagger.ObjectGraph;

@SuppressWarnings("UnusedDeclaration")
public class TestDaybreakApplication extends SugarApp implements Injector {
    private ObjectGraph graph;

    @Override public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(new TestModule(this));
    }

    @Override public void inject(Object object) {
        graph.inject(object);
    }
}
