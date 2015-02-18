package com.dmchoull.daybreak.models;

import com.orm.SugarRecord;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Alarm extends SugarRecord<Alarm> {
    int hour;
    int minute;

    @SuppressWarnings("UnusedDeclaration")
    public Alarm() {
    }

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long getTimeInMillis() {
        return getDateTime().getMillis();
    }

    private DateTime getDateTime() {
        MutableDateTime time = MutableDateTime.now();
        time.setHourOfDay(hour);
        time.setMinuteOfHour(minute);
        time.setSecondOfMinute(0);
        return time.toDateTime();
    }

    public String toString() {
        DateTime date = getDateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
        return formatter.print(date);
    }
}
