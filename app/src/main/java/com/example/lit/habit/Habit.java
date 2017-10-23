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

import com.example.lit.location.*;

import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by weikailu on 10/20/2017.
 */

public abstract class Habit implements HabitAddable{

    private String title;
    private Date date;
    public abstract String habitType();
    private Location location;
    private String comment;
    private int titleLength;
    private int commentLength;
    @JestId
    private String id;

    public String getId(){ return id ;}
    public void setId(String id){ this.id = id ;}

    public Habit(String title) {
        this.title = title;
        this.date = new Date();
    }

    public Habit(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public Location getLocation(){return this.location;}

    public void setHabitInfoFormat(int titleLength, int commentLength)throws HabitFormatException{
        if (this.title.length() < titleLength){
            this.titleLength = titleLength;
        }else{
            throw new HabitFormatException();
        }

        if (this.comment.length() < commentLength){
            this.commentLength = commentLength;
        }else {
            throw new HabitFormatException();
        }

    }

    @Override
    public String toString() {
        return "Habit{" +
                "habitName='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
