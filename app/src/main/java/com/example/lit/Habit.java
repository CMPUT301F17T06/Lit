package com.example.lit;

import java.util.Date;

/**
 * Created by damon on 10/20/2017.
 */

public abstract class Habit implements HabitAddable{

    private String habitName;
    private Date date = new Date();

    public Habit(String habitName, Date date) {
        this.habitName = habitName;
        this.date = date;
    }

    public Habit(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
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
                "habitName='" + habitName + '\'' +
                ", date=" + date +
                '}';
    }
}
