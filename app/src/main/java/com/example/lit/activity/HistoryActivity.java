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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.lit.R;
import com.example.lit.exception.LoadHabitException;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;
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

public class HistoryActivity extends AppCompatActivity {
    private static final String FILENAME = "habiteventfile.sav";
    private Button BackMain;
    private ListView eventListView;
    private ArrayList<HabitEvent> eventArrayList;
    ArrayAdapter<HabitEvent> eventAdapter;
    private HabitLocation eventLocation;
    private HabitEvent event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        eventArrayList = new ArrayList<>();
        eventListView = (ListView)findViewById(R.id.eventlist);
        eventAdapter = new ArrayAdapter<HabitEvent>(this,R.layout.list_item,eventArrayList);
        eventListView.setAdapter(eventAdapter);


        BackMain = (Button) findViewById(R.id.eventhome);

        BackMain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), HomePageActivity.class);
                startActivity(intent);
            }});

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            //if return success update the values of item
            if (resultCode == RESULT_OK) {
                /**Take from https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
                 * 2017/11/12
                 */
                Bundle bundle = data.getExtras();
                HabitEvent event = (HabitEvent) bundle.getSerializable("event");
                try {
                    double lat = bundle.getDouble("lat");
                    double lng = bundle.getDouble("lng");
                    LatLng latLng = new LatLng(lat, lng);
                    eventLocation = new HabitLocation(latLng);
                    event.setLocation(eventLocation);
                } catch (Exception e) {
                    //
                }
                eventArrayList.add(event);
                eventAdapter.notifyDataSetChanged();
                saveInFile();
            }
        }
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


        eventAdapter = new ArrayAdapter<HabitEvent>(this,
                R.layout.list_item, eventArrayList);
        eventListView.setAdapter(eventAdapter);

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
            Type listType = new TypeToken<ArrayList<NormalHabitEvent>>() {}.getType();
            eventArrayList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            eventArrayList = new ArrayList<HabitEvent>();
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
            gson.toJson(eventArrayList,out);
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
