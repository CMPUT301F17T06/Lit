package com.example.lit;

import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by damon on 10/21/2017.
 */

public class LocationTest extends ActivityInstrumentationTestCase2 {
    public LocationTest() {
        super(com.example.lit.Habit.class);
    }
    public void testgetLocation(){
        LatLng sydney = new LatLng(-33.867, 151.206);
        Location location = new Location(sydney);
        assertTrue(location.getLocation().equals(sydney));
    }
    public void testsetLocation(){
        LatLng sydney = new LatLng(-33.867, 151.206);
        LatLng newlocation = new LatLng(-30.000,150.200);
        Location location = new Location(sydney);
        location.setLocation(newlocation);
        assertTrue(location.getLocation().equals(newlocation));
    }


}

