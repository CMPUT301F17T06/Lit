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

/**
 * Created by Riley Dixon on 01/12/2017.
 */

import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;

/**
 * Designed to be used when viewing a UseerProfile that does not belong to
 * the current operating user. Simply it hides methods that are not needed.
 *
 *
 * @see UserProfile
 */
public class OtherUserProfile {
    private UserProfile theirProfile;
    private DataHandler<UserProfile> dataHandler;

    OtherUserProfile(){
        this.theirProfile = null;
        this.dataHandler = null;
    }

    OtherUserProfile(UserProfile otherUser){
        this.theirProfile = otherUser;
    }

    OtherUserProfile(String name){
        try {
            this.theirProfile = dataHandler.loadData();
        } catch (NoDataException e) {
            e.printStackTrace();
        }
    }

    public boolean requestToFollowOtherUser(UserProfile requestingUser){

        return true;
    }

    public boolean cancelRequestToFollowOtherUser(){

        return true;
    }

}
