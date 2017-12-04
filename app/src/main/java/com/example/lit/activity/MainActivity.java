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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.saving.ElasticSearchHabitController;
import com.example.lit.userprofile.UserProfile;

/**
 * MainActivity
 * This show at the begining of the app
 * user input username login to user's account
 * Version 1.0
 *
 * Nov.13 2017
 *
 * @author : damon
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
 */

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    Button loginButton;
    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.Login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), HomePageActivityNew.class);
                String username = usernameEditText.getText().toString().toLowerCase();

                ElasticSearchHabitController.GetUserTask getUserTask = new ElasticSearchHabitController.GetUserTask();
                getUserTask.execute(username);
                if (!username.isEmpty()){
                    try{
                        user = getUserTask.get();
                        if(user == null){
                            user = new UserProfile(username);
                            ElasticSearchHabitController.AddUserTask addUserTask = new ElasticSearchHabitController.AddUserTask();
                            addUserTask.execute(user);
                            Toast.makeText(getApplicationContext(),"New User created.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"User exists.",Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                    }
                }

                intent.putExtra("username",username);
                startActivity(intent);
            }});
            }
}
