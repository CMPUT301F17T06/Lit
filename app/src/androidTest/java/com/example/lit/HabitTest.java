package com.example.lit;

import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.habit.*;
import com.example.lit.location.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by weikailu on 2017-10-20.
 */

public class HabitTest extends  ActivityInstrumentationTestCase2{

    public HabitTest(){
        super(Habit.class);
    }

    public void testJestId(){
        Habit habit = new NormalHabit("test title");
        String id = "test ID";
        habit.setId(id);

        assertEquals(habit.getId(),id);
    }

    public void testGetTitle(){
        String title = "test title";
        Habit habit = new NormalHabit(title);
        assertEquals(habit.getTitle(),title);
    }

    public void testSetTitle() throws HabitFormatException{
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

    public void testGetReason(){
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        Location location = new Location(latLng);
        Habit habit = new NormalHabit(title,date,location,reason);
        assertEquals(habit.getReason(),reason);
    }

    public void testSetReason() throws HabitFormatException{
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        Location location = new Location(latLng);
        Habit habit = new NormalHabit(title,date,location,reason);
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


    public void testGetDate(){
        Date date = new Date();
        Habit habit = new NormalHabit("test habit", date);

        assertEquals(habit.getDate(),date);
    }

    public void testSetDate(){
        Date date = new Date(System.currentTimeMillis());
        Habit habit = new NormalHabit("test habit", date);

        date = new Date(System.currentTimeMillis() - 3600*1000);
        habit.setDate(date);

        assertEquals(habit.getDate(),date);
    }

    public void testGetLocation(){
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        Location location = new Location(latLng);

        NormalHabit habit = new NormalHabit(title,date,location,reason);

        assertEquals(habit.getLocation(),location);
    }

    public void testSetLocation(){
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        Location location = new Location(latLng);

        LatLng newLatlng = new LatLng(5.000,6.000);
        Location newLocation = new Location(newLatlng);

        NormalHabit habit = new NormalHabit(title,date,location,reason);
        habit.setLocation(newLatlng);

        assertEquals(habit.getLocation(),newLocation);
    }

}
