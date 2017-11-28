/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.location;

import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.habit.Habit;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by damon on 10/21/2017.
 */

public class HabitLocationTest extends ActivityInstrumentationTestCase2 {
    public HabitLocationTest() {
        super(Habit.class);
    }
    public void testgetLocation(){
        LatLng sydney = new LatLng(-33.867, 151.206);
        HabitLocation habitLocation = new HabitLocation(sydney);
        assertTrue(habitLocation.getLocation().equals(sydney));
    }
    public void testsetLocation(){
        LatLng sydney = new LatLng(-33.867, 151.206);
        LatLng newlocation = new LatLng(-30.000,150.200);
        HabitLocation habitLocation = new HabitLocation(sydney);
        habitLocation.setLocation(newlocation);
        assertTrue(habitLocation.getLocation().equals(newlocation));
    }


}

