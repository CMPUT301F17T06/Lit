/*
 * Copyright 2017 Max Schafer, Ammar Mahdi, Riley Dixon, Steven Weikai Lu, Jiaxiong Yang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.lit.elasticsearch;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lit.habit.Habit;
import com.example.lit.habit.NormalHabit;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


public class ElasticSearchHabitController {
    private static JestDroidClient client;

    // adds tweets to elastic search
    public static class AddHabitsTask extends AsyncTask<NormalHabit, Void, Void> {

        @Override
        protected Void doInBackground(NormalHabit... habits) {
            verifySettings();

            for (NormalHabit habit : habits) {
                Index index = new Index.Builder(habit).index("testing").type("habit").build();

                try {

                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        habit.setId(result.getId());
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

    // gets habits from elastic search
    public static class GetHabitsTask extends AsyncTask<String, Void, ArrayList<NormalHabit>> {
        @Override
        protected ArrayList<NormalHabit> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalHabit> habits;
            habits = new ArrayList<NormalHabit>();

            // TODO Build the query

            Search search = new Search.Builder(""+search_parameters[0]+"").addIndex("cmput301f17t06").addType("habit").build();

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
