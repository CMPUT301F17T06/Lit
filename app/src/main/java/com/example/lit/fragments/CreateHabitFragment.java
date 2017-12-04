/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.elasticsearch.ElasticSearchHabitController;
import com.example.lit.habit.NormalHabit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Maxsg on 2017-11-30.
 */

public class CreateHabitFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private static final String TIME_PATTERN = "HH:mm";
    private static final int CAMERA_PIC_REQUEST = 1337;

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    EditText titleInput;
    EditText reasonInput;
    TextView dateInput;
    TextView timeInput;
    ImageButton imageInput;
    Button postHabitButton;

    FragmentActivity listener;
    String username;

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            timeInput.setText(timeFormat.format(calendar.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateInput.setText(sdf.format(calendar.getTime()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(hourOfDay,minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            this.listener = (FragmentActivity) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getActivity().getIntent();
        username = (String) i.getSerializableExtra("USERNAME");
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.CANADA);
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.CANADA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_habit,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleInput = (EditText) view.findViewById(R.id.titleInput);
        reasonInput = (EditText) view.findViewById(R.id.reasonInput);
        dateInput = (TextView) view.findViewById(R.id.dateInput);
        timeInput = (TextView) view.findViewById(R.id.timeInput);
        imageInput = (ImageButton) view.findViewById(R.id.imageInput);
        postHabitButton = (Button) view.findViewById(R.id.postHabitButton);



        dateInput.setText(dateFormat.format(calendar.getTime()));
        timeInput.setText(timeFormat.format(calendar.getTime()));

        imageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });


        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });

        postHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    NormalHabit newHabit = new NormalHabit(titleInput.getText().toString());
                    newHabit.setDate(calendar.getTime());
                    newHabit.setImage(imageInput.getDrawingCache());
                    newHabit.setUser(username);
                    ElasticSearchHabitController.AddHabitsTask addHabitsTask = new ElasticSearchHabitController.AddHabitsTask();
                    addHabitsTask.execute(newHabit);
                    Toast.makeText(getContext(),"New Habit Created!.",Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                } catch(Exception e){
                }
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageInput.setImageBitmap(image);
        }
    }



}
