/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.saving;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lit.habit.NormalHabit;
import com.example.lit.userprofile.UserProfile;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

//TODO: Further partition data by user

/**
 * The ElasticSearch class that is used to construct static objects for when needing to save
 * data objects online. This connects to the CMPUT 301 softwareprocess server that is available
 * for purposes of the class and uses our team ID as the index for our data.
 *
 * @author Max Schafer, modified by Riley Dixon
 * @see DataHandler
 */
public class ElasticSearchHabitController {
     private static JestDroidClient client;

    // Add Habit to elastic search
    public static class AddHabitsTask extends AsyncTask<NormalHabit, Void, Void> {

        @Override
        protected Void doInBackground(NormalHabit... habits) {
            verifySettings();

            for (NormalHabit habit : habits) {
                Index index = new Index.Builder(habit).index("cmput301f17t06").type("habit").build();

                try {

                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        habit.setID(result.getId());
                    }
                    else
                    {
                        Log.i("Error","Elasticsearch was not able to add the habit");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }

            }
            return null;
        }
    }

    // Gets all habits for the user specified
    public static class GetCurrentHabitsTask extends AsyncTask<String, Void, ArrayList<NormalHabit>> {
        @Override
        protected ArrayList<NormalHabit> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalHabit> habits;
            habits = new ArrayList<NormalHabit>();


            String query = "{\n" +
                    "    \"query\" : {\n" +
                    "       \"constant_score\" : {\n" +
                    "           \"filter\" : {\n" +
                    "               \"term\" : {\"user\": \"" + search_parameters[0] + "\"}\n" +
                    "             }\n" +
                    "         }\n" +
                    "    }\n" +
                    "}";
            Search search = new Search.Builder(query).addIndex("cmput301f17t06").addType("habit").build();


            try {
                // get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    List<NormalHabit> foundHabit = result.getSourceAsObjectList(NormalHabit.class);
                    habits.addAll(foundHabit);
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return habits;
        }
    }

    // Gets all habits for the user specified
    public static class GetTodayHabitsTask extends AsyncTask<String, Void, ArrayList<NormalHabit>> {
        @Override
        protected ArrayList<NormalHabit> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalHabit> habits;
            habits = new ArrayList<NormalHabit>();


            String query = "{\n" +
                    "    \"query\" : {\n" +
                    "       \"constant_score\" : {\n" +
                    "           \"filter\" : {\n" +
                    "               \"term\" : {\"date\": \"" + search_parameters[0] + "\"}\n" +
                    "             }\n" +
                    "         }\n" +
                    "    }\n" +
                    "}";
            Search search = new Search.Builder(query).addIndex("cmput301f17t06").addType("habit").build();

            try {
                // get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    List<NormalHabit> foundHabit = result.getSourceAsObjectList(NormalHabit.class);
                    habits.addAll(foundHabit);
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return habits;
        }
    }

    // Add new user to elastic search
    public static class AddUserTask extends AsyncTask<UserProfile, Void, Void> {

        @Override
        protected Void doInBackground(UserProfile... userProfiles) {
            verifySettings();

            //TODO fix Mapping parse error document is full.
            for (UserProfile userProfile : userProfiles) {
                Index index = new Index.Builder(userProfile).index("cmput301f17t06").type("user").build();

                try {

                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        userProfile.setID(result.getId());
                    }
                    else
                    {
                        Log.i("Error","Elasticsearch was not able to add the habit");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }

            }
            return null;
        }
    }

    // Get user from elastic search
    public static class GetUserTask extends AsyncTask<String, Void, UserProfile> {
        private UserProfile userProfile = null;

        @Override
        protected UserProfile doInBackground(String... search_parameters) {
            verifySettings();

            String query = "{\n" +
                    "    \"query\" : {\n" +
                    "       \"constant_score\" : {\n" +
                    "           \"filter\" : {\n" +
                    "               \"term\" : {\"name\": \"" + search_parameters[0] + "\"}\n" +
                    "             }\n" +
                    "         }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex("cmput301f17t06").addType("user").build();

            try {
                // get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded() && result.getFirstHit(UserProfile.class).source != null)
                {
                    userProfile = result.getFirstHit(UserProfile.class).source;
                } else {
                    Log.i("Error", "The search query failed to find any Users that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return userProfile;
        }
    }

     static class AddTask<T extends Saveable> extends AsyncTask<ElasticSearchTimestampWrapper<T>, Void, Void> {
        private String username;
        private String typeOfObject;

        AddTask(String username, String typeOfObject){
            super();
            this.username = username;
            this.typeOfObject = typeOfObject;
        }

        @Override
        protected Void doInBackground(ElasticSearchTimestampWrapper<T>... objects) {
            verifySettings();

            for (ElasticSearchTimestampWrapper<T> currentT : objects) {
                Index index = new Index.Builder(currentT).index("cmput301f17t06" + username).type(typeOfObject).build();

                try {
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded()){
                        currentT.getData().setID(result.getId());
                    }else{
                        Log.i("Error","Elasticsearch was not able to add the T");
                        //TODO: What does ES return upon failure?
                        //throw new NotOnlineException();
                        //Let user know it couldn't save online successfully.
                    }
                }catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the T");
                }

            }
            return null;
        }
    }

    static class GetTask<T extends Saveable> extends AsyncTask<String, Void, ElasticSearchTimestampWrapper<T>> {
        private String username;
        private String typeOfObject;

        GetTask(String username, String typeOfObject){
            super();
            this.username = username;
            this.typeOfObject = typeOfObject;
        }

        @Override
        protected ElasticSearchTimestampWrapper<T> doInBackground(String... search_parameters) {
            verifySettings();

            ElasticSearchTimestampWrapper<T> loadingObject = null;

            Search search = new Search.Builder(""+search_parameters[0]+"")
                    .addIndex("cmput301f17t06" + username).addType(typeOfObject).build();

            try {
                // get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    loadingObject = result.getSourceAsObject(ElasticSearchTimestampWrapper.class);
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return loadingObject;
        }
    }


    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
