package com.dmchoull.daybreak.models;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String toString() {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }
}
