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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lit.R;
import com.example.lit.exception.LoadHabitException;
import com.example.lit.habit.Habit;

import java.io.Serializable;

public class ViewHabitActivity extends AppCompatActivity {

    private static final String CLASS_KEY = "com.example.lit.activity.ViewHabitActivity";

    Serializable serializable;
    Habit currentHabit;
    String habitTitleString;
    String habitCommentString;
    String habitDateStartedString;
    String habitDateCompletedString;
    TextView habitTitle;
    TextView habitComment;
    TextView habitDateStarted;
    Button editHabit;
    Button deleteHabit;

    // TODO: Habit image feature
    ImageView habitImage;
    Button habitDoneToday;          //Not sure what this should be, Button is a placeholder.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        try{
            serializable = getIntent().getExtras().getSerializable("habit");
            if (!(serializable instanceof Habit)) throw new LoadHabitException();
        }catch (LoadHabitException e){
            //TODO: handle LoadHabitException
        }
        // Retrieve habit info
        currentHabit = (Habit) serializable;
        habitTitleString = currentHabit.getTitle();
        habitCommentString = currentHabit.getReason();
        habitDateCompletedString = currentHabit.getDate().toString();

        // Set up view components
        habitTitle = (TextView) findViewById(R.id.habit_title_TextView);
        habitComment = (TextView) findViewById(R.id.Comment_TextView);
        habitDateStarted = (TextView) findViewById(R.id.habit_started_date_TextView);
        habitTitle.setText(habitTitleString);
        habitComment.setText(habitCommentString);
        habitDateStarted.setText(habitDateStartedString);

        // Set up buttons
        editHabit = (Button) findViewById(R.id.edit_habit_button);
        deleteHabit = (Button) findViewById(R.id.delete_habit_button);

        editHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditHabitActivity(serializable);
            }
        });

        deleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHabit(currentHabit);
            }
        });

    }

    public void toEditHabitActivity(Serializable serializable){
        Intent intent = new Intent(ViewHabitActivity.this, EditHabitActivity.class);
        Log.i("ViewHabitActivity", "Edit button pressed.");
        intent.putExtra(CLASS_KEY,serializable);
        startActivity(intent);
    }

    //TODO: delete current habit and return to previous activity
    public void deleteHabit(Habit habit){
        //
    }
}
