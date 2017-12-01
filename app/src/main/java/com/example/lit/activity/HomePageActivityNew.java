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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.lit.R;
import com.example.lit.Utilities.DataModel;
import com.example.lit.Utilities.DrawerItemCustomAdapter;
import com.example.lit.habit.Habit;
import com.example.lit.habit.HabitList;
import com.example.lit.habit.NormalHabitList;
import com.example.lit.location.HabitLocation;
import com.example.lit.saving.DataHandler;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HomePageActivityNew extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    //private FrameLayout frameLayout;

    private String username;
    private ListView habitsListView;
    //private ArrayList<Habit> habitArrayList;
    private HabitList habitArrayList;
    ArrayAdapter<Habit> habitAdapter;

    ImageButton addHabitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_new);

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        //habitArrayList = new ArrayList<>();
        habitArrayList = new NormalHabitList();
        habitsListView = (ListView)findViewById(R.id.habit_ListView);
        habitAdapter = new ArrayAdapter<Habit>(this,R.layout.list_item,habitArrayList.getHabits());
        habitsListView.setAdapter(habitAdapter);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[4];

        drawerItem[0] = new DataModel(R.drawable.habithistory, "HabitHistory");
        drawerItem[1] = new DataModel(R.drawable.friends, "Friends");
        drawerItem[2] = new DataModel(R.drawable.map, "Map");
        drawerItem[3] = new DataModel(R.drawable.profile, "Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        selectItem(0);

        // Set up habit List in home page
        try{
            Bundle bundle = getIntent().getExtras();
            username = bundle.getString("username");
            assert username != null;
        }catch (Exception e){
            e.printStackTrace();
        }

        addHabitButton = (ImageButton) findViewById(R.id.AddHabit);
        habitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(HomePageActivityNew.this,ViewHabitActivity.class);
                Bundle bundle = new Bundle();
                Habit selectedHabit = habitArrayList.getHabit(i);
                try {
                    bundle.putSerializable("habit",selectedHabit);
                }catch (Exception e){
                    //TODO: handle when location is null
                }
                bundle.putSerializable("habit", selectedHabit);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });

        // Set up add habit button
        addHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(v.getContext(), AddHabitActivity.class);
                intent.putExtra("username",username);
                startActivityForResult(intent,1);
            }});
    }

    @Override
    protected void onStart(){
        super.onStart();
        DataHandler dataHandler = new DataHandler(username,"HabitList",HomePageActivityNew.this);
        //TODO: load all habits by DataHandler
        habitArrayList = dataHandler.loadData();
        habitAdapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, habitArrayList.getHabits());
        habitsListView.setAdapter(habitAdapter);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                //fragment = new TestFragment();
                break;
            case 1:
                //fragment = new FriendsFragment();
                break;
            case 2:
                //fragment = new MapFragment();
                break;
            case 3:
                //fragment = new ProfileFragment();

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment fragmentDefault = new TestFragment();
            //fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentDefault);
            //Log.e("HomePageActivityNew", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

}
