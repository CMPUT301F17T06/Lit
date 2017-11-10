/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.habit;

import com.example.lit.exception.HabitFormatException;
import com.example.lit.location.*;
import com.google.android.gms.maps.model.LatLng;
import io.searchbox.annotations.JestId;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by weikailu on 10/20/2017.
 */

public abstract class Habit implements Habitable , Serializable{

    private String title;
    private Date date;
    public abstract String habitType();
    private Location location;
    private String reason;
    private int titleLength = 20;
    private int reasonLength = 30;
    @JestId
    private String id;

    public String getId(){ return id ;}

    public void setId(String id){ this.id = id ;}


    public Habit(String title)throws HabitFormatException {
        this.setTitle(title);
        this.setDate(new Date());
    }

    public Habit(String title, Date date) throws HabitFormatException{
        this.setTitle(title);
        this.setDate(date);
    }

    public Habit(String title, Date date, Location location, String reason) throws HabitFormatException {
        this.setTitle(title);
        this.setDate(date);
        this.setLocation(location);
        this.setReason(reason);
    }

    // TODO: Constructor with JestID

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws HabitFormatException {
        if (title.length() > this.titleLength){
            throw new HabitFormatException();
        }
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) throws HabitFormatException {
        if (reason.length() < this.reasonLength) {
            this.reason = reason;
        }
        else {
            throw new HabitFormatException();
        }
    }

    public void setLocation(LatLng coordinate){
        Location location = new Location(coordinate);
        this.location = location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public Location getLocation(){
        return this.location;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "habitName='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
