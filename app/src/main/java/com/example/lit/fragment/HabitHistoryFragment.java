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
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lit.R;
import com.example.lit.activity.AddHabitActivity;
import com.example.lit.activity.MapsActivity;
import com.example.lit.activity.ViewHabitActivity;
import com.example.lit.activity.ViewHabitEventActivity;
import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.HabitEvent;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.location.HabitLocation;
import com.example.lit.saving.DataHandler;
import com.example.lit.saving.ElasticSearchHabitController;
import com.example.lit.saving.NoDataException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HabitHistoryFragment extends Fragment {
    ListView eventListView;
    ArrayList<NormalHabitEvent> eventArrayList ;
    ArrayAdapter<NormalHabitEvent> eventAdapter;
    HabitLocation eventLocation;
    NormalHabitEvent event;
    Button mapButton;
    String username;
    Button searchButton;
    RadioGroup typeToSearch;
    EditText searchString;
    DataHandler<ArrayList<NormalHabitEvent>>  eventdatahandler;
    String type;
    ArrayList<NormalHabitEvent> totalList;

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
        View view = inflater.inflate(R.layout.fragment_habit_history, container, false);
        username = getActivity().getIntent().getExtras().getString("username");
        eventArrayList = new ArrayList<>();
        eventListView = (ListView) view.findViewById(R.id.eventhistory);
        eventAdapter = new ArrayAdapter<NormalHabitEvent>(getActivity(), R.layout.list_item, eventArrayList);
        eventListView.setAdapter(eventAdapter);


        eventdatahandler = new DataHandler<>(username,"habitevent",getActivity(),new TypeToken<ArrayList<NormalHabitEvent>>(){}.getType());

        try{

            eventArrayList = eventdatahandler.loadData();
        }catch (NoDataException e) {
            eventArrayList = new ArrayList<>();
            Toast.makeText(getActivity(), "No data!", Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException n){
            eventArrayList =new ArrayList<>();
        }

        eventAdapter.notifyDataSetChanged();
       /* ElasticSearchHabitController.GetCurrentEventsTask getCurrentEventsTask = new ElasticSearchHabitController.GetCurrentEventsTask();
        getCurrentEventsTask.execute(username);


        try {
            eventArrayList = getCurrentEventsTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the asyc object");
        }*/





        mapButton = (Button) view.findViewById(R.id.Mapbutton);
        searchButton = (Button) view.findViewById(R.id.searchbutton);
        typeToSearch = (RadioGroup) view.findViewById(R.id.radiogroup);
        searchString = (EditText) view.findViewById(R.id.searchstring);

        typeToSearch.clearCheck();


        typeToSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.searchtype){
                    type = "Type";
                }
                else{
                    if (checkedId == R.id.searchcomment){
                        type = "Comment";
                    }
                }

            }});
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ViewHabitEventActivity.class);
                Bundle bundle = new Bundle();
                NormalHabitEvent selectedEvent = eventArrayList.get(i);
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
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("EventList",eventArrayList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setResult(RESULT_OK);

                eventArrayList.clear();
                try{
                    totalList=eventdatahandler.loadData();
                }catch(NoDataException e){
                    Toast.makeText(getActivity(),"No History to search",Toast.LENGTH_SHORT).show();
            }
                if(type ==null){
                    Toast.makeText(getActivity(),"Choose the radio button and try again",Toast.LENGTH_SHORT).show();
                }
                else{

                    if(type == "Type"){
                        for(int i = 0; i< totalList.size();i++){
                            NormalHabitEvent event = totalList.get(i);
                            String name = event.getHabitEventName();
                            if (name.equals(searchString.getText().toString() )){
                                eventArrayList.add(event);
                            }
                    }}
                    else{
                        for(int i = 0; i< totalList.size();i++){
                            NormalHabitEvent event = totalList.get(i);
                            String comment = event.getEventComment();
                            if(comment.equals(searchString.getText().toString())){
                                eventArrayList.add(event);
                            }
                    }
                }
                    eventAdapter.notifyDataSetChanged();

                    searchString.setText("");
                    Toast.makeText(getActivity(),"Search finished",Toast.LENGTH_SHORT).show();

            }
            }});

        // Inflate the layout for this fragment
        return view;

    }
    @Override
    public void onStart() {
        super.onStart();


        eventdatahandler = new DataHandler<>(username,"habitevent",getActivity(),new TypeToken<ArrayList<NormalHabitEvent>>(){}.getType());

        try {
            eventArrayList = eventdatahandler.loadData();
        }catch (NoDataException e){
            Toast.makeText(getActivity(), "Error: Can't load data! code:3", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException n){
            Toast.makeText(getActivity(),"No data",Toast.LENGTH_SHORT).show();
        }
        eventAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, eventArrayList);
        eventListView.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();

    }
}
