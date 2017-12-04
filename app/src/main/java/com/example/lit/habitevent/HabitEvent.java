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

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.lit.exception.BitmapTooLargeException;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.location.HabitLocation;
import com.example.lit.saving.Saveable;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * HabitEvent
 *
 * Version 1.0
 *
 * Nov.13 2017
 *
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
 */

public abstract class HabitEvent implements HabitEventAddable, Comparable, Saveable, Parcelable {
    private String habitEventName;
    private HabitLocation habitLocation;
    private Date date;
    private String eventComment;
    private String user;
    private int commentLength = 20;
    private String jestID;
    private Bitmap image;


    public HabitEvent(String habitEventName) {
        setHabitEventName(habitEventName);
        setEventDate(new Date());
    }

    public HabitEvent(String habitEventName, Date date) {
        setHabitEventName(habitEventName);
        setEventDate(date);
    }

    public HabitEvent(String habitEventName, String habitEventComment) throws HabitFormatException{
        setHabitEventName(habitEventName);
        setEventDate(new Date());
        setEventComment(habitEventComment);
    }

    public HabitEvent(String habitEventName, String habitEventComment, Date date) throws HabitFormatException{
        setHabitEventName(habitEventName);
        setEventDate(date);
        setEventComment(habitEventComment);
    }

    /**
     * This is the main constructor we are using in AddHabitActivity
     *
     * @see com.example.lit.activity.AddHabitActivity
     * @param habitEventName Habitevent name, should be at most 20 char long.
     * @param habitEventComment Habitevent Comment, should be at most 30 char long.
     * @param location if user chose to attach location will get the current location
     * @throws HabitFormatException thrown when title longer than 20 char or reason longer than 30 char
     * */
    public HabitEvent(String habitEventName, String habitEventComment, Date date, HabitLocation location) throws HabitFormatException{
        setHabitEventName(habitEventName);
        setLocation(location);
        setEventDate(date);
        setEventComment(habitEventComment);
    }

    public HabitEvent(String habitEventName, String habitEventComment,HabitLocation location) throws HabitFormatException{
        setHabitEventName(habitEventName);
        setLocation(location);
        setEventDate(new Date());
        setEventComment(habitEventComment);
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

    public void setEventDate(Date date){
        // Format the current time.
        SimpleDateFormat format = new SimpleDateFormat ("dd-MM-yyyy");
        String dateString = format.format(date);

        // Parse the previous string back into a Date.
        ParsePosition pos = new ParsePosition(0);
        this.date = format.parse(dateString, pos);
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setID(String ID){
        this.jestID = ID;
    }

    public String getID(){
        return this.jestID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.habitEventName);
        dest.writeParcelable(this.habitLocation, flags);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.eventComment);
        dest.writeInt(this.commentLength);
        dest.writeString(this.jestID);
    }

    protected HabitEvent(Parcel in) {
        this.habitEventName = in.readString();
        this.habitLocation = in.readParcelable(HabitLocation.class.getClassLoader());
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.eventComment = in.readString();
        this.commentLength = in.readInt();
        this.jestID = in.readString();
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) throws BitmapTooLargeException {
        if (image.getByteCount() > 65536){
            throw new BitmapTooLargeException();
        }
        else {
            this.image = image;
        }
    }
}
