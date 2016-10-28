package com.dmchoull.daybreak;

import com.orm.SugarApp;
import com.orm.SugarContext;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class DaybreakApplication extends SugarApp implements Injector {
    private ObjectGraph graph;

    @Override public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        JodaTimeAndroid.init(this);
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

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
