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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lit.R;
import com.example.lit.saving.DataHandler;
import com.example.lit.userprofile.UserProfile;

import java.util.ArrayList;

/**
 * Created by Riley Dixon on 02/12/2017.
 */

public class ProfileFollowActivity extends AppCompatActivity {
    private ListView followListView;

    private DataHandler<UserProfile> ourDataHandler;
    private DataHandler<UserProfile> theirDataHandler;
    private UserProfile currentUser;
    private ArrayAdapter<String> followAdapter;
    private ArrayList<String> followArray;
    private String option;


    @Override
    protected void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.temp_other_user_profile_layout);

        followListView = (ListView)findViewById(R.id.followListView);
        followArray = getFollowArray();

        followListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedUser =
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        followAdapter = new ArrayAdapter<String>(this, R.layout.list_item, followArray);
        followListView.setAdapter(followAdapter);
    }

    private ArrayList<String> getFollowArray(){
        if(option.equals("following")){
            return currentUser.getFollowManager().getFollowingUsers();
        }else if(option.equals("follower")){
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
