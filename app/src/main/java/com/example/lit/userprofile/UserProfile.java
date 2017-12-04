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
import android.util.Log;

import com.example.lit.saving.DataHandler;
import com.example.lit.saving.Saveable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Riley Dixon on 21/10/2017.
 */

public class UserProfile implements Serializable, Saveable{

    public final static String CLASS_TYPE = "UserProfile";

    /**
     * The name of the user account. This is assumed to be unique for the app's purposes.
     */
    private String name;
    private String profileDescription;
    private Bitmap profileImage;
    private FollowManager followManager;
    private DataHandler<UserProfile> dataHandler;
    private String jestID;

    /**
     * A dummy constructor, should be used for debugging only.
     * If creating a new UserProfile object for a purpose other than
     * debugging, use the other two constructors.
     */
    public UserProfile() { //Dummy Constructor, sets nullable.
        this.name = null;
        this.profileDescription = null;
        this.profileImage = null;
        this.followManager = null;
    }

    /**
     * Intended to be used for when a new account is created.
     *
     * @param name The user account's name.
     */
    public UserProfile(String name){ //New Account
        this.name = name;
        this.followManager = new FollowManager(this);
    }

    /**
     * When creating a UserProfile object from an existing UserProfile.
     * i.e. Loading a UserProfile
     *
     * @param name The user account's name.
     * @param profileDescription The optional profile description of the account.
     * @param profileImage The optional profile image attached to the account.
     * @param followManager This object manages who is following who and who has requested to follow.
     *
     * @see Bitmap for information reguarding the profile image.
     */

    public UserProfile(String name, String profileDescription,
                       Bitmap profileImage, FollowManager followManager) throws BitmapTooLargeException{
        setName(name);
        setProfileDescription(profileDescription);
        setProfileImage(profileImage);
        setFollowManager(followManager);
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
    public String getProfileDescription(){
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
        int maxSize = 181; //For simplicity we are scaling to a square
        if(image.getByteCount() >= 65536){
            Log.e("UserProfile",
                    "Bitmap size to large to set. Rejecting the bitmap image.");
            image = Bitmap.createScaledBitmap(image, maxSize, maxSize, true);
            image.reconfigure(maxSize, maxSize, Bitmap.Config.RGB_565);
        }

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

        return this.profileImage;
    }


    public void setFollowManager(FollowManager followManager){
        this.followManager = followManager;
    }

    public FollowManager getFollowManager(){
        return this.followManager;
    }

    public DataHandler<UserProfile> getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(DataHandler<UserProfile> dataHandler) {
        this.dataHandler = dataHandler;
    }

    /**
     * Returns the UserProfile's name if the only thing desired is the name
     *
     * @param userPosition The position the followed UserProfile is in the list.
     * @return The name of the user that is currently being followed.
     *
     * @see UserProfile#getFollowingUserProfile(int) if the UserProfile is desired instead.
     */
    public String getFollowingUser(int userPosition) throws IndexOutOfBoundsException{
        return followManager.getFollowingUsers().get(userPosition);
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
     * Returns the UserProfile of an account that is currently followed by the user.
     *
     * @param username The name of the UserProfile we want to load.
     * @return A UserProfile object that is the desired requested account
     * @see UserProfile#getFollowingUser(int) if just the name is needed instead.
     */
    public UserProfile getFollowingUserProfile(String username){
        //TODO: Use ElasticSearch to obtain the details of the followed users account

        return this; //TODO: Don't forget to change this.
    }

    /**
     * Approve a request for a user to follow the current user.
     *
     * @param name The user requesting following access.
     * @return True is the user is added successfully. False otherwise.
     */
    public boolean requestToFollowUser(UserProfile requestingUser){
        //this.followManager


        return true;
    }

    public boolean cancelRequestToFollowUser(UserProfile cancellingUser){

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

    /**
     * Finds if a user is currently following another user given by their name.
     *
     * @param name The unique name used by the user that is followed
     * @return -1 if not found. Otherwise their position in the ArrayList is returned.
     */
    public int findFollowingUser(String name){
        return -1;
    }



    public void setID(String ID){
        this.jestID = ID;
    }

    public String getID(){
        return this.jestID;
    }

}
