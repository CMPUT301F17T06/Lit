package com.example.lit;

/**
 * Created by damon on 10/20/2017.
 */
import com.google.android.gms.maps.model.LatLng;
public class Location {
    private LatLng location;

    public Location (LatLng location){
        this.location = location;
    }

    /*TODO : get the current location working later
     */

    public  Location (){
        LatLng currentLocation = new LatLng(0.000, 0.000);
        this.location = currentLocation;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
