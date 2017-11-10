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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lit.R;

public class HomePageActivity extends AppCompatActivity {

    ListView currentHabitList;
    ListView habitHistoryList;
    ListView friendsList;
    View mapView; //What kind of view is this supposed to be?
    View profileView; //What kind of view is this supposed to be?
    EditText habitHistoryQuery;

    TextView userProfileName;
    TextView userProfileDescription;
    ImageView userProfileImage;
    Button searchHistoryByHabitName;
    Button searchHistoryByComment;
    Button sortHistoryMenu; //Not sure what kind of View this should be


    private ImageButton addHabitButton;
    private Button Maps;
    private Button HabitHistory;
    private Button Friends;
    private Button Profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addHabitButton = (ImageButton) findViewById(R.id.AddHabit);
        Maps = (Button) findViewById(R.id.Maps) ;
        HabitHistory =  (Button) findViewById(R.id.HabitHistory) ;
        Friends  = (Button) findViewById(R.id.Friend);
        Profile = (Button) findViewById(R.id.Profile);


        HabitHistory.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), ViewHabitActivity.class);
                startActivityForResult(intent,1);
            }});

        Friends.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), ViewFriendActivity.class);
                startActivityForResult(intent,1);
            }});

        Maps.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivityForResult(intent,1);
            }});

        Profile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                startActivityForResult(intent,1);
            }});

        addHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), AddHabitActivity.class);
                startActivityForResult(intent,1);
            }});


    }

}
