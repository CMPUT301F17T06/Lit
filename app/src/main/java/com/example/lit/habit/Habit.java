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
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import com.example.lit.exception.BitmapTooLargeException;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.saving.Saveable;
import com.example.lit.exception.HabitFormatException;
import io.searchbox.annotations.JestId;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class is an abstract habit class
 * @author Steven Weikai Lu
 */
public abstract class Habit implements Habitable , Parcelable, Saveable {

    private String title;
    private SimpleDateFormat format;
    private Date date;
    public abstract String habitType();
    private String user;
    private String reason;
    private int titleLength = 20;
    private int reasonLength = 30;
    private List<Calendar> calendars;
    private List<Date> dates;
    private String encodedImage;

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

    public Habit(String title, Date date, String reason, List<Calendar> calendars, Bitmap image)throws HabitFormatException, BitmapTooLargeException{
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Function takes in a Date object and formats it to dd-MM-yyyy
     * @param date
     */
    public void setDate(Date date) {
        // Format the current time.
        SimpleDateFormat format = new SimpleDateFormat ("dd-MM-yyyy");
        String dateString = format.format(date);

        // Parse the previous string back into a Date.
        ParsePosition pos = new ParsePosition(0);
        this.date = format.parse(dateString, pos);
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
        if(encodedImage != null) {
            byte[] decodedString = Base64.decode(this.encodedImage, Base64.DEFAULT);
            this.image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return this.image;
    }

    /**
     * Function takes in a Bitmap object and decodes it to a 64Base string
     * @param image
     * @throws BitmapTooLargeException
     */
    public void setImage(Bitmap image) throws BitmapTooLargeException {
        if (image == null){
            this.image = null;
        }
        else if (image.getByteCount() > 65536){
            throw new BitmapTooLargeException();
        }
        else {
            this.image = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] byteArray = baos.toByteArray();
            this.encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            //Log.i("encoded",this.encodedImage);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return "Habit Name: " + this.getTitle() + '\n' +
                "Started From: " + format.format(this.getDate());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeSerializable(this.format);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.user);
        dest.writeString(this.reason);
        dest.writeInt(this.titleLength);
        dest.writeInt(this.reasonLength);
        dest.writeList(this.calendars);
        dest.writeList(this.dates);
        dest.writeString(this.encodedImage);
        dest.writeString(this.id);
        dest.writeParcelable(this.image, flags);
    }

    protected Habit(Parcel in) {
        this.title = in.readString();
        this.format = (SimpleDateFormat) in.readSerializable();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.user = in.readString();
        this.reason = in.readString();
        this.titleLength = in.readInt();
        this.reasonLength = in.readInt();
        this.calendars = new ArrayList<Calendar>();
        in.readList(this.calendars, Calendar.class.getClassLoader());
        this.dates = new ArrayList<Date>();
        in.readList(this.dates, Date.class.getClassLoader());
        this.encodedImage = in.readString();
        this.id = in.readString();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

}
