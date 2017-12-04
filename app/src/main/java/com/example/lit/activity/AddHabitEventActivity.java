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
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.lit.location.PlaceAutocompleteAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class AddHabitEventActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
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
    private AutoCompleteTextView SearchText;
    //TODO: Implement image feature

    String habitNameString;
    String commentString;
    LocationManager manager;
    private HabitLocation habitLocation;
    private String provider;
    double latitude;
    double longitude;
    private HabitLocation eventLocation;
    private LocationListener locationListener;
    LatLng returnLocation;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


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
        SearchText = (AutoCompleteTextView) findViewById(R.id.searchlocation);


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

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        SearchText.setAdapter(mPlaceAutocompleteAdapter);
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

        LatLng latLng = buildLocation(locationCheck);
        eventLocation = new HabitLocation(latLng);

        try {
            HabitEvent newHabitEvent = new NormalHabitEvent(habitNameString, commentString,eventLocation);
            bundle.putParcelable("event", newHabitEvent);
            newHabitEventIntent.putExtras(bundle);
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
    private LatLng buildLocation(CheckBox locationCheck){
                /*if checkbox checked return current location*/

        if  (locationCheck.isChecked()){
            manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            //request the location update thru location manager
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
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude and longitude from the location
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    returnLocation = new LatLng(latitude,longitude);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
        else{
            String searchString = SearchText.getText().toString();
            if(searchString != null){



                Geocoder geocoder = new Geocoder(AddHabitEventActivity.this);
                List<Address> list = new ArrayList<>();
                try{
                    list = geocoder.getFromLocationName(searchString, 1);
                }catch (IOException e){
                    Toast.makeText(this, geocoder.toString(), Toast.LENGTH_SHORT).show();
                }

                if(list.size() > 0) {
                    Address address = list.get(0);
                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();
                    returnLocation = new LatLng(latitude, longitude);
                    Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                returnLocation =null;
            }
    }

        return returnLocation;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


