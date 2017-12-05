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

import android.util.Log;

import com.example.lit.saving.DataHandler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Riley Dixon on 31/10/2017.
 */

/**
 * A class used to help organize data for the UserProfile. Tracks who the user is following,
 * who the user has requested to follow, tracks who is following the user and tracks who
 * has requested to follow the user.
 *
 * This class also helps organize the process to go about requesting to follow, cancelling a
 * follow request, and approving or denying a follow request.
 *
 */
public class FollowManager implements Serializable{
    //Follow Manager should not exist without being constructed with an accompanying UserProfile
    private String currentUsersName;
    private ArrayList<String> followingUsers;
    private ArrayList<String> followedUsers;
    private ArrayList<String> requestToFollowOther;
    private ArrayList<String> requestToFollowUser;

    /**
     * Creates a FollowManager object for new users. Since the user is new all of their
     * follow lists will be empty.
     *
     * @param userProfile Used to get the current users name. Even though we only require a name
     *                    it should ensure that only a really user is constructing this class.
     */
    FollowManager(UserProfile userProfile){
        currentUsersName = userProfile.getName();
        followedUsers = new ArrayList<String>();
        followingUsers = new ArrayList<String>();
        requestToFollowOther = new ArrayList<String>();
        requestToFollowUser = new ArrayList<String>();
    }

