package com.example.lit.userprofile;

import android.graphics.Bitmap;

import com.example.lit.habit.Habit;
import com.example.lit.habitevent.HabitEvent;

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 21/10/2017.
 * edited by Damon on 08/11/2017
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



    public UserProfile(String name){ //New Account
        this.name = name;

    }

    public UserProfile(String name, String profileDescription,
                       Bitmap profileImage, ArrayList<Habit> currentHabits,
                       ArrayList<HabitEvent> habitCompletionHistory,
                       ArrayList<String> followingUsers){
        this.name = name;
        this. profileDescription = profileDescription;
        this.profileImage = profileImage;
        this.currentHabits = currentHabits;
        this.habitCompletionHistory = habitCompletionHistory;
        this.followingUsers = followingUsers;

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setProfileDescription(String profileDescription){
        this.profileDescription = profileDescription;
    }

    public String getProfileDecription(){
        return profileDescription;
    }

    public void setProfileImage(Bitmap profileImage){
        this.profileImage = profileImage;
    }

    public Bitmap getProfileImage(){
        return profileImage;
    }

    public void setCurrentHabits(ArrayList<Habit> currentHabits){
        this.currentHabits = currentHabits;
    }

    public ArrayList<Habit> getCurrentHabits(){
        return currentHabits;
    }

    public Habit getHabit(int habitPosition){
        return currentHabits.get(habitPosition);
    }

    public void setHabitCompletionHistory(
            ArrayList<HabitEvent> habitCompletionHistory){
        this.habitCompletionHistory = habitCompletionHistory;
    }

    public ArrayList<HabitEvent> getHabitCompletionHistory(){
        return habitCompletionHistory;
    }

    public HabitEvent getHabitEvent(int eventPosititon){
        return habitCompletionHistory.get(eventPosititon);
    }

    public void setFollowingUsers(
            ArrayList<String> followingUsers){
        this.followingUsers = followingUsers;
    }

    public ArrayList<String> getFollowingUsers(){
        return followingUsers;
    }

    public String getFollowingUser(int userPosition){
        return followingUsers.get(userPosition);
    }

    public void addHabit(Habit newHabit){
        currentHabits.add(newHabit);
    }

    public void deleteHabit(Habit deletedHabit){
        currentHabits.remove(deletedHabit);
    }

    public void addHabitEvent(HabitEvent newHabitEvent){
        habitCompletionHistory.add(newHabitEvent);
    }

    public Boolean addFollowingUser(String name){
        if (followingUsers.contains(name)) {
            return false;
        }
        else{
            followingUsers.add(name);
            return true;}

    }
    //False if the user is already following
    //True if the user added successfully

    public Boolean removeFollowingUser(String name){
        if(followingUsers.contains(name)){
            followingUsers.remove(name);
            return true;
        }
        else{
            return false;
        }
    }


}
