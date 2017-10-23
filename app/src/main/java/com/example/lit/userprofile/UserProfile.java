/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.lit.userprofile;

import android.graphics.Bitmap;

import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitList;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.HabitHistory;

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 21/10/2017.
 */

public abstract class UserProfile {

    /**
     * The name of the user account. This is assumed to be unique for the app's purposes.
     */
    private String name;
    private String profileDescription;
    private Bitmap profileImage;
    private HabitList currentHabits;
    private HabitHistory habitCompletionHistory;
    private ArrayList<String> followingUsers;
    //followingUsers should be NULL unless the profile being viewed
    //is that of the current user
    //private ArrayList<String> followedUsers; Depending on how we manage this

    /**
     * A dummy constructor, should be used for debugging only.
     * If creating a new UserProfile object for a purpose other than
     * debugging, use the other two constructors.
     */
    public UserProfile() { //Dummy Constructor, sets nullable.

    }

    /**
     * Intended to be used for when a new account is created.
     *
     * @param name The user account's name.
     */
    public UserProfile(String name){ //New Account

    }

    /**
     * When creating a UserProfile object from an existing UserProfile.
     *
     * @param name The user account's name.
     * @param profileDescription The optional profile description of the account.
     * @param profileImage The optional profile image attached to the account.
     * @param currentHabits The list of Habits currently associated with the account.
     * @param habitCompletionHistory The list of HabitEvents currently associated with the account.
     * @param followingUsers The list of the users that this account has been approved to follow.
     *
     * @see Bitmap for information reguarding the profile image.
     */
    public UserProfile(String name, String profileDescription,
                       Bitmap profileImage, ArrayList<Habit> currentHabits,
                       ArrayList<HabitEvent> habitCompletionHistory,
                       ArrayList<String> followingUsers){

    }

    /**
     * Sets the account name of the UserProfile. The name shouldn't be allowed to be changed.
     *
     * @param name The name the UserProfile should now be associated with.
     */
    protected void setName(String name){
        this.name = name;
    }

    /**
     * Returns the name the UserProfile is associated with.
     *
     * @return The name the UserProfile is associated with.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the description of the profile the account.
     *
     * @param description The description of the profile the account.
     */
    public void setProfileDescription(String description){
        this.profileDescription = description;
    }

    /**
     * Returns the description associated with the profile.
     *
     * @return The description associated with the profile.
     */
    public String getProfileDecription(){
        return this.profileDescription;
    }

    /**
     * Sets the image associated with the profile. The image used should be in a Bitmap file.
     *
     * @param image The bitmap image to be shown on the UserProfile.
     *
     * @see Bitmap
     */
    public void setProfileImage(Bitmap image){
        //this.profileImage.createBitmap(image);
        this.profileImage = image; //May not work, needs to be tested
    }

    /**
     * Returns the bitmap image that is associated with the UserProfile.
     *
     * @return The bitmap image to be shown on the UserProfile.
     *
     * @see Bitmap
     */
    public Bitmap getProfileImage(){
        //int[] dumbArray = new int[1];
        //return Bitmap.createBitmap(dumbArray, 1, 1, Bitmap.Config.ARGB_8888);

        return this.profileImage;
    }

    /**
     * Sets the list of Habits that the user has created.
     *
     * @param currentHabits A HabitList that carries information about each Habit the user created.
     */
    public void setCurrentHabits(HabitList currentHabits){
        this.currentHabits = currentHabits;
    }

    /**
     * Returns the HabitList of the Habits that the user has created.
     *
     * @return a HabitList of Habits.
     *
     * @see Habit
     * @see HabitList
     */
    public HabitList getCurrentHabits(){
        return this.currentHabits;
    }

    /**
     * Sets the list of habits that the user has created.
     *
     * @param habitCompletionHistory A HabitHistory that carries information about each
     *                               HabitEvent the user created.
     */
    public void setHabitCompletionHistory(HabitHistory habitCompletionHistory){
        this.habitCompletionHistory = habitCompletionHistory;
    }

    /**
     * Returns the HabitHistory of the HabitEvents that the user has completed.
     *
     * @return a HabitHistory of HabitEvents.
     *
     * @see HabitEvent
     * @see HabitHistory
     */
    public HabitHistory getHabitCompletionHistory(){
        return this.habitCompletionHistory;
    }

    /**
     * Sets a list of the other users that the user is currently following.
     *
     * @param followingUsers An ArrayList that contains the users names
     */
    public void setFollowingUsers(
            ArrayList<String> followingUsers){
        this.followingUsers = followingUsers;
    }

    /**
     * Returns a list of the other users that the user is currently following.
     *
     * @return An ArrayList that contains the users names
     */
    public ArrayList<String> getFollowingUsers(){
        return this.followingUsers;
    }

    /**
     * Returns the UserProfile's name if the only thing desired is the name
     *
     * @param userPosition The position the followed UserProfile is in the list.
     * @return The name of the user that is currently being followed.
     *
     * @see UserProfile#getFollowingUserProfile(int) if the UserProfile is desired instead.
     */
    public String getFollowingUser(int userPosition){
        return this.followingUsers.get(userPosition);
    }

    /**
     * Returns the UserProfile of an account that is currently followed by the user.
     *
     * @param userPosition The position the followed UserProfile is in the list.
     * @return A UserProfile object that is the desired requested account
     * @see UserProfile#getFollowingUser(int) if just the name is needed instead.
     */
    public UserProfile getFollowingUserProfile(int userPosition){
        //TODO: Use ElasticSearch to obtain the details of the followed users account

        return this; //TODO: Don't forget to change this.
    }

    /**
     * Approve a request for a user to follow the current user.
     *
     * @param name The user requesting following access.
     * @return True is the user is added successfully. False otherwise.
     */
    public boolean addFollowingUser(String name){
        return true;
    }

    /**
     * Remove a user that is currently following the current user.
     *
     * @param name The user whos following access is to be revoked.
     * @return True if the user has been revoked permissions successfully. False otherwise.
     */
    public boolean removeFollowingUser(String name){
        return true;
    }

    /**
     * Respond to whether a current request to follow the user.
     *
     * @param decision True if the user approves the follow request. False otherwise.
     */
    public void respondToFollowRequest(boolean decision){

    }

}
