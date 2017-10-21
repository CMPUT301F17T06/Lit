package com.example.lit;

import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;

import java.util.Date;

/**
 * Created by ammar on 21/10/17.
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {
    public HabitEventTest(){
        super(HabitEvent.class);
    }

    public void testAddHabitEvent(){
    }


    public void testGetName() {
        String name = "test name";
        HabitEvent habitEvent = new NormalHabitEvent(name);
        assertEquals(habitEvent.getHabitEventName(),name);
    }

    public void testSetName(){
        HabitEvent habitEvent = new NormalHabitEvent("test habit event");
        String newName = "new habit event name";
        habitEvent.setHabitEventName(newName);

        assertEquals(habitEvent.getHabitEventName(),newName);
    }


    public void testDate(){
        Date date = new Date();
        HabitEvent habitEvent = new NormalHabitEvent("test");
        habitEvent.setEventDate(date);

        assertEquals(habitEvent.getEventDate(),date);
    }

    public void testComment(){
        String comment = "test comment";
        HabitEvent habitEvent = new NormalHabitEvent("test");
        habitEvent.setEventComment(comment);

        assertEquals(habitEvent.getEventComment(),comment);
    }

}

