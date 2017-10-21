/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.habit;

import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitDateComparator;

import java.util.ArrayList;
import java.util.Collections;

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
                break;
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
