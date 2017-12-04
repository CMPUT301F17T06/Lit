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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.exception.LoadHabitException;
import com.example.lit.habit.Habit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.location.HabitLocation;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
/**
 * viewHabitActivity
 * Version 1.0
 *
 * Nov.13 2017
 *
 * @see HomePageActivity
 *
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
 */
public class ViewHabitActivity extends AppCompatActivity {

    private static final String CLASS_KEY = "com.example.lit.activity.ViewHabitActivity";

    Habit currentHabit;
    String habitTitleString;
    String habitCommentString;
    String habitDateStartedString;
    TextView habitTitle;
    TextView habitComment;
    TextView habitDateStarted;
    Button editHabit;
    Button deleteHabit;
    Button mainMenu;
    Button addHabitEventButton;
    String username;
    ImageView habitImageView;
    Bitmap habitImage;
    DataHandler eventDataHanler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);
        addHabitEventButton = (Button) findViewById(R.id.AddHabitEvent);

        try{
            Bundle bundle = getIntent().getExtras();
            currentHabit = (Habit)bundle.getParcelable("habit");
            eventDataHanler = (DataHandler)bundle.getSerializable("eventdatahandler");
            username = (String)bundle.getString("username");

            if (!(currentHabit instanceof Habit)) throw new LoadHabitException();
        }catch (LoadHabitException e){
            //TODO: handle LoadHabitException
        }
        // Retrieve habit info
        habitTitleString = currentHabit.getTitle();
        habitCommentString = currentHabit.getReason();
        habitDateStartedString = currentHabit.getDate().toString();
        habitImage = currentHabit.getImage();

        // Set up view components
        habitImageView = (ImageView) findViewById(R.id.ViewHabitImage);
        habitTitle = (TextView) findViewById(R.id.habit_title_TextView);
        habitComment = (TextView) findViewById(R.id.Comment_TextView);
        habitDateStarted = (TextView) findViewById(R.id.date_started_TextView);
        habitTitle.setText(habitTitleString);
        habitComment.setText(habitCommentString);
        habitDateStarted.setText(habitDateStartedString);
        habitImageView.setImageBitmap(habitImage);


        // Set up buttons
        editHabit = (Button) findViewById(R.id.edit_habit_button);
        deleteHabit = (Button) findViewById(R.id.delete_habit_button);
        mainMenu = (Button) findViewById(R.id.main_menu_button);

        editHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditHabitActivity(currentHabit);
            }
        });

        deleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHabit(currentHabit);
            }
        });

        addHabitEventButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddHabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("habit", currentHabit);
                bundle.putSerializable("eventdatehandler",eventDataHanler);
                bundle.putString("username",username);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }});
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * This function jump to EditHabitActivity
     * @param habit
     */
    public void toEditHabitActivity(Habit habit){
        Log.i("ViewHabitActivity", "Edit button pressed.");

        Intent intent = new Intent(ViewHabitActivity.this,EditHabitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("habit", habit);
        intent.putExtras(bundle);
        startActivityForResult(intent,2);
    }

    //TODO: delete current habit and return to previous activity
    public void deleteHabit(Habit habit){
        //
    }


}
