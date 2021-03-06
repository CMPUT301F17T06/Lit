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
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.example.lit.exception.BitmapTooLargeException;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.location.HabitLocation;
import com.example.lit.saving.Saveable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * NormalHabit
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

public class NormalHabit extends Habit{
    public NormalHabit(String title)throws HabitFormatException {super(title);}

    public NormalHabit(String title, Date date)throws HabitFormatException {super(title, date);}

    public NormalHabit(String title, Date date, String reason) throws HabitFormatException{super(title, date, reason);}

    public NormalHabit(String title, Date date, String reason, List<Calendar> calenderList, Bitmap image) throws HabitFormatException, BitmapTooLargeException {
        super(title, date, reason, calenderList,image);
    }

    @Override
    public String habitType(){return "Normal";}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected NormalHabit(Parcel in) {
        super(in);
    }

    public static final Creator<NormalHabit> CREATOR = new Creator<NormalHabit>() {
        @Override
        public NormalHabit createFromParcel(Parcel source) {
            return new NormalHabit(source);
        }

        @Override
        public NormalHabit[] newArray(int size) {
            return new NormalHabit[size];
        }
    };
}
