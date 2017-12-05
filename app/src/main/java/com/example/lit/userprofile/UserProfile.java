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
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.example.lit.saving.DataHandler;
import com.example.lit.saving.Saveable;

import java.io.ByteArrayOutputStream;
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
    private String encodedImage;

    private FollowManager followManager;
    //private DataHandler<UserProfile> dataHandler;
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
        setName(name);
        setProfileDescription("");
        //setProfileImage(Bitmap.createBitmap(0,0, Bitmap.Config.RGB_565));
        profileImage = null;
        setFollowManager(new FollowManager(this));
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
    public void setProfileImage(@NonNull Bitmap image){
        int maxSize = 90; //For simplicity we are scaling to a square
        if(image.getByteCount() >= 65536){ //ensures the space requirement
            Log.e("UserProfile",
                    "Bitmap size to large, resizing.");
            image = Bitmap.createScaledBitmap(image, maxSize, maxSize, true);
            image.reconfigure(maxSize, maxSize, Bitmap.Config.ARGB_8888);
        }

        //Code provided by Ammar from Habit.java
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArray = baos.toByteArray();
        this.encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        this.profileImage = image;
    }

    /**
     * Returns the bitmap image that is associated with the UserProfile.
     *
     * @return The bitmap image to be shown on the UserProfile.
     *
     * @see Bitmap
     */
    public Bitmap getProfileImage(){
        if(encodedImage != null) {
            byte[] decodedString = Base64.decode(this.encodedImage, Base64.DEFAULT);
            this.profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return this.profileImage;
    }


    public void setFollowManager(FollowManager followManager){
        this.followManager = followManager;
    }

    public FollowManager getFollowManager(){
        return this.followManager;
    }

    public void setID(String ID){
        this.jestID = ID;
    }

    public String getID(){
        return this.jestID;
    }

}
