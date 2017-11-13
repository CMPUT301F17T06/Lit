/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.habitevent;

import com.example.lit.exception.HabitFormatException;
import com.example.lit.location.HabitLocation;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by damon on 10/20/2017.
 * Edited by ammar on 10/21/2017.
 */

public abstract class HabitEvent implements HabitEventAddable, Comparable, Serializable {
    private String habitEventName;
    private HabitLocation habitLocation;
    private Date date = new Date();
    private String eventComment;
    private int commentLength = 20;


    public HabitEvent(String habitEventName) {
        this.habitEventName = habitEventName;
        this.date = new Date();
    }

    public HabitEvent(String habitEventName, String habitEventComment) throws HabitFormatException{
        this.habitEventName = habitEventName;
        this.date = new Date();
        this.eventComment= habitEventComment;
    }
    public HabitEvent(String habitEventName, String habitEventComment,HabitLocation location) throws HabitFormatException{
        this.habitEventName = habitEventName;
        this.habitLocation = location;
        this.date = new Date();
        this.eventComment= habitEventComment;
    }
    public int compareTo(HabitEvent habitEvent){
        return this.date.compareTo(habitEvent.date);
    }

    public String getHabitEventName() {
        return habitEventName;
    }

    public void setHabitEventName(String habitEventName) {
        this.habitEventName = habitEventName;
    }

    public Date getEventDate() {
        return date;
    }

    public void setEventDate(Date date) {
        this.date = date;
    }

    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) throws HabitFormatException {
        if (eventComment.length() < this.commentLength) {
            this.eventComment = eventComment;
        }
        else {
            throw new HabitFormatException();
        }
    }
    public void setLocation(HabitLocation habitLocation){
        this.habitLocation = habitLocation;
    }

    public HabitLocation getHabitLocation(){
        return this.habitLocation;
    }

    @Override
    public String toString() {
        return "Habit Event{" +
                "habitEventName='" + habitEventName + '\'' +
                ", date=" + date + '\'' +
                ", comment=" + eventComment +
                '}';
    }

}
