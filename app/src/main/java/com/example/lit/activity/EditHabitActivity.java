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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.Utilities.MultiSelectionSpinner;
import com.example.lit.exception.*;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * This activity is used for editing a existing habit.
 * Transition from this activity should be from ViewHabitActivity
 * @see ViewHabitActivity
 * @author Steven Weikai Lu
 *
 * @throws LoadHabitException when fail to load habit passed from ViewHabitActivity
 * */
public class EditHabitActivity extends AppCompatActivity {

    private static final String CLASS_KEY = "com.example.lit.activity.EditHabitActivity";

    Serializable serializable;
    Habit currentHabit;
    EditText habitName;
    EditText habitComment;
    Button saveHabit;
    Button cancelHabit;
    CheckBox locationCheck; //This should not be a button, its currently a placeholder
    MultiSelectionSpinner weekday_spinner;
    Spinner hour_spinner;
    Spinner minute_spinner;

    ImageView habitImage;
    Button editImage;
    //TODO: Implement image feature
    //TODO: Implement location feature

    Date habitStartDate;
    String habitNameString;
    String commentString;
    ArrayList<Integer> weekdays;
    Integer hour;
    Integer minute;
    String habitTitleString;
    String habitCommentString;
    Date habitDate;
    List<Calendar> calendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        try{
            serializable = getIntent().getExtras().getSerializable("habit");
            if (!(serializable instanceof Habit)) throw new LoadHabitException();
        }catch (LoadHabitException e){
            //TODO: handle LoadHabitException
        }
        currentHabit = (Habit) serializable;
        habitTitleString = currentHabit.getTitle();
        habitCommentString = currentHabit.getReason();
        habitDate = currentHabit.getDate();
        calendarList = currentHabit.getCalendars();
        hour = calendarList.get(0).getTime().getHours();
        minute = calendarList.get(0).getTime().getHours();
        weekdays = new ArrayList<>();
        for (Calendar calendar:calendarList
             ) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            weekdays.add(dayOfWeek);
        }

        // Activity components
        habitName = (EditText) findViewById(R.id.Habit_EditText);
        habitComment = (EditText) findViewById(R.id.Comment_EditText);
        habitComment.setLines(3); //Maximum lines our comment should be able to show at once.
        saveHabit = (Button) findViewById(R.id.save_habit_button);
        cancelHabit = (Button) findViewById(R.id.discard_button);
        hour_spinner = (Spinner) findViewById(R.id.hour_spinner);
        minute_spinner = (Spinner) findViewById(R.id.minute_spinner);
        weekday_spinner = (MultiSelectionSpinner) findViewById(R.id.weekday_spinner);
        locationCheck = (CheckBox) findViewById(R.id.locationCheckBox);

        // Set up weekday selection
        weekday_spinner.setItems(createWeekdayList());
        // Set up hour selection
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,createHourList());
        hourAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hour_spinner.setAdapter(hourAdapter);
        // Set up minute selection
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,createMinuteList());
        minuteAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hour_spinner.setAdapter(minuteAdapter);

        // Set up components initial info
        habitName.setText(habitTitleString);
        habitComment.setText(habitCommentString);
        weekday_spinner.setSelection(convertIntegers(weekdays));
        hour_spinner.setSelection(hour);
        minute_spinner.setSelection(minute);

        saveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditHabitActivity", "Save Button pressed.");
                returnNewHabit(view);
            }
        });

        cancelHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditHabitActivity", "Cancel button pressed. Habit edit cancelled.");
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    /**
     * @param saveNewHabitButton the view currently in
     * */
    public void returnNewHabit(View saveNewHabitButton){
        habitNameString = habitName.getText().toString();
        commentString = habitComment.getText().toString();
        habitStartDate = Calendar.getInstance().getTime();
        hour = Integer.parseInt(hour_spinner.getSelectedItem().toString());
        minute = Integer.parseInt(minute_spinner.getSelectedItem().toString());
        //weekdays = weekday_spinner.getSelectedStrings();

        //TODO: should be able to edit habit within this activity
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem selection){
        switch (selection.getItemId()){
            case android.R.id.home: //Up button pressed
                Log.i("AddHabitActivity", "Up button pressed. Habit edit cancelled.");
                setResult(Activity.RESULT_CANCELED);
                finish();
        }
        return true;
    }

    /**
     *@return returns an array list of string from Monday to Sunday
     * */
    private ArrayList<String> createWeekdayList(){
        ArrayList<String> weekdayList = new ArrayList<String>();
        weekdayList.add("None");
        weekdayList.add("Monday");
        weekdayList.add("Tuesday");
        weekdayList.add("Wednesday");
        weekdayList.add("Thursday");
        weekdayList.add("Friday");
        weekdayList.add("Saturday");
        weekdayList.add("Sunday");

        return weekdayList;
    }

    /**
     * @return  return an array list of number from 1 to 24 in string format
     * */
    private List<String> createHourList(){
        List<String> hourList = createNumberList(1,24,1);
        return hourList;
    }

    /**
     * Returns an array list of number from 1 to 60 in string format
     *
     * @return  An array list of number fom 1 to 60 in string format
     * */
    private List<String> createMinuteList(){
        List<String> hourList = createNumberList(1,60,1);
        return hourList;
    }

    //TODO: should be able to set habit image
    private void setHabitImage(ImageView habitImage){}

    /**
     * Returns an array list of numbers in a string format. This list is from low to high
     * inclusively. Each number is separated by each other as defined by the interval.
     * Should low > high then the list returned is empty.
     *
     * @param low The beginning of the range of numbers to be added to the list.
     * @param high The end of the range of numbers to be added to the list.
     * @param interval The interval in between numbers.
     * @return A list of numbers in ascending order.
     */
    private ArrayList<String> createNumberList(int low, int high, int interval){
        ArrayList<String> numberList = new ArrayList<>();
        for(int i = low; i <= high; i += interval){
            numberList.add(String.valueOf(i));
        }
        return numberList;
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}
