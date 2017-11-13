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
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addHabitButton = (ImageButton) findViewById(R.id.AddHabit);
        Maps = (Button) findViewById(R.id.Maps) ;
        HabitHistory =  (Button) findViewById(R.id.HabitHistory) ;
        Friends  = (Button) findViewById(R.id.Friend);
        Profile = (Button) findViewById(R.id.Profile);
        habitArrayList = new ArrayList<>();
        habitsListView = (ListView)findViewById(R.id.habit_ListView);
        habitAdapter = new ArrayAdapter<Habit>(this,R.layout.list_item,habitArrayList);
        habitsListView.setAdapter(habitAdapter);

        HabitHistory.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(HomePageActivity.this, HistoryActivity.class);
                startActivity(intent);
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

        habitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(HomePageActivity.this,ViewHabitActivity.class);
                Bundle bundle = new Bundle();
                Habit selectedHabit = habitArrayList.get(i);
                try {
                    HabitLocation location = selectedHabit.getHabitLocation();
                    LatLng latLng = location.getLocation();
                    double latitude = latLng.latitude;
                    double longitude = latLng.longitude;
                    selectedHabit.setLocation(null);
                    bundle.putDouble("lat", latitude);
                    bundle.putDouble("lng", longitude);
                }catch (Exception e){
                //TODO: handle when location is null
                }
                bundle.putSerializable("habit", selectedHabit);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


        habitAdapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, habitArrayList);
        habitsListView.setAdapter(habitAdapter);

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
        // TODO: Not sure the functionality of this block and why entering HistoryActivity
        // Comment out for normal transition for now
        else if(requestCode == 2){ // Deal with habit event
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                HabitEvent habitEvent = (HabitEvent) bundle.getSerializable("event");
                double lat = bundle.getDouble("lat");
                double lng = bundle.getDouble("lng");
                Intent EventIntent = new Intent(HomePageActivity.this, HistoryActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("event", habitEvent);
                EventIntent.putExtras(bundle2);
                //startActivityForResult(EventIntent, 1);
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
