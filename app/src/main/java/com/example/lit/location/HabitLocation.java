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

/**
 * Habitlocation implement the location object in the habitevent
 * Version 1.0
 *
 * Nov.13 2017
 *
 *
 * @author : damon
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
public  class HabitLocation implements Parcelable {


    private LatLng location;
    
    public HabitLocation(LatLng location){
        this.location = location;
    }

    /*TODO : get the current location working later
     */

    public HabitLocation(){
        LatLng currentLocation = new LatLng(0.000, 0.000);
        this.location = currentLocation;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.location, flags);
    }

    protected HabitLocation(Parcel in) {
        this.location = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Parcelable.Creator<HabitLocation> CREATOR = new Parcelable.Creator<HabitLocation>() {
        @Override
        public HabitLocation createFromParcel(Parcel source) {
            return new HabitLocation(source);
        }

        @Override
        public HabitLocation[] newArray(int size) {
            return new HabitLocation[size];
        }
    };
}
