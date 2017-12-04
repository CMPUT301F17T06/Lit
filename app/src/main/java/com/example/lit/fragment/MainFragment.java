/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.activity.AddHabitActivity;
import com.example.lit.activity.ViewHabitActivity;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.NoDataException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    ImageButton addHabitButton;
    ListView habitsListView;
    ArrayList<NormalHabit> habitArrayList;
    ArrayAdapter<NormalHabit> habitAdapter;
    String username;
    DataHandler<ArrayList<NormalHabit>> dataHandler;
    DataHandler<ArrayList<NormalHabitEvent>> eventDataHandler;

    FragmentActivity listener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        username = getActivity().getIntent().getExtras().getString("username");

        habitArrayList = new ArrayList<>();
                /*ElasticSearchHabitController.GetTodayHabitsTask getTodayHabitsTask = new ElasticSearchHabitController.GetTodayHabitsTask();
        getTodayHabitsTask.execute(username,date);


        try {
            habitArrayList = getTodayHabitsTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the asyc object");
        }*/
        habitAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, habitArrayList);
        habitsListView = (ListView) view.findViewById(R.id.habit_list_view);
        habitsListView.setAdapter(habitAdapter);
        habitAdapter.notifyDataSetChanged();

        dataHandler = new DataHandler<>(username,"habit",getActivity(), new TypeToken<ArrayList<NormalHabit>>(){}.getType());
        eventDataHandler = new DataHandler<>(username,"habitevent",getActivity(),new TypeToken<ArrayList<NormalHabitEvent>>(){}.getType());
        try {
            habitArrayList = dataHandler.loadData();
        }catch (NoDataException e){
            habitArrayList = new ArrayList<>();
        }


        // A dummy habit for testing
        /*
        try {
            NormalHabit testHabit = new NormalHabit("test habit title");
            habitArrayList.add(testHabit);
            habitAdapter.notifyDataSetChanged();
            dataHandler.saveData(habitArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        addHabitButton = (ImageButton) view.findViewById(R.id.add_habit_button);

        habitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(),ViewHabitActivity.class);
                Bundle bundle = new Bundle();
                Habit selectedHabit = habitArrayList.get(i);
                bundle.putParcelable("habit", selectedHabit);
                bundle.putSerializable("dataHandler",dataHandler);
                bundle.putSerializable("eventDataHandler",eventDataHandler);
                bundle.putInt("index",i);
                //bundle.putString("username",username);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });


        // Set up add habit button
        addHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getActivity().setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), AddHabitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataHandler",dataHandler);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }});
        return view;
        // Inflate the layout for this fragment
    }
    @Override
    public void onStart() {
        super.onStart();
        habitAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, habitArrayList);
        habitsListView.setAdapter(habitAdapter);
        try {
            habitArrayList = dataHandler.loadData();
        }catch (NoDataException e){
            Toast.makeText(getActivity(), "Error: Can't load data! code:3", Toast.LENGTH_SHORT).show();
        }

        habitAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        try{
            habitArrayList = dataHandler.loadData();
            habitAdapter.notifyDataSetChanged();
        }catch (NoDataException e){
            Toast.makeText(getActivity(), "Error: Can't load data! code:1", Toast.LENGTH_SHORT).show();
        }
    }

}
