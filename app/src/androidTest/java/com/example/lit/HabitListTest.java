package com.example.lit;

/**
 * Created by weikailu on 2017-10-21.
 */

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

public class HabitListTest extends ActivityInstrumentationTestCase2 {

    public HabitListTest(){
        super(com.example.lit.HabitList.class);
    }

    public void testAddTweet(){
        HabitList habits = new HabitList();
        Habit habit = new NormalHabit("adding tweet") {
        };
        habits.add(habit);
        assertTrue(habits.hasHabit(habit));
    }

    public void testDelete(){
        HabitList list = new HabitList();
        Habit habit = new NormalHabit("test");
        list.add(habit);
        list.delete(habit);
        assertFalse(list.hasHabit(habit));
    }

    public void testSetHabits(){
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

    public void testGetTweet(){
        HabitList habits = new HabitList(); //
        Habit habit = new NormalHabit("test");
        habits.add(habit);
        Habit returnedTweet = habits.getHabit(0);
        assertEquals(returnedTweet.getTitle(), habit.getTitle());
    }

    public void testHasTweet(){
        HabitList list = new HabitList();
        Habit tweet = new NormalHabit("test");
        list.add(tweet);
        assertTrue(list.hasHabit(tweet));
    }

    public void testGetHabits(){
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