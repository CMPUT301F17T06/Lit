package com.example.lit;

import java.util.Calendar;
import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by weikailu on 2017-10-20.
 */

public class HabitTest extends  ActivityInstrumentationTestCase2{

    public HabitTest(){
        super(com.example.lit.Habit.class);
    }

    public void testGetTitle() {
        String title = "test title";
        Habit habit = new NormalHabit(title);
        assertEquals(habit.getTitle(),title);
    }

    public void testSetTitle(){
        Habit habit = new NormalHabit("test habit");
        String newTitle = "new habit title";
        habit.setTitle(newTitle);

        assertEquals(habit.getTitle(),newTitle);
    }

    public void testGetDate(){
        Date date = new Date();
        Habit habit = new NormalHabit("test habit", date);

        assertEquals(habit.getDate(),date);
    }

    public void testSetDate(){
        Date date = new Date(System.currentTimeMillis());
        Habit habit = new NormalHabit("test habit", date);

        date = new Date(System.currentTimeMillis() - 3600*1000);
        habit.setDate(date);

        assertEquals(habit.getDate(),date);
    }

}
