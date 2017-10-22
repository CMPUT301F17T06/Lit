package com.example.lit.userprofile;

import android.graphics.Bitmap;

import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 21/10/2017.
 */

public abstract class UserProfile {
    private String name; //Assumed to be unique.
    private String profileDescription;
    private Bitmap profileImage;
    private ArrayList<Habit> currentHabits;
    private ArrayList<HabitEvent> habitCompletionHistory;
    private ArrayList<String> followingUsers;
    //followingUsers should be NULL unless the profile being viewed
    //is that of the current user
    //private ArrayList<String> followedUsers; Depending on how we manage this

    public UserProfile() { //Dummy Constructor, sets nullable.

    }

    public UserProfile(String name){ //New Account

    }

    public UserProfile(String name, String profileDescription,
                       Bitmap profileImage, ArrayList<Habit> currentHabits,
                       ArrayList<HabitEvent> habitCompletionHistory,
                       ArrayList<String> followingUsers){

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setProfileDescription(String description){
        this.profileDescription = description;
    }

    public String getProfileDecription(){
        return this.profileDescription;
    }

    public void setProfileImage(Bitmap image){
        //this.profileImage.createBitmap(image);
        this.profileImage = image; //May not work, needs to be tested
    }

    public Bitmap getProfileImage(){
        int[] dumbArray = new int[1];
        return Bitmap.createBitmap(dumbArray, 1, 1, Bitmap.Config.ARGB_8888);
    }

    public void setCurrentHabits(ArrayList<Habit> currentHabits){

    }

    public ArrayList<Habit> getCurrentHabits(){
        return new ArrayList<Habit>();
    }

    public Habit getHabit(int habitPosition){
        return new NormalHabit("Dummy");
    }

    public void setHabitCompletionHistory(ArrayList<HabitEvent> habitCompletionHistory){

    }

    public ArrayList<HabitEvent> getHabitCompletionHistory(){
        return new ArrayList<HabitEvent>();
    }

    public HabitEvent getHabitEvent(int eventPosititon){
        return new NormalHabitEvent("Dummy");
    }

    public void setFollowingUsers(
            ArrayList<String> followingUsers){

    }

    public ArrayList<String> getFollowingUsers(){
        return new ArrayList<String>();
    }

    public String getFollowingUser(int userPosition){
        return new String();
    }

    public void addHabit(Habit newHabit){

    }

    public void deleteHabit(Habit deletedHabit){

    }

    public void addHabitEvent(HabitEvent newHabitEvent){

    }

    public boolean addFollowingUser(String name){
        return true;
    }
    //False if the user is already following
    //True if the user added successfully

    public boolean removeFollowingUser(){
        return true;
    }


}
