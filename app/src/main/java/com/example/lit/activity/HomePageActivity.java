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
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitList;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.location.HabitLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * HomepageActivity
 * This is the main page of the app. implements most of the function
 * Version 1.0
 *
 * Nov.13 2017
 * @author : damon
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
 */
public class HomePageActivity extends AppCompatActivity {


    private static final String FILENAME = "habitFile.sav";

    /*ListView currentHabitList;
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
*/

    private ImageButton addHabitButton;
    private Button Maps;
    private Button HabitHistory;
    private Button Friends;
    private Button Profile;

    private ListView habitsListView;
    private ArrayList<Habit> habitArrayList;
    ArrayAdapter<Habit> habitAdapter;
    private HabitLocation habitLocation;
    private HabitLocation eventlocation;
    HabitEvent habitEvent;
    private double lat;
    private double lng;

    //initializing navigation drawer
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_updated);

        //OLD CODE COMMENTED OUT
        //OLD CODE COMMENTED OUT

//        addHabitButton = (ImageButton) findViewById(R.id.AddHabit);
//        Maps = (Button) findViewById(R.id.Maps) ;
//        HabitHistory =  (Button) findViewById(R.id.HabitHistory) ;
//        Friends  = (Button) findViewById(R.id.Friend);
//        Profile = (Button) findViewById(R.id.Profile);
//        habitArrayList = new ArrayList<>();
//        habitsListView = (ListView)findViewById(R.id.habit_ListView);
//        habitAdapter = new ArrayAdapter<Habit>(this,R.layout.list_item,habitArrayList);
//        habitsListView.setAdapter(habitAdapter);
//
//
//        HabitHistory.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Intent EventIntent = new Intent(HomePageActivity.this, HistoryActivity.class);
//
//                startActivity(EventIntent);
//            }});
//
//        Friends.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                setResult(RESULT_OK);
//                Intent intent = new Intent(v.getContext(), ViewFriendActivity.class);
//                startActivityForResult(intent,1);
//            }});
//
//        Maps.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                setResult(RESULT_OK);
//                Intent intent = new Intent(v.getContext(), MapsActivity.class);
//                startActivityForResult(intent,1);
//            }});
//
//        Profile.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                setResult(RESULT_OK);
//                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
//                startActivityForResult(intent,1);
//            }});
//
//        addHabitButton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                setResult(RESULT_OK);
//                Intent intent = new Intent(v.getContext(), AddHabitActivity.class);
//                startActivityForResult(intent,1);
//            }});
//
//        habitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intent = new Intent(HomePageActivity.this,ViewHabitActivity.class);
//                Bundle bundle = new Bundle();
//                Habit selectedHabit = habitArrayList.get(i);
//                try {
//                    HabitLocation location = selectedHabit.getHabitLocation();
//                    LatLng latLng = location.getLocation();
//                    double latitude = latLng.latitude;
//                    double longitude = latLng.longitude;
//                    selectedHabit.setLocation(null);
//                    bundle.putDouble("lat", latitude);
//                    bundle.putDouble("lng", longitude);
//                }catch (Exception e){
//                //TODO: handle when location is null
//                }
//                bundle.putSerializable("habit", selectedHabit);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,2);
//            }
//        });

        //NEW CODE BELOW
        //----------------------


        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems() {
        String[] osArray = { "HabitHistory", "Friends", "Map", "Profile" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomePageActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


       /* habitAdapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, habitArrayList);
        habitsListView.setAdapter(habitAdapter);*/

    }
    /**
     * This function handle the new Habit returned from AddHabitActivity.
     * Activated when user click 'SAVE' button habit info valid.
     * @see AddHabitActivity
     *
     * @serialData A new Habit object
     * @param requestCode Request Code
     * @param resultCode Result Code
     * @param data  data returned from previous activity
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            //if return success update the values of item
            if(resultCode == RESULT_OK) {
                /**Take from https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
                 * 2017/11/12
                 */
                Bundle bundle = data.getExtras();
                Habit habit = (Habit) bundle.getSerializable("habit");
                try {
                    double lat = bundle.getDouble("lat");
                    double lng = bundle.getDouble("lng");
                    LatLng latLng = new LatLng(lat, lng);
                    habitLocation = new HabitLocation(latLng);
                    habit.setLocation(habitLocation);
                }catch (Exception e){
                    //
                }
                habitArrayList.add(habit);
                habitAdapter.notifyDataSetChanged();
                saveInFile();
            }
            else {
                //not return success do nothing
                habitAdapter.notifyDataSetChanged();
                saveInFile();
            }

        }


    }

    /**
     * This function load data from local file to HomePageActivity habit list view.
     * */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017/09/19
            Type listType = new TypeToken<ArrayList<NormalHabit>>() {}.getType();
            habitArrayList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habitArrayList = new ArrayList<Habit>();
        }
    }

    /**
     * This function save data when new habit object is instantiated.
     * */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitArrayList,out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
