package com.example.lit;

import java.util.Date;

/**
 * Created by damon on 10/20/2017.
 */

public abstract class Habit implements HabitAddable{

    private String title;
    private Date date;
    public abstract String habitType();

    public Habit(String title) {
        this.title = title;
        this.date = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setHabitTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "habitName='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
