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

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 31/10/2017.
 */

class FollowManager {
    //Follow Manager should not exist without being constructed with an accompanying UserProfile
    private String currentUsersName;
    private ArrayList<String> followingUsers;
    private ArrayList<String> followedUsers;
    private ArrayList<String> requestToFollow;
    private ArrayList<String> requestedToFollow;

    FollowManager(UserProfile userProfile){
        currentUsersName = userProfile.getName();
        followedUsers = new ArrayList<String>();
        followingUsers = new ArrayList<String>();
        requestToFollow = new ArrayList<String>();
        requestedToFollow = new ArrayList<String>();
    }

    FollowManager(UserProfile userProfile, ArrayList<String> followingUsers,
                  ArrayList<String> followedUsers, ArrayList<String> requestToFollow,
                  ArrayList<String> requestedToFollow){

        currentUsersName = userProfile.getName();
        this.followingUsers = followingUsers;
        this.followedUsers = followedUsers;
        this.requestToFollow = requestToFollow;
        this.requestedToFollow = requestedToFollow;
    }

    public ArrayList<String> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(ArrayList<String> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public ArrayList<String> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(ArrayList<String> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public ArrayList<String> getRequestToFollow() {
        return requestToFollow;
    }

    public void setRequestToFollow(ArrayList<String> requestToFollow) {
        this.requestToFollow = requestToFollow;
    }

    public ArrayList<String> getRequestedToFollow() {
        return requestedToFollow;
    }

    public void setRequestedToFollow(ArrayList<String> requestedToFollow) {
        this.requestedToFollow = requestedToFollow;
    }





    public void requestToFollowUser(String requestingToFollowThisUser){
        requestToFollow.add(requestingToFollowThisUser);
        //TODO: Get the requestingToFollowUser's requestedToFollowArrayList and add requestingUser
        //TODO: Save the modified requestToFollow and requestedToFollow
    }

    public void cancelRequestToFollow(String requestingToFollowThisUser){
        requestToFollow.remove(requestingToFollowThisUser);
        //TODO: Get the requestingToFollowUser's requestedToFollowArrayList and remove requestingUser
        //TODO: Save the modified requestToFollow and requestedToFollow
    }

    public void approveFollowRequest(String requestedUser){

    }


}
