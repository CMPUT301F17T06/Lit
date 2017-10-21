package com.example.lit;

import java.util.ArrayList;

/**
 * Created by weikailu on 2017-10-21.
 */

public class HabitList {

    private ArrayList<Habit> habits = new ArrayList<>();

    public HabitList(){}

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    public Habit getHabit(int index){
        return habits.get(index);
    }

    public boolean hasHabit(Habit habit){
        return habits.contains(habit);
    }

    public void add(Habit habit) {
        habits.add(habit);
    }

    public void delete(Habit habit) {
        habits.remove(habit);
    }
}
