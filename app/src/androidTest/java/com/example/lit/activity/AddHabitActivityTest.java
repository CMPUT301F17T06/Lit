/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.example.lit.R;
import com.robotium.solo.Solo;

/**
 * Created by weikailu on 2017-11-13.
 */

public class AddHabitActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public AddHabitActivityTest(){
        super(AddHabitActivity.class);
    }

    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testAddHabit(){
        solo.assertCurrentActivity("Wrong Activity",AddHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.Habit_EditText),"Test Habit!");
        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity",HomePageActivity.class);
        assertTrue(solo.waitForText("Test Habit!"));
    }

    public void testDiscardHabit(){
        solo.assertCurrentActivity("Wrong Activity",AddHabitActivity.class);
        solo.clickOnButton("Discard");

        solo.assertCurrentActivity("Wrong Activity",HomePageActivity.class);
    }

    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
