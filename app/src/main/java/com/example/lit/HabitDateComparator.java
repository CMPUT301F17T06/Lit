package com.example.lit;

import java.util.Comparator;

/**
 * Created by weikailu on 2017-10-21.
 */

public class HabitDateComparator implements Comparator<Habit> {
    public int compare(Habit left, Habit right) {
        return left.getDate().compareTo(right.getDate());
    }
}
