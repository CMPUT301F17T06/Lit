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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lit.Utilities.MultiSelectionSpinner;
import com.example.lit.R;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddHabitActivity extends AppCompatActivity {

    private static final String CLASS_KEY = "com.example.lit.activity.AddHabitActivity";

    private EditText habitName;
    private EditText habitComment;
    //private EditText habitFrequency;
    private ImageView habitImage;
    private Button editImage;
    private Button saveHabit;
    private Button cancelHabit;
    private CheckBox locationCheck; //This should not be a button, its currently a placeholder
    private MultiSelectionSpinner weekday_spinner;
    //TODO: Implement location feature

    private Date habitStartDate;
    private String habitNameString;
    private String commentString;
    private List<String> weekdays;
    private EditText hour_view;
    private EditText minute_view;
    private  int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        setTitle("Adding A New Habit");

        // Activity components
        habitName = (EditText) findViewById(R.id.Habit_EditText);
        habitComment = (EditText) findViewById(R.id.Comment_EditText);
        habitComment.setLines(3); //Maximum lines our comment should be able to show at once.
        saveHabit = (Button) findViewById(R.id.SaveHabit);
        cancelHabit = (Button) findViewById(R.id.discard_button);
        hour_view = (EditText) findViewById(R.id.hour);
        minute_view = (EditText)findViewById(R.id.minute);
        weekday_spinner = (MultiSelectionSpinner) findViewById(R.id.weekday_spinner);
        locationCheck = (CheckBox) findViewById(R.id.locationCheckBox);

        // Set up weekday selection
        weekday_spinner.setItems(createWeekdayList());

        saveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddHabitActivity", "Save Button pressed.");
                returnNewHabit(view);
            }
        });

        cancelHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddHabitActivity", "Cancel button pressed. Habit creation cancelled.");
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });


    }

    public void returnNewHabit(View saveNewHabitButton){
        habitNameString = habitName.getText().toString();
        commentString = habitComment.getText().toString();
        habitStartDate = Calendar.getInstance().getTime();
        hour = Integer.parseInt(hour_view.getText().toString());
        minute = Integer.parseInt(minute_view.getText().toString());
        weekdays = weekday_spinner.getSelectedStrings();

        //TODO: missing location parameter. Currently null pointer.
        //TODO: What if the habit throws an exception.

        Intent newHabitIntent = new Intent();
        Habit newHabit = new NormalHabit(habitNameString, habitStartDate,
                null, commentString);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem selection){
        switch (selection.getItemId()){
            case android.R.id.home: //Up button pressed
                Log.i("AddHabitActivity", "Up button pressed. Habit creation cancelled.");
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return true;
    }

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
     * Returns an array list of numbers in a string format. This list is from low to high
     * inclusively. Each number is seperated by each other as defined by the interval.
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

}
