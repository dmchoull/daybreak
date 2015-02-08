package com.dmchoull.daybreak;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class DaybreakApplication extends Application implements Injector {
    private ObjectGraph graph;

    @Override public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new AppModule()
        );
    }

    @Override public void inject(Object object) {
        graph.inject(object);
    }
}
