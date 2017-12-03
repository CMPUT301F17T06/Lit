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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lit.R;
import com.example.lit.saving.NoDataException;
import com.example.lit.userprofile.UserProfile;

/**
 * Created by Riley Dixon on 02/12/2017.
 */

public class ProfileEditActivity extends AppCompatActivity {

    public final static String ACTIVITY_KEY = "com.example.lit.activity.ProfileEditActivity";

    private Button discardButton;
    private Button saveButton;
    private EditText profileDescriptionView;
    private ImageView profileImageView;
    private Button editProfileImageButton;

    private UserProfile edittingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_edit_user_profile_layout);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selection){
        switch(selection.getItemId()){
            case android.R.id.home: //Up button pressed
                Log.i("ProfileEditActivity", "Back Button Pressed. Save Changes");

                Intent sendModifiedUserProfile = new Intent();
                sendModifiedUserProfile.putExtra(ACTIVITY_KEY, edittingUser);
                setResult(Activity.RESULT_OK, sendModifiedUserProfile);
                finish();
        }
        return true;
    }
}
