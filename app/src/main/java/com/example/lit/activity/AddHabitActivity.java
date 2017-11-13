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

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Parcel;
import android.support.v4.app.ActivityCompat;
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

import com.example.lit.Utilities.MultiSelectionSpinner;
import com.example.lit.R;

import com.example.lit.Utilities.SchduledTask;
import com.example.lit.habit.Habit;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.NormalHabit;
import com.example.lit.location.HabitLocation;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class AddHabitActivity extends AppCompatActivity  {

    private static final String CLASS_KEY = "com.example.lit.activity.AddHabitActivity";

    private EditText habitName;
    private EditText habitComment;
    private CheckBox locationCheck; //This should not be a button, its currently a placeholder
    private MultiSelectionSpinner weekday_spinner;
    private Spinner hour_spinner;
    private Spinner minute_spinner;
    Button saveHabit;
    Button cancelHabit;
    private ImageView habitImage;
    private Button editImage;
    //TODO: Implement image feature


    Date habitStartDate;
    String habitNameString;
    String commentString;
    List<String> weekdays;
    Integer hour;
    Integer minute;
    List<Calendar> calendarList;

    LocationManager manager;
    private HabitLocation habitLocation;
    private String provider;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        setTitle("Adding A New Habit");

        // Activity components
        habitName = (EditText) findViewById(R.id.Habit_EditText);
        habitComment = (EditText) findViewById(R.id.Comment_EditText);
        habitComment.setLines(3); //Maximum lines our comment should be able to show at once.
        saveHabit = (Button) findViewById(R.id.save_button);
        cancelHabit = (Button) findViewById(R.id.discard_button);
        hour_spinner = (Spinner) findViewById(R.id.hour_spinner);
        minute_spinner = (Spinner) findViewById(R.id.minute_spinner);
        weekday_spinner = (MultiSelectionSpinner) findViewById(R.id.weekday_spinner);
        locationCheck = (CheckBox) findViewById(R.id.locationCheckBox);

        // Set up weekday selection
        weekday_spinner.setItems(createWeekdayList());
        // Set up hour selection
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, createHourList());
        hourAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hour_spinner.setAdapter(hourAdapter);
        // Set up minute selection
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, createMinuteList());
        minuteAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        minute_spinner.setAdapter(minuteAdapter);


        saveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddHabitActivity", "Save Button pressed.");
                returnNewHabit(view);
                finish();
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

    /**
     * This function is called when user click on save button.
     * This function will build a NormalHabit based on user inputs.
     * The NormalHabit built will be sent back to HomePageActivity
     * @see HomePageActivity
     * @param saveNewHabitButton the current view.
     * */
    public void returnNewHabit(View saveNewHabitButton) {
        habitNameString = habitName.getText().toString();
        commentString = habitComment.getText().toString();
        habitStartDate = Calendar.getInstance().getTime();
        hour = Integer.parseInt(hour_spinner.getSelectedItem().toString());
        minute = Integer.parseInt(minute_spinner.getSelectedItem().toString());
        weekdays = weekday_spinner.getSelectedStrings();
        try {
            calendarList = buildCalender(weekdays, hour, minute);
        } catch (ParseException e) {
            //TODO: handle exception
        }

        /*if checkbox checked return current location*/
        if (locationCheck.isChecked() == true) {
            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Define the criteria how to select the locatioin provider -> use
            // default
            Criteria criteria = new Criteria();
            provider = manager.getBestProvider(criteria, false);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = manager.getLastKnownLocation(provider);
            if (location != null) {
                /*get the latitude and longitude from the location*/
                latitude = location.getLatitude();
                longitude = location.getLongitude();



            }}
        else{
            habitLocation =null;
        }


        Intent newHabitIntent = new Intent(AddHabitActivity.this, HomePageActivity.class);
        try {Habit newHabit = new NormalHabit(habitNameString, habitStartDate,
                null, commentString, calendarList);
            Bundle bundle = new Bundle();
            bundle.putSerializable("habit", newHabit);
            bundle.putDouble("lat",latitude);
            bundle.putDouble("lng",longitude);
            newHabitIntent.putExtras(bundle);

            setResult(Activity.RESULT_OK, newHabitIntent);
            finish();
        } catch (HabitFormatException e){
            Toast.makeText(AddHabitActivity.this,"Error: Illegal Habit information!",Toast.LENGTH_LONG).show();
        }
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

    private List<String> createHourList(){
        List<String> hourList = createNumberList(1,24,1);
        return hourList;
    }

    private List<String> createMinuteList(){
        List<String> hourList = createNumberList(1,60,1);
        return hourList;
    }

    private List<Calendar> buildCalender(List<String> weekdays, int hour, int minute)throws ParseException{
        List<Calendar> calendarList = new ArrayList<Calendar>();
        for (String weekday:weekdays
             ) {
            Calendar calendar = Calendar.getInstance();
            int weekOfDay = parseDayOfWeek(weekday,Locale.CANADA);
            calendar.set(Calendar.DAY_OF_WEEK,weekOfDay);
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            calendarList.add(calendar);

            // Periodic Timer, only a prototype now
            Timer timer = new Timer();
            timer.schedule(new SchduledTask(), calendar.getTime());
        }
        return calendarList;
    }

    // Taken https://stackoverflow.com/questions/18232340/convert-string-to-day-of-week-not-exact-date
    private static int parseDayOfWeek(String day, Locale locale)
            throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", locale);
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    //TODO: should be able to set habit image
    private void setHabitImage(ImageView habitImage){

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
