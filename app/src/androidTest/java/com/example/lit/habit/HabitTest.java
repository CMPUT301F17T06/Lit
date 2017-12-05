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

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.lit.exception.HabitFormatException;
import com.example.lit.location.*;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by weikailu on 2017-10-20.
 */

public class HabitTest extends  ActivityInstrumentationTestCase2{

    public HabitTest(){
        super(Habit.class);
    }

    public void testJestId()throws HabitFormatException{
        Habit habit = new NormalHabit("test title");
        String id = "test ID";
        habit.setID(id);
        assertEquals(habit.getID(),id);
    }

    public void testGetTitle()throws HabitFormatException{
        String title = "test title";
        Habit habit = new NormalHabit(title);
        assertEquals(habit.getTitle(),title);
    }

    public void testSetTitle() throws HabitFormatException {
        Habit habit = new NormalHabit("test habit");
        String newTitle = "new habit title";
        habit.setTitle(newTitle);
        assertEquals(habit.getTitle(),newTitle);

        Boolean thrown = false;
        char[] arr = new char[50];
        Arrays.fill(arr, 'a');
        String tooLongTitle = new String(arr);
        try{
            habit.setTitle(tooLongTitle);
        }catch (HabitFormatException e){
            thrown = true;
        }
        assertTrue(thrown);

    }

    public void testGetReason()throws HabitFormatException{
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        HabitLocation habitLocation = new HabitLocation(latLng);
        Habit habit = new NormalHabit(title,date,reason);
        assertEquals(habit.getReason(),reason);
    }

    public void testSetReason() throws HabitFormatException{
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        HabitLocation habitLocation = new HabitLocation(latLng);
        Habit habit = new NormalHabit(title,date,reason);
        String newReason = "new habit reason";
        habit.setReason(newReason);
        assertEquals(habit.getReason(),newReason);

        Boolean thrown = false;
        char[] arr = new char[50];
        Arrays.fill(arr, 'a');
        String tooLongReason = new String(arr);
        try{
            habit.setReason(tooLongReason);
        }catch (HabitFormatException e){
            thrown = true;
        }
        assertTrue(thrown);

    }


    public void testGetDate()throws HabitFormatException{
        Date date = new Date();
        Habit habit = new NormalHabit("test habit", date);

        // Format the current time.
        SimpleDateFormat format = new SimpleDateFormat ("dd-MM-yyyy");
        String dateString = format.format(date);

        // Parse the previous string back into a Date.
        ParsePosition pos = new ParsePosition(0);
        date = format.parse(dateString, pos);

        assertEquals(habit.getDate(),date);
    }

    public void testSetDate()throws HabitFormatException{
        Date date = new Date();
        Habit habit = new NormalHabit("test habit", date);

        date = new Date();
        habit.setDate(date);

        // Format the current time.
        SimpleDateFormat format = new SimpleDateFormat ("dd-MM-yyyy");
        String dateString = format.format(date);

        // Parse the previous string back into a Date.
        ParsePosition pos = new ParsePosition(0);
        date = format.parse(dateString, pos);

        assertEquals(habit.getDate(),date);
    }

    public void testSetCalenders()throws HabitFormatException{
        Habit habit = new NormalHabit("test habit");

    }

}
