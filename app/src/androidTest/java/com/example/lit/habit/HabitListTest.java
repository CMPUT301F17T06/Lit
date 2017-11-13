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

/**
 * Created by weikailu on 2017-10-21.
 */

import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.*;


import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitList;
import com.example.lit.habit.NormalHabit;


import java.util.ArrayList;

public class HabitListTest extends ActivityInstrumentationTestCase2 {

    public HabitListTest(){
        super(HabitList.class);
    }

    public void testAddHabit() throws HabitFormatException{
        HabitList habits = new HabitList();
        Habit habit = new NormalHabit("adding tweet");
        habits.add(habit);
        assertTrue(habits.hasHabit(habit));
    }

    public void testDeleteHabit()throws HabitFormatException{
        HabitList list = new HabitList();
        Habit habit = new NormalHabit("test");
        list.add(habit);
        list.delete(habit);
        assertFalse(list.hasHabit(habit));
    }

    public void testSetHabits()throws HabitFormatException{
        HabitList list1 = new HabitList();
        Habit habit1 = new NormalHabit("test habit 1");
        list1.add(habit1);
        ArrayList<Habit> list2 = new ArrayList<>();
        Habit habit2 = new NormalHabit("test habit 2");
        list2.add(habit2);

        list1.setHabits(list2);
        assertFalse(list1.hasHabit(habit1));
        assertTrue(list1.hasHabit(habit2));
    }

    public void testGetHabit()throws HabitFormatException{
        HabitList habits = new HabitList(); //
        Habit habit = new NormalHabit("test");
        habits.add(habit);
        Habit returnedHabit = habits.getHabit(0);
        assertEquals(returnedHabit.getTitle(), habit.getTitle());
    }


    public void testHasHabit()throws HabitFormatException{
        HabitList list = new HabitList();
        Habit habit = new NormalHabit("test");
        list.add(habit);
        assertTrue(list.hasHabit(habit));
    }

    public void testGetHabits()throws HabitFormatException{
        HabitList list = new HabitList();
        Habit habit1 = new NormalHabit("test1");
        list.add(habit1);
        android.os.SystemClock.sleep(5000);
        Habit habit2 = new NormalHabit("test1");
        list.add(habit2);
        android.os.SystemClock.sleep(5000);
        Habit habit3 = new NormalHabit("test1");
        list.add(habit3);

        ArrayList<Habit> returnList = list.getHabits("date");

        boolean sorted = true;
        for(int i=0;i<returnList.size();i++){
            for (int j=i;j<returnList.size()-i;j++){
                if (returnList.get(i).getDate().after(returnList.get(j).getDate())){
                    sorted = false;
                }
            }
        }
        assertTrue(sorted);
    }
}
