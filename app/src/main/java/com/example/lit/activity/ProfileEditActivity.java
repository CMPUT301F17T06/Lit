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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

/**
 * Created by Riley Dixon on 02/12/2017.
 */

public class ProfileEditActivity extends AppCompatActivity {

    public final static String ACTIVITY_KEY = "com.example.lit.activity.ProfileEditActivity";
    public final static int EDIT_USERPROFILE_CODE = 43; //a "random" number
    public final static int GET_FROM_GALLERY = 82; //Our requestCode

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
        editProfileImageButton = (Button) findViewById(R.id.editImageButton);

        Intent editUserProfileIntent = getIntent();
        editingUser = (UserProfile) editUserProfileIntent.getSerializableExtra(ACTIVITY_KEY);
        usernameView.setText(editingUser.getName());
        profileDescriptionView.setText(editingUser.getProfileDescription());
        if(editingUser.getProfileImage() != null) {
            profileImageView.setImageBitmap(editingUser.getProfileImage());
        }

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
                editingUser.setProfileDescription(profileDescriptionView.getText().toString());
                sendModifiedUserProfile.putExtra(ProfileActivity.ACTIVITY_KEY, editingUser);
                setResult(Activity.RESULT_OK, sendModifiedUserProfile);
                finish();
            }
        });

        editProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ProfileEditActivity", "User requested to change profile image.");
                //Next line sourced from https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
                //By user Dhruv Gairola. Accessed Dec. 3rd.
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

                //changeProfileImage();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selection){
        switch(selection.getItemId()){
            case android.R.id.home: //Up button pressed
                Log.d("ProfileEditActivity", "Up Button Pressed. Edit Cancelled");
                setResult(Activity.RESULT_CANCELED);
                finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent selectedImage){
        if(requestCode == GET_FROM_GALLERY){
            //User is coming back from selecting an image
            if(resultCode == Activity.RESULT_OK){
                //Next lines sourced from https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
                //By user Dhruv Gairola. Accessed Dec. 3rd.
                Uri selectedImagePath = selectedImage.getData();
                Bitmap changeImage;
                try {
                    changeImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImagePath);
                    editingUser.setProfileImage(changeImage);
                    profileImageView.setImageBitmap(editingUser.getProfileImage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ProfileEditActivity", "Image failed to load.");
                }
            }else if(resultCode == Activity.RESULT_CANCELED){

            }else{
                Log.wtf("ProfileEditActivity", "Unknown resultCode received.");
                throw new RuntimeException("Crash Me!");
            }
        }else{
            Log.wtf("ProfileEditActivity", "Came from an unknown activity.");
            throw new RuntimeException("Crash Me!");
        }
    }


}
