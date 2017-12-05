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
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.lit.Utilities.MultiSelectionSpinner;
import com.example.lit.R;
import com.example.lit.Utilities.SchduledTask;
import com.example.lit.exception.BitmapTooLargeException;
import com.example.lit.habit.Habit;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.NormalHabit;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

/*
 * AddHabitActivity
 *
 * Version 1.0
 *
 * Nov.13 2017
 *
 *
 *
 * Copyright 2017 Team 6, CMPUT301, University of Alberta-All Rights Reserved.
 * You may use distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * you may find a copy of the license in the project. Otherwise please contact jiaxiong@ualberta.ca
 */
public class AddHabitActivity extends AppCompatActivity  {

    private static final String CLASS_KEY = "com.example.lit.activity.AddHabitActivity";
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    final int maxSize = 90;

    private EditText habitName;
    private EditText habitComment;
    private MultiSelectionSpinner weekday_spinner;
    private Spinner hour_spinner;
    private Spinner minute_spinner;
    Button saveHabit;
    Button cancelHabit;
    private Bitmap image;
    private ImageView habitImage;
    Button takeImage;
    Date habitStartDate;
    String habitNameString;
    String commentString;
    List<String> weekdays;
    Integer hour;
    Integer minute;
    List<Calendar> calendarList;
    DataHandler dataHandler;
    ArrayList<NormalHabit> habitArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        setTitle("Adding A New Habit");

        // Ignore file URI exposure
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Retrieve data handler
        try {
            Bundle bundle = getIntent().getExtras();
            dataHandler = (DataHandler) bundle.getSerializable("dataHandler");
        }catch (NullPointerException e){
            Toast.makeText(AddHabitActivity.this,"Error: Can't load data! code:3",Toast.LENGTH_LONG).show();
        }
        try {
            habitArrayList = (ArrayList<NormalHabit>) dataHandler.loadData();
        }catch (NoDataException e){
            //Toast.makeText(AddHabitActivity.this,"Error: Can't load habit!",Toast.LENGTH_LONG).show();
            habitArrayList = new ArrayList<>();
        }catch (Exception e1){
            Toast.makeText(AddHabitActivity.this,"Error: Unexpected Exception!",Toast.LENGTH_LONG).show();
            e1.printStackTrace();
            habitArrayList = new ArrayList<>();
            //finish();
        }

        // Activity components
        habitName = (EditText) findViewById(R.id.Habit_EditText);
        habitComment = (EditText) findViewById(R.id.Comment_EditText);
        habitComment.setLines(3); //Maximum lines our comment should be able to show at once.
        saveHabit = (Button) findViewById(R.id.save_button);
        cancelHabit = (Button) findViewById(R.id.discard_button);
        hour_spinner = (Spinner) findViewById(R.id.hour_spinner);
        minute_spinner = (Spinner) findViewById(R.id.minute_spinner);
        weekday_spinner = (MultiSelectionSpinner) findViewById(R.id.weekday_spinner);
        habitImage = (ImageView) findViewById(R.id.HabitImage);
        takeImage = (Button)findViewById(R.id.takeImageButton);

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        // Set up weekday selection
        weekday_spinner.setItems(createWeekdayList());
        // Set up hour selection
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, createHourList());
        hourAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hour_spinner.setAdapter(hourAdapter);
        hourAdapter.notifyDataSetChanged();
        // Set up minute selection
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, createMinuteList());
        minuteAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        minute_spinner.setAdapter(minuteAdapter);
        minuteAdapter.notifyDataSetChanged();

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
     * The NormalHabit built will be saved by DataHandler
     * Return to HomepageActivityNew is saved successfully
     * @see HomePageActivity
     * @param saveNewHabitButton the current view.
     * */
    public void returnNewHabit(View saveNewHabitButton) {
        habitNameString = habitName.getText().toString();
        commentString = habitComment.getText().toString();
        habitStartDate = Calendar.getInstance().getTime();
        weekdays = weekday_spinner.getSelectedStrings();
        try {
            hour = Integer.parseInt(hour_spinner.getSelectedItem().toString());
            minute = Integer.parseInt(minute_spinner.getSelectedItem().toString());
            calendarList = buildCalender(weekdays, hour, minute);
        }catch (Exception e) {
            try{
                calendarList = buildCalender(weekdays);
            }catch (ParseException e2){
                e2.printStackTrace();
            }
        }


        try {NormalHabit newHabit = new NormalHabit(habitNameString, habitStartDate,
                commentString, calendarList,image);
            habitArrayList.add(newHabit);
            dataHandler.saveData(habitArrayList);
            Log.i("AddHabitActivity", "Save button pressed. Habit saved successfully.");
            finish();
        } catch (HabitFormatException e){
            Toast.makeText(AddHabitActivity.this,"Error: Illegal Habit information!",Toast.LENGTH_LONG).show();
        }catch (BitmapTooLargeException e2){
            Toast.makeText(AddHabitActivity.this,"Error: Image too large!",Toast.LENGTH_LONG).show();
        }
        catch (Exception e3){
            e3.printStackTrace();
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
        weekdayList.add("Sunday");
        weekdayList.add("Monday");
        weekdayList.add("Tuesday");
        weekdayList.add("Wednesday");
        weekdayList.add("Thursday");
        weekdayList.add("Friday");
        weekdayList.add("Saturday");

        return weekdayList;
    }

    private List<String> createHourList(){
        List<String> hourList = createNumberList(0,23,1);
        return hourList;
    }

    private List<String> createMinuteList(){
        List<String> hourList = createNumberList(0,59,1);
        return hourList;
    }

    /**
     * Return a calender list. The field in Calender set: weekdays, hour, minute.
     *
     * @param hour hour time
     * @param minute minute time
     * @param weekdays weekday
     * @throws ParseException thrown when fail to parse weekday string
     * @return A Calender list.
     * @see Calendar
     * */
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


    private List<Calendar> buildCalender(List<String> weekdays)throws ParseException{
        List<Calendar> calendarList = new ArrayList<Calendar>();
        for (String weekday:weekdays
                ) {
            Calendar calendar = Calendar.getInstance();
            int weekOfDay = parseDayOfWeek(weekday,Locale.CANADA);
            calendar.set(Calendar.DAY_OF_WEEK,weekOfDay);
            calendarList.add(calendar);

            // Periodic Timer, only a prototype now
            Timer timer = new Timer();
            timer.schedule(new SchduledTask(), calendar.getTime());
        }
        return calendarList;
    }

    /**
     * This function will parse a weekday string (e.g. "Monday") to corresponding integer.
     *
     * Taken https://stackoverflow.com/questions/18232340/convert-string-to-day-of-week-not-exact-date
     *
     * @param day weekday string.
     * @param locale weekday string format
     * @throws ParseException when day is not a weekday string
     * @return dayOfWeek a integer representing day of week in Calender.
     * @see Calendar
     * */
    private static int parseDayOfWeek(String day, Locale locale)
            throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", locale);
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
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
        numberList.add(" ");
        for(int i = low; i <= high; i += interval){
            numberList.add(String.valueOf(i));
        }
        return numberList;
    }


    /**
     * Function used to take picture by camera.
     * taken: https://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
     */
    public void takePicture(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public Bitmap compressPicture(Bitmap bitmap){

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,maxSize,maxSize,true);
        resizedBitmap.reconfigure(maxSize,maxSize, Bitmap.Config.ARGB_8888);

        return resizedBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    image = (Bitmap) data.getExtras().get("data");
                    image = compressPicture(image);
                    habitImage.setImageBitmap(image);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
        }
    }
}