package com.example.lit.userprofile;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 21/10/2017.
 */

public class UserProfile {
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

    }

    public String getName(){

    }

    public void setProfileDescription(String name){

    }

    public String getProfileDecription(){

    }

    public void setProfileImage(Bitmap image){

    }

    public Bitmap getProfileImage(){

    }

    public void setCurrentHabits(ArrayList<Habit> currentHabits){

    }

    public ArrayList<Habit> getCurrentHabits(){

    }

    public Habit getHabit(int habitPosition){

    }

    public void setHabitCompletionHistory(
            ArrayList<HabitEvent> habitCompletionHistory){

    }

    public ArrayList<HabitEvent> getHabitCompletionHistory(){

    }

    public HabitEvent getHabitEvent(int eventPosititon){

    }

    public void setFollowingUsers(
            ArrayList<String> followingUsers){

    }

    public ArrayList<String> getFollowingUsers(){

    }

    public String getFollowingUser(int userPosition){

    }

    public void addHabit(Habit newHabit){

    }

    public void deleteHabit(Habit deletedHabit){

    }

    public void addHabitEvent(HabitEvent newHabitEvent){

    }

    public Boolean addFollowingUser(String name){

    }
    //False if the user is already following
    //True if the user added successfully

    public Boolean removeFollowingUser(){

    }


}
