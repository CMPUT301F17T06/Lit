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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.example.lit.fragment.HabitHistoryFragment;
import com.example.lit.habit.Habit;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.location.HabitLocation;
import com.example.lit.location.PlaceAutocompleteAdapter;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.ElasticSearchHabitController;
import com.example.lit.saving.NoDataException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.lit.activity.AddHabitActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;


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
    final int maxSize = 665536 / (1024*10);

    Habit currentHabit;
    ArrayList<NormalHabitEvent> eventArrayList;
    String habitTitleString;
    private TextView habitEventName;
    private EditText habitEventComment;
    private CheckBox locationCheck;
    Button saveHabitEvent;
    Button cancelHabitEvent;
    private ImageView habitImage;
    private Button editImage;
    private AutoCompleteTextView SearchText;
    Bitmap image;
    String habitNameString;
    String commentString;
    String username;
    LocationManager manager;
    private HabitLocation eventLocation;
    private LocationListener locationListener;
    LatLng returnLocation;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    DataHandler<ArrayList<NormalHabitEvent>> eventDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        setTitle("Adding A New Habit Event");


        try{
            Bundle bundle = getIntent().getExtras();
            currentHabit = (Habit)bundle.getParcelable("habit");
            eventDataHandler = (DataHandler)bundle.getSerializable("eventDataHandler");
            //username = (String)bundle.getString("username");
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
        habitImage = (ImageView) findViewById(R.id.HabitEventImage);
        editImage = (Button)findViewById(R.id.eventImageButton);
        eventArrayList = new ArrayList<>();
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });


        saveHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddHabitEventActivity", "Save Button pressed.");
                setResult(RESULT_OK);
                returnNewHabitEvent(view);
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
        Intent newHabitEventIntent = new Intent();
        Bundle bundle = new Bundle();

        LatLng latLng = buildLocation(locationCheck);
        eventLocation = new HabitLocation(latLng);

        try {
            NormalHabitEvent newHabitEvent = new NormalHabitEvent(habitNameString, commentString,eventLocation);
            try {
                eventArrayList = eventDataHandler.loadData();
            }catch (NoDataException e){
                eventArrayList = new ArrayList<>();
            }
            ArrayList<NormalHabitEvent> newArrayList  = new ArrayList<>();
            newArrayList.add(newHabitEvent);
            for(int i=0; i < eventArrayList.size();i++){
                newArrayList.add((eventArrayList.get(i)));
            }

            try {
                eventDataHandler.saveData(newArrayList);
            }catch (NullPointerException e){
                Toast.makeText(AddHabitEventActivity.this, "Error: DataHanlder problem!", Toast.LENGTH_LONG).show();
            }
            //newHabitEvent.setUser(username);
           // ElasticSearchHabitController.AddHabitEventTask addHabitEventTask = new ElasticSearchHabitController.AddHabitEventTask();
            //addHabitEventTask.execute(newHabitEvent);

            //set Fragmentclass Arguments


            finish();
        } catch (HabitFormatException e) {
            Toast.makeText(AddHabitEventActivity.this, "Error: Illegal Habit Event information!", Toast.LENGTH_LONG).show();
        }
    }

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
            try {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    locationListener.onLocationChanged(location);

                } else {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    locationListener.onLocationChanged(location);
                }
            }catch(NullPointerException N){
                Toast.makeText(AddHabitEventActivity.this, "GPS null functioning", Toast.LENGTH_LONG).show();
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


    /**
     * Function used to take picture by camera.
     * taken: https://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
     */
    public void takePicture(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public Bitmap compressPicture(Bitmap bitmap){

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,maxSize,maxSize,true);

        return resizedBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    image = (Bitmap) data.getExtras().get("data");
                    image = compressPicture(image);
                    habitImage.setImageBitmap(image);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


