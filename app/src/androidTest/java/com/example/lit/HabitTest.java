package com.example.lit;

import android.test.ActivityInstrumentationTestCase2;

import com.example.lit.habit.*;
import com.example.lit.location.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by weikailu on 2017-10-20.
 */

public class HabitTest extends  ActivityInstrumentationTestCase2{

    public HabitTest(){
        super(Habit.class);
    }

    public void testGetTitle() throws HabitFormatException {
        String title = "test title";
        Habit habit = new NormalHabit(title);
        assertEquals(habit.getTitle(),title);
    }

    public void testSetTitle() throws HabitFormatException{
        Habit habit = new NormalHabit("test habit");
        String newTitle = "new habit title";
        habit.setTitle(newTitle);

        assertEquals(habit.getTitle(),newTitle);
    }

    public void testGetDate()throws HabitFormatException{
        Date date = new Date();
        Habit habit = new NormalHabit("test habit", date);

        assertEquals(habit.getDate(),date);
    }

    public void testSetDate()throws HabitFormatException{
        Date date = new Date(System.currentTimeMillis());
        Habit habit = new NormalHabit("test habit", date);

        date = new Date(System.currentTimeMillis() - 3600*1000);
        habit.setDate(date);

        assertEquals(habit.getDate(),date);
    }

    public void testGetLocation()throws HabitFormatException{
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        Location location = new Location(latLng);

        NormalHabit habit = new NormalHabit(title,date,location,reason);

        assertEquals(habit.getLocation(),location);
    }

    public void testSetLocation() throws HabitFormatException{
        String title = "test title";
        String reason = "test reason";
        Date date = new Date(System.currentTimeMillis());
        LatLng latLng = new LatLng(0.000, 0.000);
        Location location = new Location(latLng);

        LatLng newLatlng = new LatLng(5.000,6.000);
        Location newLocation = new Location(newLatlng);

        NormalHabit habit = new NormalHabit(title,date,location,reason);
        habit.setLocation(newLocation);

        assertEquals(habit.getLocation(),newLocation);
    }

}
