package com.dmchoull.daybreak.models;

import com.orm.SugarRecord;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Alarm extends SugarRecord {
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

    public long getNextAlarmTime() {
        DateTime alarmTime = getDateTime();

        if (alarmTime.isBeforeNow()) {
            return alarmTime.plusDays(1).getMillis();
        } else {
            return alarmTime.getMillis();
        }
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
        DateTimeFormatter formatter = DateTimeFormat.forPattern("h:mm:ss a");
        return formatter.print(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        return getId().equals(alarm.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
