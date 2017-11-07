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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lit.Utilities.MultiSelectionSpinner;
import com.example.lit.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddHabitActivity extends AppCompatActivity {

    private EditText habitName;
    private EditText habitComment;
    //private EditText habitFrequency;
    private ImageView habitImage;
    private Button editImage;
    private Button saveHabit;
    private Button cancelHabit;
    private Button locationCheck; //This should not be a button, its currently a placeholder


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

        // Activity components
        habitName = (EditText) findViewById(R.id.Habit_EditText);
        habitComment = (EditText) findViewById(R.id.Comment_EditText);
        saveHabit = (Button) findViewById(R.id.SaveHabit);
        cancelHabit = (Button) findViewById(R.id.discard_button);
        hour_view = (EditText) findViewById(R.id.hour);
        minute_view = (EditText)findViewById(R.id.minute);

        // Set up weekday selection
        final MultiSelectionSpinner weekday_spinner=(MultiSelectionSpinner) findViewById(R.id.weekday_spinner);
        List<String> list = new ArrayList<String>();
        list.add("None");
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");
        list.add("Thursday");
        list.add("Friday");
        list.add("Saturday");
        list.add("Sunday");
        weekday_spinner.setItems(list);

        saveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitNameString = habitName.getText().toString();
                commentString = habitComment.getText().toString();
                habitStartDate = Calendar.getInstance().getTime();
                hour = Integer.parseInt(hour_view.getText().toString());
                minute = Integer.parseInt(minute_view.getText().toString());
                weekdays = weekday_spinner.getSelectedStrings();
            }
        });

        cancelHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AddHabitActivity.this,HomePageActivity.this);
            }
        });


    }
}
