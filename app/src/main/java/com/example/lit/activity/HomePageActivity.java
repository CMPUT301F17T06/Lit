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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lit.R;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
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

public class HomePageActivity extends AppCompatActivity {

    private static final String FILENAME = "habitFile.sav";
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

    private ListView habitsListView;
    private ArrayList<Habit> habitArrayList;
    ArrayAdapter<Habit> habitAdapter;


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

        //Set up a dummy habit for testing
        try {
            Habit habit = new NormalHabit("Testing habit");
            habitArrayList.add(habit);
        }catch (HabitFormatException e){

        }

        habitAdapter.notifyDataSetChanged();

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

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017-09-19
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            habitArrayList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habitArrayList = new ArrayList<Habit>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter((new OutputStreamWriter(fos)));
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
