package com.example.lit;

import java.util.Date;

/**
 * Created by damon on 10/20/2017.
 * Edited by ammar on 10/21/2017.
 */

public abstract class HabitEvent implements HabitEventAddable{
    public String habitEventName;
    public Date date = new Date();
    public String eventComment;

    public HabitEvent(String habitEventName) {
        this.habitEventName = habitEventName;
        this.date = new Date();
    }

    public String getHabitEventName() {
        return habitEventName;
    }

    public void setHabitEventName(String habitEventName) {
        this.habitEventName = habitEventName;
    }

    public Date getEventDate() {
        return date;
    }

    public void setEventDate(Date date) {
        this.date = date;
    }

    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }

    @Override
    public String toString() {
        return "Habit Event{" +
                "habitEventName='" + habitEventName + '\'' +
                ", date=" + date + '\'' +
                ", comment=" + eventComment +
                '}';
    }

}
