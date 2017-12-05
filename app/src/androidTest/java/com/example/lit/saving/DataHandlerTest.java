/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.saving;

import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.activity.MainActivity;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.userprofile.FollowManager;
import com.example.lit.userprofile.UserProfile;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;

import java.util.Date;

/**
 * Created by Riley Dixon on 04/12/2017.
 */

public class DataHandlerTest extends ActivityInstrumentationTestCase2{
    private UserProfile userProfile = new UserProfile("unitTest");

    public DataHandlerTest(){
        super(MainActivity.class);
    }

    public void testSaveHabit(){
        try {
            NormalHabit habit = new NormalHabit("Test title", new Date(1), "Test reason");
            DataHandler<NormalHabit> dataHandler = new DataHandler<>(userProfile.getName(), "Habit",
                                        getActivity(), new TypeToken<NormalHabit>(){}.getType());
            dataHandler.saveData(habit);
            NormalHabit loadedHabit = dataHandler.loadData();
            assertEquals(habit, loadedHabit);
        } catch (HabitFormatException e) {
            e.printStackTrace();
        } catch (NoDataException e) {
            e.printStackTrace();
        }
    }

    public void testSaveUserProfile(){
        try {
            DataHandler<UserProfile> dh = new DataHandler<>(userProfile.getName(), "UserProfile",
                    getActivity(), new TypeToken<UserProfile>(){}.getType());

            dh.saveData(userProfile);
            UserProfile loadedProfile = dh.loadData();
            assertEquals(userProfile, loadedProfile);

        } catch (NoDataException e) {
            e.printStackTrace();
        }
    }

    public void testSaveHabitEvent(){
        try {
            NormalHabitEvent habitEvent = new NormalHabitEvent("Event name", "Habit comment");
            DataHandler<NormalHabitEvent> dataHandler = new DataHandler<>(userProfile.getName(), "UserProfile",
                    getActivity(), new TypeToken<NormalHabitEvent>() {}.getType());
            dataHandler.saveData(habitEvent);
            NormalHabitEvent loadedHabitEvent = dataHandler.loadData();
            assertEquals(habitEvent, loadedHabitEvent);
        } catch (HabitFormatException e) {
            e.printStackTrace();
        } catch (NoDataException e) {
            e.printStackTrace();
        }
    }


}
