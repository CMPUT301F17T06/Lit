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

import android.support.annotation.NonNull;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.userprofile.UserProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maxsg on 2017-12-04.
 */

public class ElasticSearchHabitControllerTest extends ActivityInstrumentationTestCase2 {
    String dummyUser = "dummy";
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Date date = Calendar.getInstance().getTime();
    public ElasticSearchHabitControllerTest(){
        super(ElasticSearchHabitController.class);
    }

    // Working
    public void testAddUserTask(){
        try{
            UserProfile dummy = new UserProfile(dummyUser);
            ElasticSearchHabitController.GetUserTask getUserTask = new ElasticSearchHabitController.GetUserTask();
            getUserTask.execute(dummyUser);
            UserProfile esGet;
            esGet = getUserTask.get();
            if (esGet != null){
                Log.i("esTest","user exits on es");
            }else{
                ElasticSearchHabitController.AddUserTask addUserTask = new ElasticSearchHabitController.AddUserTask();
                addUserTask.execute(dummy);
            }
        }catch (Exception e){
        }
    }

    // Working
    public void testGetUserTask(){
        try{
            UserProfile dummy;
            ElasticSearchHabitController.GetUserTask getUserTask = new ElasticSearchHabitController.GetUserTask();
            getUserTask.execute(dummyUser);
            dummy = getUserTask.get();
            Log.i("esTest",dummy.getName().toString());
        }catch (Exception e){
        }
    }


    public void testAddHabitsTask(){
        try{
            NormalHabit dummyHabit = new NormalHabit("test");
            dummyHabit.setUser(dummyUser);
            ElasticSearchHabitController.AddHabitsTask addHabitsTask = new ElasticSearchHabitController.AddHabitsTask();
            addHabitsTask.execute(dummyHabit);
        }catch (Exception e){
        }

    }

    // Working
    public void testGetCurrentHabitsTask(){
        ElasticSearchHabitController.GetCurrentHabitsTask getCurrentHabitsTask = new ElasticSearchHabitController.GetCurrentHabitsTask();
        getCurrentHabitsTask.execute(dummyUser);
        ArrayList<NormalHabit> habitArrayList = new ArrayList<NormalHabit>();
        try{
            habitArrayList = getCurrentHabitsTask.get();
        } catch (Exception e){
        }
        Log.i("esTest",habitArrayList.toString());

    }


    public void testAddHabitEventTask(){
        try{
            NormalHabitEvent dummyHabitEvent = new NormalHabitEvent("test") {
                @Override
                public int compareTo(@NonNull Object o) {
                    return 0;
                }
            };
            dummyHabitEvent.setUser(dummyUser);
            ElasticSearchHabitController.AddHabitEventTask addHabitEventTask = new ElasticSearchHabitController.AddHabitEventTask();
            addHabitEventTask.execute(dummyHabitEvent);
        }catch (Exception e){
        }

    }

    // Working
    public void testGetCurrentEventTask(){
        try{
            ElasticSearchHabitController.GetCurrentEventsTask getCurrentEventsTask = new ElasticSearchHabitController.GetCurrentEventsTask();
            getCurrentEventsTask.execute(dummyUser);
            ArrayList<NormalHabitEvent> habitEventArrayList = new ArrayList<NormalHabitEvent>();
            habitEventArrayList = getCurrentEventsTask.get();
            Log.i("esTest",habitEventArrayList.toString());
        }catch (Exception e){
        }

    }

    // Working
    public void testGetTodayHabitsTask(){
        try{
            NormalHabit forGettingDate = new NormalHabit("");
            Date today = forGettingDate.getDate();
            ElasticSearchHabitController.GetTodayHabitsTask getTodayHabitsTask = new ElasticSearchHabitController.GetTodayHabitsTask();
            getTodayHabitsTask.execute(dummyUser,today.toString());
            ArrayList<NormalHabit> habitArrayList = new ArrayList<NormalHabit>();
            habitArrayList = getTodayHabitsTask.get();
            Log.i("esTest",habitArrayList.toString());
        }catch (Exception e){
        }

    }
}
