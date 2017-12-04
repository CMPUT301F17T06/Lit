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


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.lit.R;
import com.example.lit.activity.AddHabitActivity;
import com.example.lit.activity.MapsActivity;
import com.example.lit.activity.ViewHabitActivity;
import com.example.lit.activity.ViewHabitEventActivity;
import com.example.lit.habit.Habit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.location.HabitLocation;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HabitHistoryFragment extends Fragment {
    ListView eventListView;
    ArrayList<HabitEvent> eventArrayList ;
    ArrayAdapter<HabitEvent> eventAdapter;
    HabitLocation eventLocation;
    HabitEvent event;
    Button mapButton;

    public HabitHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_habit_history, container, false);
        eventArrayList = new ArrayList<>();
        eventListView = (ListView) view.findViewById(R.id.eventhistory);
        eventAdapter = new ArrayAdapter<HabitEvent>(getActivity(), R.layout.list_item, eventArrayList);
        eventListView.setAdapter(eventAdapter);
        mapButton = (Button) view.findViewById(R.id.Mapbutton);


        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ViewHabitEventActivity.class);
                Bundle bundle = new Bundle();
                HabitEvent selectedEvent = eventArrayList.get(i);
                bundle.putParcelable("event", selectedEvent);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setResult(RESULT_OK);
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                startActivityForResult(intent,1);
            }
        });
        
        return view;
    }
}
