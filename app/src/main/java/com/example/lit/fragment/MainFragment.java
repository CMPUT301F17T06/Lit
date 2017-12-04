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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.lit.R;
import com.example.lit.activity.AddHabitActivity;
import com.example.lit.activity.ViewHabitActivity;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.ElasticSearchHabitController;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getActivity().getIntent().getExtras().getString("username");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //DataHandler dataHandler = new DataHandler("username", "HabitList", getActivity());
        //habitArrayList = dataHandler.loadData();
        habitArrayList = new ArrayList<>();
        ElasticSearchHabitController.GetTodayHabitsTask getTodayHabitsTask = new ElasticSearchHabitController.GetTodayHabitsTask();
        getTodayHabitsTask.execute(username,date);
        //habitAdapter.notifyDataSetChanged();

        try {
            habitArrayList = getTodayHabitsTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the asyc object");
        }

        habitAdapter = new ArrayAdapter<NormalHabit>(getActivity(), R.layout.list_item, habitArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        habitsListView = (ListView) view.findViewById(R.id.habit_list_view);
        habitsListView.setAdapter(habitAdapter);

        // A dummy habit for testing
        /*try {
            Habit testHabit = new NormalHabit("test habit title");
            habitArrayList.add(testHabit);
            habitAdapter.notifyDataSetChanged();
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
                bundle.putSerializable("habit", selectedHabit);
                bundle.putSerializable("username", username);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });


        // Set up add habit button
        addHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getActivity().setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), AddHabitActivity.class);
                //intent.putExtra("username",username);
                startActivityForResult(intent,1);
            }});

        // Inflate the layout for this fragment
    }

}
