package com.example.lit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by weikailu on 2017-10-21.
 */

public class HabitList {

    private ArrayList<Habit> habits = new ArrayList<>();

    public HabitList(){}

    public ArrayList<Habit> getHabits(String sortType) {

        switch (sortType){
            case "date":
                Collections.sort(habits, new HabitDateComparator());
                break;
            case "frequency":
                // sort by frequency
        }
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
