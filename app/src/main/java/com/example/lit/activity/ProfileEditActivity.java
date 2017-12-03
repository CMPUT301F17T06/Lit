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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lit.R;
import com.example.lit.userprofile.UserProfile;

/**
 * Created by Riley Dixon on 02/12/2017.
 */

public class ProfileEditActivity extends AppCompatActivity {

    public final static String ACTIVITY_KEY = "com.example.lit.activity.ProfileEditActivity";
    public final static int EDIT_USERPROFILE_CODE = 2983472; //a "random" number

    private Button discardButton;
    private Button saveButton;
    private TextView usernameView;
    private EditText profileDescriptionView;
    private ImageView profileImageView;
    private Button editProfileImageButton;

    private UserProfile editingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_edit_user_profile_layout);
        discardButton = (Button) findViewById(R.id.discardButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        usernameView = (TextView) findViewById(R.id.usernameView);
        profileDescriptionView = (EditText) findViewById(R.id.profileDescriptionView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        editProfileImageButton = (Button) findViewById(R.id.editProfileButton);

        Intent editUserProfileIntent = getIntent();
        editingUser = (UserProfile) editUserProfileIntent.getSerializableExtra(ProfileActivity.ACTIVITY_KEY);
        usernameView.setText(editingUser.getName());
        profileDescriptionView.setText(editingUser.getProfileDescription());
        profileImageView.setImageBitmap(editingUser.getProfileImage());

        setTitle("Editing " + editingUser.getName() +"'s Profile");

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ProfileEditActivity", "User discarded Changes. Edit Cancelled");
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ProfileEditActivity", "User saving changes.");

                Intent sendModifiedUserProfile = new Intent();
                sendModifiedUserProfile.putExtra(ACTIVITY_KEY, editingUser);
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        editProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ProfileEditActivity", "User requested to change profile iamge.");
                changeProfileImage();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selection){
        switch(selection.getItemId()){
            case android.R.id.home: //Up button pressed
                Log.d("ProfileEditActivity", "Up Button Pressed. Edit Cancelled");

                Intent sendModifiedUserProfile = new Intent();
                sendModifiedUserProfile.putExtra(ACTIVITY_KEY, editingUser);
                setResult(Activity.RESULT_CANCELED);
                finish();
        }
        return true;
    }

    private void saveButtonPressed(){
        Intent editProfileIntent = new Intent();
        editingUser.setProfileDescription(profileDescriptionView.getText().toString());
        editProfileIntent.putExtra(ACTIVITY_KEY, editingUser);
        setResult(Activity.RESULT_OK, editProfileIntent);
        finish();
    }

    private void changeProfileImage(){

    }

}
