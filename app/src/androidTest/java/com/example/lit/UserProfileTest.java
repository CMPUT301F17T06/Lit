/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.activity.HomePageActivity;
import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitList;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.HabitHistory;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.userprofile.UserProfile;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Riley Dixon on 23/10/2017.
 */

public class UserProfileTest extends ActivityInstrumentationTestCase2{
    UserProfile userProfile;

    public UserProfileTest(){
        super(HomePageActivity.class);
    }

    @Before
    public void setup(){
        userProfile = new UserProfile();
    }

    @Test
    public void testSetProfileImage(){
        //UserProfile userProfile = new UserProfile();
        int[] bitmapArray = {1,2,3,4};

        Bitmap image = Bitmap.createBitmap(bitmapArray, 2, 2, Bitmap.Config.ARGB_8888);

        userProfile.setProfileImage(image);

        assertEquals(image, userProfile.getProfileImage());
    }

    @Test
    public void testAddFollowingUser(){
        String name = "Jane Doe";
        UserProfile userProfileFollowing = new UserProfile(name);

        assertTrue(userProfile.addFollowingUser(userProfileFollowing.getName()));

        //assertEquals(0, userProfile.findFollowingUser(name));
    }

    @Test
    public void testFindFollowingUser(){
        String name = "Jane Doe";
        UserProfile userProfileFollowing = new UserProfile(name);

        userProfile.addFollowingUser(userProfileFollowing.getName());

        assertEquals(0, userProfile.findFollowingUser(name));
    }

    @Test
    public void testFailFindFollowingUser(){
        String name = "Jane Doe";
        UserProfile userProfileFollowing = new UserProfile(name);

        userProfile.addFollowingUser(userProfileFollowing.getName());

        assertEquals(-1, userProfile.findFollowingUser("Not Jane Doe"));
    }

    @Test
    public void testRemovingFollowingUser(){
        String name = "Jane Doe";
        UserProfile userProfileFollowing = new UserProfile(name);

        userProfile.addFollowingUser(userProfileFollowing.getName());
        assertTrue(userProfile.removeFollowingUser(name));

    }

    @Test
    public void testAddName(){
        String name = "My name";
        userProfile = new UserProfile(name);

        assertEquals(name, userProfile.getName());
    }

    @Test
    public void testAddDescription(){
        String description = "My description";
        userProfile.setProfileDescription(description);

        assertEquals(description, userProfile.getProfileDecription());
    }

    @Test
    public void testCurrentHabits(){
        HabitList history = new HabitList();
        userProfile.setCurrentHabits(history);

        assertEquals(history, userProfile.getCurrentHabits());
    }

    @Test
    public void testAddCurrentHabits(){
        HabitList history = new HabitList();
        Habit myHabit = new NormalHabit("My Habit");

        history.add(myHabit);
        userProfile.setCurrentHabits(history);

        assertTrue(userProfile.getCurrentHabits().hasHabit(myHabit));
    }

    @Test
    public void testCurrentHabitEvents(){
        HabitList history = new HabitList();
        userProfile.setCurrentHabits(history);

        assertEquals(history, userProfile.getCurrentHabits());
    }

    @Test
    public void testAddCurrentHabitEvents(){
        HabitHistory history = new HabitHistory();
        HabitEvent myHabitEvent = new NormalHabitEvent("My Habit");

        history.addHabitEvent(myHabitEvent);
        userProfile.setHabitCompletionHistory(history);

        assertTrue(userProfile.getHabitCompletionHistory().hasHabitEvent(myHabitEvent));
    }


}
