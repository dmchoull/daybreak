package com.dmchoull.daybreak.models;

import com.orm.SugarRecord;

public class Alarm extends SugarRecord<Alarm> {
    long time;

    @SuppressWarnings("UnusedDeclaration") public Alarm() {
    }

    public Alarm(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
