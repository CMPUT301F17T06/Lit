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

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lit.R;
import com.example.lit.elasticsearch.ElasticSearchHabitController;
import com.example.lit.exception.HabitFormatException;
import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitList;
import com.example.lit.habit.NormalHabit;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {


    ArrayList<NormalHabit> habitArrayList;
    ArrayAdapter<NormalHabit> habitAdapter;
    NormalHabit testHabit;

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
        habitArrayList = new ArrayList<NormalHabit>();
        ElasticSearchHabitController.GetHabitsTask getHabitsTask = new ElasticSearchHabitController.GetHabitsTask();
        getHabitsTask.execute("");

        try{
            habitArrayList = getHabitsTask.get();
        }
        catch (Exception e){
            Log.i("Error","Failed to get the habits from the asyc object");
        }

        habitAdapter = new ArrayAdapter<NormalHabit>(this.getActivity(),R.layout.list_item,habitArrayList);

        try {
            testHabit = new NormalHabit("TestHabit") {
                @Override
                public String habitType() {
                    return null;
                }
            };
        } catch (HabitFormatException e) {
            e.printStackTrace();
        }




        habitArrayList.add(testHabit);




    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView lv = (ListView) view.findViewById(R.id.habit_ListView);
        lv.setAdapter(habitAdapter);
    }
}
