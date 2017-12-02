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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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


/*
 * AddHabitEventActivity
 *
 * Version 1.0
 *
 * Nov.13 2017
 *
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
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
    LocationManager manager;
    private HabitLocation habitLocation;
    private String provider;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        setTitle("Adding A New Habit Event");

        try{
            Bundle bundle = getIntent().getExtras();
            currentHabit = (Habit)bundle.getSerializable("habit");
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
        locationCheck = (CheckBox) findViewById(R.id.Locationcheck);
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
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
    /**
     * This function is called when user click on the completed button.
     * This function will build a NormalHabitEvent based on user inputs.
     * The NormalHabitEvent built will be sent back to HistoryActivity
     * @see ViewHabitActivity
     * @param saveNewHabitButton the current view.
     * */
    public void returnNewHabitEvent(View saveNewHabitButton) {
        habitNameString = habitEventName.getText().toString();
        commentString = habitEventComment.getText().toString();
        Intent newHabitEventIntent = new Intent(AddHabitEventActivity.this, HistoryActivity.class);
        Bundle bundle = new Bundle();
        try{
            Location location = buildLocation(locationCheck);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if (!(location == null)){
                bundle.putDouble("lat",latitude);
                bundle.putDouble("lng",longitude);
            }
        }
        catch (NullPointerException e){
            //TODO: handle when location is null
        }

        try {
            HabitEvent newHabitEvent = new NormalHabitEvent(habitNameString, commentString,null);
            bundle.putSerializable("event", newHabitEvent);
            newHabitEventIntent.putExtras(bundle);
            startActivityForResult(newHabitEventIntent,1);
            finish();
        } catch (HabitFormatException e) {
            Toast.makeText(AddHabitEventActivity.this, "Error: Illegal Habit Event information!", Toast.LENGTH_LONG).show();
        }
    }

    //TODO: should be able to set habit image
    private void setHabitImage(ImageView habitImage){}
    /**
     * This function will return a Location object containing Latitude and Longitude attribute.
     *
     * @param locationCheck location checkbox in AddHabitActivity.
     *
     * @return A location object, null if fail to initialize location.
     * */
    private Location buildLocation(CheckBox locationCheck){
                /*if checkbox checked return current location*/
        Location returnLocation = null;
        if  (locationCheck.isChecked()){
            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Define the criteria how to select the locatioin provider -> use
            // default
            Criteria criteria = new Criteria();
            provider = manager.getBestProvider(criteria, false);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location location = manager.getLastKnownLocation(provider);
            if (location != null) {
                /*get the latitude and longitude from the location*/
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                returnLocation = location;
            }}
        else{
            returnLocation = null;
        }
        return returnLocation;
    }

}


