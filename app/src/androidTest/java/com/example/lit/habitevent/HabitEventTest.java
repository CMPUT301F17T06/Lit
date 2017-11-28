/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.habitevent;

import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.exception.HabitFormatException;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;

import java.util.Date;

/**
 * Created by ammar on 21/10/17.
 * news
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {
    public HabitEventTest(){
        super(HabitEvent.class);
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

    public void testComment()throws HabitFormatException {
        String comment = "test comment";
        HabitEvent habitEvent = new NormalHabitEvent("test");
        habitEvent.setEventComment(comment);

        assertEquals(habitEvent.getEventComment(),comment);
    }

}

