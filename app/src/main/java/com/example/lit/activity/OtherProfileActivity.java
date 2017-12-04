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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lit.R;
import com.example.lit.userprofile.UserProfile;

/**
 * Created by Riley Dixon on 04/12/2017.
 */

public class OtherProfileActivity extends AppCompatActivity {
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
    private UserProfile viewingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = (ImageView)findViewById(R.id.profileImageView);
        usernameView = (TextView)findViewById(R.id.usernameView);
        profileDescriptionView = (TextView)findViewById(R.id.profileDescriptionView);
        numFollowersView = (TextView)findViewById(R.id.numFollowersView);
        numFollowingView = (TextView)findViewById(R.id.numFollowingView);
        habitListView = (ListView)findViewById(R.id.listHabitView);
        followersView = (TextView)findViewById(R.id.followersView);
        followingView = (TextView)findViewById(R.id.followingView);
        followButton = (Button)findViewById(R.id.followButton);

        getIntent();



    }


}
