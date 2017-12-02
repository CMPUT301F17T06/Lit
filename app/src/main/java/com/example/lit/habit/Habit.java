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

import android.graphics.Bitmap;
import android.os.Parcelable;

import com.example.lit.exception.HabitFormatException;
import com.example.lit.location.*;
import com.example.lit.saving.Saveable;
import com.google.android.gms.maps.model.LatLng;
import io.searchbox.annotations.JestId;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class is an abstract habit class
 * @author Steven Weikai Lu
 */
public abstract class Habit implements Habitable , Serializable, Saveable {

    private String title;
    private Date date;
    public abstract String habitType();
    private String reason;
    private int titleLength = 20;
    private int reasonLength = 30;
    private List<Calendar> calendars;
    @JestId
    private String id;
    private Bitmap image;

    public String getID(){ return id ;}

    public void setID(String id){ this.id = id ;}


    public Habit(String title) throws HabitFormatException {
        this.setTitle(title);
        this.setDate(new Date());
    }

    public Habit(String title, Date date) throws HabitFormatException{
        this.setTitle(title);
        this.setDate(date);
    }

    public Habit(String title, Date date, String reason) throws HabitFormatException {
        this.setTitle(title);
        this.setDate(date);
        this.setReason(reason);
    }

    /**
     * This is the main constructor we are using in AddHabitActivity
     *
     * @see com.example.lit.activity.AddHabitActivity
     * @param title Habit name, should be at most 20 char long.
     * @param reason Habit Comment, should be at most 30 char long.
     * @param date Set by GPS when creating the habit
     * @param calendarList Set by user when creating the habit
     * @throws HabitFormatException thrown when title longer than 20 char or reason longer than 30 char
     * */
    public Habit(String title, Date date, String reason, List<Calendar> calendarList) throws HabitFormatException {
        this.setTitle(title);
        this.setDate(date);
        this.setReason(reason);
        this.setCalendars(calendarList);
    }

    public Habit(String title, Date date, String reason, List<Calendar> calendars, Bitmap image)throws HabitFormatException {
        this.setTitle(title);
        this.setDate(date);
        this.setReason(reason);
        this.setCalendars(calendars);
        this.setImage(image);
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

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Habit Name: " + this.getTitle() + '\n' +
                "Started From: " + this.getDate();
    }

}