    /**
     * Creates a FollowManager for an existing user.
     *
     * @param userProfile Used to get the current users name. Even though we only require a name
     *                    it should ensure that only a really user is constructing this class.
     * @param followingUsers Who the user is following.
     * @param followedUsers Who is followed by the user.
     * @param requestToFollowOther Who the user has requested to follow.
     * @param requestToFollowUser Who has requested to follow the user,
     */
    FollowManager(UserProfile userProfile, ArrayList<String> followingUsers,
                  ArrayList<String> followedUsers, ArrayList<String> requestToFollowOther,
                  ArrayList<String> requestToFollowUser){

        currentUsersName = userProfile.getName();
        this.followingUsers = followingUsers;
        this.followedUsers = followedUsers;
        this.requestToFollowOther = requestToFollowOther;
        this.requestToFollowUser = requestToFollowUser;
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

    public ArrayList<String> getRequestToFollowOther() {
        return requestToFollowOther;
    }

    public void setRequestToFollowOther(ArrayList<String> requestToFollowOther) {
        this.requestToFollowOther = requestToFollowOther;
    }

    public ArrayList<String> getRequestToFollowUser() {
        return requestToFollowUser;
    }

    public void setRequestToFollowUser(ArrayList<String> requestToFollowUser) {
        this.requestToFollowUser = requestToFollowUser;
    }

    /**
     * The user requests to follow another user. This adds the other user to the requestToFollowOther
     * list and adds the current user to the other users requestToFollowUser list. This then saves
     * both lists to their respective DataHandlers.
     *
     * Even though there are UserProfile objects in the parameters, the lists are still referred to
     * by their containers. As such the changes will cascade.
     *
     * @param currentUser The UserProfile of the current user, used to save their side of the lists.
     * @param otherUser The User profile of the other user, used to save their side of the lists.
     * @param currentDataHandler Used to save currentUser after modifications.
     * @param otherDataHandler Used to save the otherUser after modifications.
     *
     */
    public static void requestToFollowOther(UserProfile currentUser, UserProfile otherUser,
                                     DataHandler<UserProfile> currentDataHandler,
                                     DataHandler<UserProfile> otherDataHandler){
        currentUser.getFollowManager().getRequestToFollowOther().add(otherUser.getName());
        otherUser.getFollowManager().getRequestToFollowUser().add(currentUser.getName());

        currentDataHandler.saveData(currentUser);
        otherDataHandler.saveData(otherUser);
    }

    /**
     * The user cancels their request to follow another user. This removes the other user from the
     * requestToFollowOther list and removes the current user from the other users
     * requestToFollowUser list. This then saves both lists to their respective DataHandlers.
     *
     * Even though there are UserProfile objects in the parameters, the lists are still referred to
     * by their containers. As such the changes will cascade.
     *
     * @param currentUser The UserProfile of the current user, used to save their side of the lists.
     * @param otherUser The User profile of the other user, used to save their side of the lists.
     * @param currentDataHandler Used to save CurrentUser after modifications.
     * @param otherDataHandler Used to save the otherUser after modifications.
     */
    public static void cancelRequestToFollowOther(UserProfile currentUser, UserProfile otherUser,
                                                  DataHandler<UserProfile> currentDataHandler,
                                                  DataHandler<UserProfile> otherDataHandler){
        boolean currentRemoved = currentUser.getFollowManager().getRequestToFollowOther()
                                    .remove(otherUser.getName());
        boolean otherRemoved = otherUser.getFollowManager().getRequestToFollowUser()
                                    .remove(currentUser.getName());
        if(!currentRemoved || !otherRemoved){
            Log.e("FollowManager", "Failed to remove user.");
            throw new RuntimeException("Failed to cancel follow request.");
        }
        currentDataHandler.saveData(currentUser);
        otherDataHandler.saveData(otherUser);
    }

    /**
     * The user approves a request from another user to follow them. This removes the other user
     * from the requestToFollowUser and adds them to the followedUsers list on the current users'
     * side. In turn this removes the current user from the requestToFollowOther list and adds them
     * to followingUsers on the other users side.
     *
     * Even though there are UserProfile objects in the parameters, the lists are still referred to
     * by their containers. As such the changes will cascade.
     *
     * @param currentUser The UserProfile of the current user, used to save their side of the lists.
     * @param otherUser The User profile of the other user, used to save their side of the lists.
     * @param currentDataHandler Used to save CurrentUser after modifications.
     * @param otherDataHandler Used to save the otherUser after modifications.
     */
    public static void approveRequestToFollowUser(UserProfile currentUser, UserProfile otherUser,
                                                  DataHandler<UserProfile> currentDataHandler,
                                                  DataHandler<UserProfile> otherDataHandler){
        currentUser.getFollowManager().followedUsers.add(otherUser.getName());
        otherUser.getFollowManager().followingUsers.add(currentUser.getName());

        //Now remove them from the requesting lists
        boolean currentRemoved = currentUser.getFollowManager().getRequestToFollowOther()
                .remove(otherUser.getName());
        boolean otherRemoved = otherUser.getFollowManager().getRequestToFollowUser()
                .remove(currentUser.getName());
        if(!currentRemoved || !otherRemoved){
            Log.e("FollowManager", "Failed to remove user.");
            throw new RuntimeException("Failed to cancel follow request.");
        }
        currentDataHandler.saveData(currentUser);
        otherDataHandler.saveData(otherUser);

    }

    /**
     * The user denys the follow request and removes the other user from the requestToFollowUser
     * list as well as removes the current user from the requestToFollowOther list on the other
     * users side.
     *
     * Even though there are UserProfile objects in the parameters, the lists are still referred to
     * by their containers. As such the changes will cascade.
     *
     * @param currentUser The UserProfile of the current user, used to save their side of the lists.
     * @param otherUser The User profile of the other user, used to save their side of the lists.
     * @param currentDataHandler Used to save CurrentUser after modifications.
     * @param otherDataHandler Used to save the otherUser after modifications.
     */
    public static void denyRequestToFollowUser(UserProfile currentUser, UserProfile otherUser,
                                               DataHandler<UserProfile> currentDataHandler,
                                               DataHandler<UserProfile> otherDataHandler){
        cancelRequestToFollowOther(currentUser, otherUser, currentDataHandler, otherDataHandler);
    }

    /**
     * The current user unfollows the other user
     *
     * Even though there are UserProfile objects in the parameters, the lists are still referred to
     * by their containers. As such the changes will cascade.
     *
     * @param currentUser The UserProfile of the current user, used to save their side of the lists.
     * @param otherUser The User profile of the other user, used to save their side of the lists.
     * @param currentDataHandler Used to save CurrentUser after modifications.
     * @param otherDataHandler Used to save the otherUser after modifications.
     */
    public static void unFollowUser(UserProfile currentUser, UserProfile otherUser,
                                               DataHandler<UserProfile> currentDataHandler,
                                               DataHandler<UserProfile> otherDataHandler){
        currentUser.getFollowManager().getFollowingUsers().remove(otherUser.getName());
        otherUser.getFollowManager().getFollowedUsers().remove(currentUser.getName());

    }


}
