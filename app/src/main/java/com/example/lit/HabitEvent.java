package com.example.lit;

import java.util.Date;

/**
 * Created by damon on 10/20/2017.
 * Edited by ammar on 10/21/2017.
 */

public abstract class HabitEvent implements HabitEventAddable{
    public String habitEventName;
    public Date date = new Date();
    public String habitComment;

    public HabitEvent(String habitEventName, Date date) {
        this.habitEventName = habitEventName;
        this.date = date;
    }

    public HabitEvent(String habitEventName) {
        this.habitEventName = habitEventName;
    }

    public String getHabitEventName() {
        return habitEventName;
    }

    public void setHabitEventName(String habitEventName) {
        this.habitEventName = habitEventName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHabitComment() {
        return habitComment;
    }

    public void setHabitComment(String habitComment) {
        this.habitComment = habitComment;
    }

    @Override
    public String toString() {
        return "Habit Event{" +
                "habitEventName='" + habitEventName + '\'' +
                ", date=" + date +
                ", comment=" + habitComment +
                '}';
    }

}
