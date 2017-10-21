package com.example.lit;

import android.test.ActivityInstrumentationTestCase2;

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

    public void testSetHabitTitle(){
        Habit habit = new NormalHabit("test habit");
        String newTitle = "new habit title";
        habit.setHabitTitle(newTitle);

        assertEquals(habit.getTitle(),newTitle);
    }

}
