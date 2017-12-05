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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;
import com.example.lit.userprofile.FollowManager;
import com.example.lit.userprofile.OtherUserProfile;
import com.example.lit.userprofile.UserProfile;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 02/12/2017.
 */

/**
 * This activity is used for viewing the ArrayLists in FollowManager from UserProfile.
 * This allows a user to select their list to show their profile.
 */
public class ProfileFollowActivity extends AppCompatActivity {
    public final static int FOLLOW_REQUEST_CODE = 65; //random number
    public final static String ACTIVITY_KEY = "com.example.lit.activity.ProfileFollowActivity";
    public final static String OPERATION_MODE = ACTIVITY_KEY + ".FOLLOW";

    private ListView followListView;

    private DataHandler<UserProfile> ourDataHandler;
    private UserProfile currentUser;
    private ArrayAdapter<String> followAdapter;
    private ArrayList<String> followArray;
    private String option;


    @Override
    protected void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.temp_user_following_follower_layout);

        //get working data
        Intent ourUserIntent = getIntent();
        currentUser = (UserProfile)ourUserIntent.getSerializableExtra(ACTIVITY_KEY);
        option = ourUserIntent.getStringExtra(OPERATION_MODE);

        //Setup the view
        followListView = (ListView)findViewById(R.id.followListView);
        followArray = getFollowArray();
        followAdapter = new ArrayAdapter<String>(this, R.layout.list_item, followArray);
        followListView.setAdapter(followAdapter);



        //A user from the list was selected. This validates if the user exists or not.
        //If user does not exist, removes them from the list when selected.
        followListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedUser = followArray.get(position);
                DataHandler<UserProfile> theirDataHandler = new DataHandler<>(selectedUser, "UserProfile", getApplicationContext(), new TypeToken<UserProfile>(){}.getType());
                try {
                    UserProfile selectedUserProfile = theirDataHandler.loadData();
                    Intent viewOtherUserProfile = new Intent(getApplicationContext(), OtherProfileActivity.class);
                    viewOtherUserProfile.putExtra(OtherProfileActivity.CURRENT_USER, (Parcelable)currentUser);
                    viewOtherUserProfile.putExtra(OtherProfileActivity.OTHER_USER, (Parcelable)selectedUserProfile);
                    startActivityForResult(viewOtherUserProfile, OtherProfileActivity.OTHER_REQUEST_CODE);
                } catch (NoDataException e) {
                    Toast.makeText(getApplicationContext(),"User Doesn't exist.",Toast.LENGTH_LONG).show();
                    followArray.remove(selectedUser);
                    followAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selection){
        switch(selection.getItemId()){
            case android.R.id.home: //Up button pressed
                Log.d("ProfileFollowActivity", "Up Button Pressed. Return to profile");
                setResult(Activity.RESULT_OK);
                finish();
        }
        return true;
    }

    /**
     * Returns which array we should be looking at based on the option provided on activity create.
     *
     * @return The following user list
     */
    private ArrayList<String> getFollowArray(){
        if(option.equals("following")){
            setTitle("Following"); //Not necessary for getFollowArray but a convenient place
            return currentUser.getFollowManager().getFollowingUsers();
        }else if(option.equals("follower")){
            setTitle("Followed by"); //Not necessary for getFollowArray but a convenient place
            return currentUser.getFollowManager().getFollowedUsers();
        }else{
            Log.wtf("ProfileFollowActivity", "What did Riley do??");
            throw new RuntimeException("Crash Me!");
            //Crash our program. There isn't really an easy way to force the entire
            //app to exit nicely. There are ways for INDIVIDUAL processes, not this entire app at
            //once however. We shouldn't be here unless Riley messed up.
        }
    }


}
