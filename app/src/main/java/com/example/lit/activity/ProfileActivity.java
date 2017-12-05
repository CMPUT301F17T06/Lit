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
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lit.R;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;
import com.example.lit.userprofile.BitmapTooLargeException;
import com.example.lit.userprofile.FollowManager;
import com.example.lit.userprofile.UserProfile;
import com.google.gson.reflect.TypeToken;

public class ProfileActivity extends AppCompatActivity{
    public final static String ACTIVITY_KEY = "com.exmample.lit.activity.ProfileActivity";

    private ImageView profileImageView;
    private TextView usernameView;
    private TextView userDescriptionView;
    private TextView numFollowersView;
    private TextView numFollowingView;
    private ListView habitListView; //May be deprecated based on what our design actually is?
    private Button editProfileView;
    private TextView followersView;
    private TextView followingView;
    private DataHandler<UserProfile> dataHandler;

    private UserProfile currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_user_profile_layout);

        //TODO: Get the intent to initialize currentUser!
        Intent getUserIntent = getIntent();
        String username = getUserIntent.getStringExtra("username");



        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        usernameView = (TextView) findViewById(R.id.usernameView);
        userDescriptionView = (TextView) findViewById(R.id.profileDescriptionView);
        numFollowersView = (TextView) findViewById(R.id.numFollowersView);
        numFollowingView = (TextView) findViewById(R.id.numFollowingView);
        habitListView = (ListView) findViewById(R.id.listHabitView);
        editProfileView = (Button) findViewById(R.id.editProfileButton);
        followersView = (TextView) findViewById(R.id.followersView);
        followingView = (TextView) findViewById(R.id.followingView);
        //dataHandler = new DataHandler<>(currentUser.getName(), "UserProfile", this, new TypeToken<UserProfile>(){}.getType());
        dataHandler = new DataHandler<>(username, "UserProfile", this, new TypeToken<UserProfile>(){}.getType());
        setTitle(username + "'s Profile");

        try {
            currentUser = dataHandler.loadData();
        } catch (NoDataException e) {
            Log.d("ProfileActivity", "No user, creating new user.");
            currentUser = new UserProfile(username);
            dataHandler.saveData(currentUser);
        }
/*
        FollowManager fm = currentUser.getFollowManager();
        fm.getFollowingUsers().add("max");
        fm.getFollowingUsers().add("ammar");
        fm.getFollowedUsers().add("steven");
        fm.getFollowedUsers().add("damon");
        fm.getFollowedUsers().add("riley");
*/
        //currentUser.getFollowManager().getFollowingUsers().add("lswlrjnh");


        usernameView.setText(currentUser.getName());
        userDescriptionView.setText(currentUser.getProfileDescription());
        numFollowingView.setText(String.valueOf(currentUser.getFollowManager().getFollowingUsers().size()));
        numFollowersView.setText(String.valueOf(currentUser.getFollowManager().getFollowedUsers().size()));

        if(currentUser.getProfileImage() != null){
            profileImageView.setImageBitmap(currentUser.getProfileImage());
        }

        editProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ProfileActivity", "Editing profile of user: " + currentUser.getName());
                Intent editThisProfile = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                editThisProfile.putExtra(ProfileEditActivity.ACTIVITY_KEY, currentUser);
                startActivityForResult(editThisProfile, ProfileEditActivity.EDIT_USERPROFILE_CODE);
            }
        });

        numFollowersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateFollowActivity("follower");
            }
        });

        numFollowingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateFollowActivity("following");
            }
        });

        followersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateFollowActivity("follower");
            }
        });

        followingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateFollowActivity("following");
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        //Update numFollowers?
        //Update numFollowing?
        //Update Habit list?
    }

    private void initiateFollowActivity(String option){
        Intent listOfFollowingers = new Intent(ProfileActivity.this, ProfileFollowActivity.class);
        listOfFollowingers.putExtra(ProfileFollowActivity.ACTIVITY_KEY, currentUser);

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
        if(requestCode == ProfileEditActivity.EDIT_USERPROFILE_CODE){
            if(resultCode == Activity.RESULT_OK) {
                UserProfile returnProfile = (UserProfile) modifiedUserProfile
                        .getSerializableExtra(ACTIVITY_KEY);
                currentUser.setProfileDescription(returnProfile.getProfileDescription());
                userDescriptionView.setText(currentUser.getProfileDescription());
                if (returnProfile.getProfileImage() != null){
                    currentUser.setProfileImage(returnProfile.getProfileImage());
                    profileImageView.setImageBitmap(currentUser.getProfileImage());
                }
                dataHandler.saveData(currentUser);

            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.d("ProfileActivity", "Cancelled edit complete.");
            }
        }else if(requestCode == ProfileFollowActivity.FOLLOW_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                dataHandler.saveData(currentUser);
            }else if(resultCode == Activity.RESULT_CANCELED){
                dataHandler.saveData(currentUser);
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

}
