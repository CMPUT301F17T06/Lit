package com.example.lit;

import java.util.ArrayList;

/**
 * Created by ammar on 21/10/17.
 */

public class HabitHistory {

    private ArrayList<HabitEvent> habitHistory = new ArrayList<>();

    public HabitHistory(){}

    public ArrayList<HabitEvent> getHabitHistory() {
        return habitHistory;
    }

    public void setHabitHistory(ArrayList<HabitEvent> habitHistory) {
        this.habitHistory = habitHistory;
    }

    public HabitEvent getHabitEvent(int index){
        return habitHistory.get(index);
    }

    public boolean hasHabitEvent(HabitEvent habitEvent){
        return habitHistory.contains(habitEvent);
    }

    public void add(HabitEvent habitEvent) {
        habitHistory.add(habitEvent);
    }

    public void delete(HabitEvent habitEvent) {
        habitHistory.remove(habitEvent);
    }
}