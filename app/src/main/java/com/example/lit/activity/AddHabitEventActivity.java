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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ammar on 12/11/17.
 */

public class AddHabitEventActivity extends AppCompatActivity  {
    private static final String CLASS_KEY = "com.example.lit.activity.AddHabitEventActivity";


    Habit currentHabit;
    String habitTitleString;
    private TextView habitEventName;
    private EditText habitEventComment;
    private CheckBox locationCheck;
    Button saveHabitEvent;
    Button cancelHabitEvent;
    private ImageView habitImage;
    private Button editImage;
    //TODO: Implement image feature

    String habitNameString;
    String commentString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        setTitle("Adding A New Habit Event");


        try{
            Bundle bundle = getIntent().getExtras();
            currentHabit = (Habit)bundle.getSerializable("habit");
            double lat = bundle.getDouble("lat");
            double lng = bundle.getDouble("lng");
            LatLng latLng = new LatLng(lat, lng);
            HabitLocation habitLocation= new HabitLocation(latLng);

            currentHabit.setLocation(habitLocation);
            if (!(currentHabit instanceof Habit)) throw new LoadHabitException();
        }catch (LoadHabitException e){
            //TODO: handle LoadHabitException
        }

        // Retrieve habit info

        habitTitleString = currentHabit.getTitle();

        // Activity components
        habitEventName = (TextView) findViewById(R.id.habit_title_TextView);
        habitEventComment = (EditText) findViewById(R.id.Comment_EditText);
        habitEventComment.setLines(3); //Maximum lines our comment should be able to show at once.
        saveHabitEvent = (Button) findViewById(R.id.save_button);
        cancelHabitEvent = (Button) findViewById(R.id.discard_button);
        locationCheck = (CheckBox) findViewById(R.id.locationCheckBox);
        habitEventName.setText(habitTitleString);



        saveHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddHabitEventActivity", "Save Button pressed.");
                returnNewHabitEvent(view);
                finish();
            }
        });

        cancelHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddHabitEventActivity", "Cancel button pressed. Habit Event creation cancelled.");
                finish();
            }
        });

    }
    public void returnNewHabitEvent(View saveNewHabitButton) {
        habitNameString = habitEventName.getText().toString();
        commentString = habitEventComment.getText().toString();

        Intent newHabitEventIntent = new Intent(AddHabitEventActivity.this, ViewHabitActivity.class);
        try {
            HabitEvent newHabitEvent = new NormalHabitEvent(habitNameString, commentString);

            Bundle bundle = new Bundle();
            bundle.putSerializable("event", newHabitEvent);
            newHabitEventIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK, newHabitEventIntent);
            finish();
        } catch (HabitFormatException e) {
            Toast.makeText(AddHabitEventActivity.this, "Error: Illegal Habit Event information!", Toast.LENGTH_LONG).show();
        }
    }
        //TODO: should be able to set habit image
         private void setHabitImage(ImageView habitImage){}


}


