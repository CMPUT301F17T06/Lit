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
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.location.HabitLocation;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.and;


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

    NormalHabit currentHabit;
    String habitTitleString;
    String habitCommentString;
    String habitDateStartedString;
    List<Calendar> habitCalenderList;
    String weeklyString;
    TextView weeklyTextView;
    TextView habitTitle;
    TextView habitComment;
    TextView habitDateStarted;
    Button editHabit;
    Button deleteHabit;
    Button mainMenu;
    Button addHabitEventButton;
    //String username;
    ImageView habitImageView;
    Bitmap habitImage;

    DataHandler eventDataHandler;
    DataHandler dataHandler;
    Integer index;
    ArrayList<NormalHabit> habitArrayList;
    List<Integer> selectedWeekdays;
    int hour;
    int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);
        addHabitEventButton = (Button) findViewById(R.id.AddHabitEvent);

        try{
            Bundle bundle = getIntent().getExtras();
            currentHabit = (NormalHabit)bundle.getParcelable("habit");
            dataHandler = (DataHandler) bundle.getSerializable("dataHandler");
            eventDataHandler = (DataHandler)bundle.getSerializable("eventDataHandler");
            index = bundle.getInt("index");

            habitArrayList = (ArrayList<NormalHabit>) dataHandler.loadData();


            if (!(currentHabit instanceof Habit)) throw new LoadHabitException();
        }catch (LoadHabitException e){
            //TODO: handle LoadHabitException
        }catch (NoDataException e2){
            Toast.makeText(ViewHabitActivity.this,"Error: Can't load data! code:5",Toast.LENGTH_LONG).show();
        }
        // Retrieve habit info
        habitTitleString = currentHabit.getTitle();
        habitCommentString = currentHabit.getReason();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        habitDateStartedString = format.format(currentHabit.getDate());
        habitImage = currentHabit.getImage();
        habitCalenderList = currentHabit.getCalendars();
        selectedWeekdays = getSelectedWeekdays(habitCalenderList);
        hour = getHour(habitCalenderList);
        minute = getMinute(habitCalenderList);


        // Set up view components
        habitImageView = (ImageView) findViewById(R.id.ViewHabitImage);
        habitTitle = (TextView) findViewById(R.id.habit_title_TextView);
        habitComment = (TextView) findViewById(R.id.Comment_TextView);
        habitDateStarted = (TextView) findViewById(R.id.date_started_TextView);
        weeklyTextView = (TextView)findViewById(R.id.repeat_schedule);
        habitTitle.setText(habitTitleString);
        habitComment.setText(habitCommentString);
        habitDateStarted.setText(habitDateStartedString);
        habitImageView.setImageBitmap(habitImage);
        weeklyString = getWeekdayCalenderString(selectedWeekdays,hour,minute);
        weeklyTextView.setText(weeklyString);


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
                deleteHabit(habitArrayList, index);
            }
        });

        addHabitEventButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddHabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("habit", currentHabit);
                bundle.putSerializable("eventDataHandler",eventDataHandler);
                //bundle.putString("username",username);
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
        bundle.putSerializable("DataHandler",dataHandler);
        bundle.putInt("index",index);
        intent.putExtras(bundle);
        startActivityForResult(intent,2);
    }

    //TODO: delete current habit and return to previous activity
    public void deleteHabit(ArrayList<NormalHabit> habitArrayList, int index){
        habitArrayList.remove(index);
        dataHandler.saveData(habitArrayList);

        Log.i("ViewHabitActivity", "Delete button pressed.");
        finish();
    }


    /**
     *
     * @param calendarList
     * @return
     */
    public int getMinute(List<Calendar> calendarList){
        int minute=0;
        if (calendarList!=null) {
            if (calendarList.size() > 1) {
                minute = calendarList.get(0).getTime().getMinutes();
            }
        }
        return minute;
    }

    /**
     *
     * @param calendarList
     * @return
     */
    public int getHour(List<Calendar> calendarList){
        int hour=0;
        if (calendarList!=null) {
            if (calendarList.size() > 1) {
                hour = calendarList.get(0).getTime().getHours();
            }
        }
        return hour;
    }

    /**
     *
     * @param calendarList
     * @return
     */
    public List<Integer> getSelectedWeekdays(List<Calendar> calendarList){
        List<Integer> selectedWeekdays = new ArrayList<>();
        if (calendarList!=null) {
            if (calendarList.size() > 1){
                for (Calendar calendar : calendarList) {
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    selectedWeekdays.add(dayOfWeek-1);
                }
            }
            else {
                selectedWeekdays.add(0);
            }
        }else{
            selectedWeekdays.add(0);
        }
        return selectedWeekdays;
    }

    /**
     *
     * @param calendarList
     * @return
     */
    public String getWeekdayCalenderString(List<Integer> selectedWeekdays,int hour, int minute){

        final String[] days = {"None","Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String weekdayString = "";

        if (selectedWeekdays.size() > 0){
            for (int weekday:selectedWeekdays){
                weekdayString = weekdayString + days[weekday] + ",";
            }
        }

        weekdayString = weekdayString + "  " + Integer.toString(hour) + ":" + Integer.toString(minute);

        return  weekdayString;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            finish();
        }
    }

}
