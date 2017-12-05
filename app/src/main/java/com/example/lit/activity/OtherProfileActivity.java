/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lit.R;
import com.example.lit.saving.DataHandler;
import com.example.lit.userprofile.FollowManager;
import com.example.lit.userprofile.UserProfile;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Riley Dixon on 04/12/2017.
 */

/**
 * This activity is to show the user the profile of another user. This view however lacks
 * some features of the ProfileActivity that shows the user their own profile like the
 * "Edit Profile" button (for obvious reasons).
 *
 * As of right now you cannot view another users followers or who the users follow however
 * that could be easily added in.
 *
 */
public class OtherProfileActivity extends AppCompatActivity {
    public final static String ACTIVITY_KEY = "com.example.lit.activity.OtherProfileActivity";
    public final static String OTHER_USER = ACTIVITY_KEY + "OTHERUSER";
    public final static String CURRENT_USER = ACTIVITY_KEY + "CURRENTUSER";
    public final static int OTHER_REQUEST_CODE = 39;

    private ImageView profileImageView;
    private TextView usernameView;
    private TextView profileDescriptionView;
    private TextView numFollowersView;
    private TextView numFollowingView;
    private ListView habitListView; //May be deprecated based on what our design actually is?
    private TextView followersView;
    private TextView followingView;
    private Button followButton;

    private UserProfile currentUser;
    private UserProfile otherUser;
    private DataHandler<UserProfile> currentDataHandler;
    private DataHandler<UserProfile> otherDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_other_user_profile_layout);

        //Setup view
        profileImageView = (ImageView)findViewById(R.id.profileImageView);
        usernameView = (TextView)findViewById(R.id.usernameView);
        profileDescriptionView = (TextView)findViewById(R.id.profileDescriptionView);
        numFollowersView = (TextView)findViewById(R.id.numFollowersView);
        numFollowingView = (TextView)findViewById(R.id.numFollowingView);
        habitListView = (ListView)findViewById(R.id.listHabitView);
        followersView = (TextView)findViewById(R.id.followersView);
        followingView = (TextView)findViewById(R.id.followingView);
        followButton = (Button)findViewById(R.id.followButton);

        //get working data
        Intent otherProfileIntent = getIntent();
        currentUser = (UserProfile)otherProfileIntent.getSerializableExtra(CURRENT_USER);
        otherUser = (UserProfile)otherProfileIntent.getSerializableExtra(OTHER_USER);

        currentDataHandler = new DataHandler<>(currentUser.getName(), "UserProfile", this, new TypeToken<UserProfile>(){}.getType());
        otherDataHandler = new DataHandler<>(otherUser.getName(), "UserProfile", this, new TypeToken<UserProfile>(){}.getType());

        setTitle(otherUser.getName() + "'s Profile");

        usernameView.setText(otherUser.getName());
        profileDescriptionView.setText(otherUser.getProfileDescription());
        numFollowingView.setText(String.valueOf(otherUser.getFollowManager().getFollowingUsers().size()));
        numFollowersView.setText(String.valueOf(otherUser.getFollowManager().getFollowedUsers().size()));

        if(currentUser.getProfileImage() != null){
            profileImageView.setImageBitmap(currentUser.getProfileImage());
        }

        followButton.setText(setButtonText());

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workOnFollowStatus();
            }
        });

        //We can have these show the other users followers and following
        //Only do if we have time. Quite frankly its not important.
        numFollowersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    initiateFollowActivity("follower");
            }
        });

        numFollowingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    initiateFollowActivity("following");
            }
        });

        followersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    initiateFollowActivity("follower");
            }
        });

        followingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    initiateFollowActivity("following");
            }
        });


    }

    //Returns text based on what state the button should read
    private String setButtonText(){
        String buttonText;
        if(currentUser.getFollowManager().getFollowingUsers().contains(otherUser.getName())){
            buttonText = "Following";
        }else if(currentUser.getFollowManager().getRequestToFollowOther().contains(otherUser.getName())){
            buttonText = "Requested";
        }else{
            buttonText = "Follow";
        }


        return buttonText;
    }

    //This is to be used if the follower and following textiews should support this functionality.
    private void initiateFollowActivity(String option){
        Intent listOfFollowingers = new Intent(OtherProfileActivity.this, ProfileFollowActivity.class);
        listOfFollowingers.putExtra(ProfileFollowActivity.ACTIVITY_KEY, (Parcelable)currentUser);

        if(option.equals("following")){
            listOfFollowingers.putExtra(ProfileFollowActivity.OPERATION_MODE, option);
            Log.d("ProfileActivity", "Viewing followers of user: " + currentUser.getName());
            startActivityForResult(listOfFollowingers, ProfileFollowActivity.FOLLOW_REQUEST_CODE);
        }else if(option.equals("follower")){
            listOfFollowingers.putExtra(ProfileFollowActivity.OPERATION_MODE, option);
            Log.d("ProfileActivity", "Viewing following of user: " + currentUser.getName());
            startActivityForResult(listOfFollowingers, ProfileFollowActivity.FOLLOW_REQUEST_CODE);
        }else{
            Log.wtf("ProfileActivity", "initiateFollowDivided by zero. What did Riley do?");
            throw new RuntimeException("Crash Me!");
            //Crash our program. There isn't really an easy way to force the entire
            //app to exit nicely. There are ways for INDIVIDUAL processes, not this entire app at
            //once however. We shouldn't be here unless Riley messed up.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent modifiedUserProfile){
        if(requestCode == ProfileFollowActivity.FOLLOW_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                currentDataHandler.saveData(currentUser);
                otherDataHandler.saveData(otherUser);
            }else if(resultCode == Activity.RESULT_CANCELED){
                currentDataHandler.saveData(currentUser);
                otherDataHandler.saveData(otherUser);
            }
        }else{
            Log.wtf("ProfileActivity", "Invalid requestCode received.");
            //Let user know their edited data was lost.
            AlertDialog.Builder asdBuilder = new AlertDialog.Builder(this);
            asdBuilder.setTitle("Edit failed.");
            asdBuilder.setMessage("The edit failed to save and the edited information is lost." +
                    " We are unsure as to why this happened.");
            asdBuilder.show();
        }
    }

    /**
     * Based on the current button state, run the intended method to manipulate the follow lists.
     */
    private void workOnFollowStatus(){
        String buttonState = followButton.getText().toString();
        if(buttonState.equals("Follow")){
            FollowManager.requestToFollowOther(currentUser, otherUser, currentDataHandler, otherDataHandler);
            Log.d("OtherProfileActivity", "Requesting to follow");

        }else if(buttonState.equals("Requested")){
            FollowManager.cancelRequestToFollowOther(currentUser, otherUser, currentDataHandler, otherDataHandler);
            Log.d("OtherProfileActivity", "cancel request to follow");

        }else if(buttonState.equals("Following")){
            FollowManager.unFollowUser(currentUser, otherUser, currentDataHandler, otherDataHandler);
            Log.d("OtherProfileActivity", "unfollow");

        }else{
            Log.wtf("OtherProfileActivity", "Unknown button state.");
            throw new RuntimeException("Crash Me!"); //Crash ourselves since we shouldn't be here
        }
        followButton.setText(setButtonText());
    }

}
