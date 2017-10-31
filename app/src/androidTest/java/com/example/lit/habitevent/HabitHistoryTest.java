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

import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.HabitHistory;
import com.example.lit.habitevent.NormalHabitEvent;

import java.util.ArrayList;

/**
 * Created by ammar on 22/10/17.
 */

public class HabitHistoryTest extends ActivityInstrumentationTestCase2{
    public HabitHistoryTest(){
        super(HabitHistory.class);
    }

    public void testGetHabitHistory(){
        HabitHistory list = new HabitHistory();
        HabitEvent habitEvent1 = new NormalHabitEvent("test1");
        list.addHabitEvent(habitEvent1);
        android.os.SystemClock.sleep(5000);
        HabitEvent habitEvent2 = new NormalHabitEvent("test1");
        list.addHabitEvent(habitEvent2);
        android.os.SystemClock.sleep(5000);
        HabitEvent habitEvent3 = new NormalHabitEvent("test1");
        list.addHabitEvent(habitEvent3);


    }

    public void testSetHabitHistory(){
        HabitHistory list1 = new HabitHistory();
        HabitEvent habitEvent1 = new NormalHabitEvent("test habit 1");
        list1.addHabitEvent(habitEvent1);
        ArrayList<HabitEvent> list2 = new ArrayList<>();
        HabitEvent habitEvent2 = new NormalHabitEvent("test habit 2");
        list2.add(habitEvent2);

        list1.setHabitHistory(list2);
        assertFalse(list1.hasHabitEvent(habitEvent1));
        assertTrue(list1.hasHabitEvent(habitEvent2));
    }

    public void testGetHabitEvent(){
        HabitHistory habitHistory = new HabitHistory(); //
        HabitEvent habitEvent = new NormalHabitEvent("test");
        habitHistory.addHabitEvent(habitEvent);
        HabitEvent returnedHabitEvent = habitHistory.getHabitEvent(0);
        assertEquals(returnedHabitEvent.getHabitEventName(), habitEvent.getHabitEventName());
    }

    public void testHasHabitEvent(){
        HabitHistory list = new HabitHistory();
        HabitEvent habitEvent1 = new NormalHabitEvent("test");
        HabitEvent habitEvent2 = new NormalHabitEvent("test2");
        list.addHabitEvent(habitEvent1);
        assertTrue(list.hasHabitEvent(habitEvent1));
        assertFalse(list.hasHabitEvent(habitEvent2));
    }

    public void testAddHabitEvent(){
        HabitHistory list = new HabitHistory();
        HabitEvent habitEvent = new NormalHabitEvent("test");
        list.addHabitEvent(habitEvent);
        assertTrue(list.hasHabitEvent(habitEvent));
    }

    public void testDeleteHabitEvent(){
        HabitHistory list = new HabitHistory();
        HabitEvent habitEvent = new NormalHabitEvent("test");
        list.addHabitEvent(habitEvent);
        list.deleteHabitEvent(habitEvent);
        assertFalse(list.hasHabitEvent(habitEvent));
    }
}
