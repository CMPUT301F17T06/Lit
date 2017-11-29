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
import com.example.lit.saving.Saveable;
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


class ElasticSearchHabitController {
    private static JestDroidClient client;

    // adds tweets to elastic search
     static class AddTask<T extends Saveable> extends AsyncTask<T, Void, Void> {
        private String username;
        private String typeOfObject;

        AddTask(String username, String typeOfObject){
            super();
            this.username = username;
            this.typeOfObject = typeOfObject;
        }

        @Override
        protected Void doInBackground(T... objects) {
            verifySettings();

            for (T currentT : objects) {
                Index index = new Index.Builder(currentT).index("cmput301f17t06" + username).type(typeOfObject).build();

                try {

                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        currentT.setId(result.getId());
                    }
                    else
                    {
                        Log.i("Error","Elasticsearch was not able to add the T");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the T");
                }

            }
            return null;
        }
    }

    // gets habits from elastic search
    public static class GetTask<T extends Saveable> extends AsyncTask<String, Void, T> {
        private String username;
        private String typeOfObject;
        private Class<T> classOfObject;

        GetTask(String username, String typeOfObject, Class<T> classOfObject){
            super();
            this.username = username;
            this.typeOfObject = typeOfObject;
            this.classOfObject = classOfObject;
        }

        @Override
        protected T doInBackground(String... search_parameters) {
            verifySettings();

            T loadingObject;

            //ArrayList<NormalHabit> habits;
            //habits = new ArrayList<NormalHabit>();

            // TODO Build the query

            Search search = new Search.Builder(""+search_parameters[0]+"")
                    .addIndex("cmput301f17t06" + username).addType(typeOfObject).build();

            try {
                // get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    //List<NormalHabit> foundHabit = result.getSourceAsObjectList(NormalHabit.class);
                    //habits.addAll(foundHabit);
                    loadingObject = result.getSourceAsObject(classOfObject);
                }
            }
            catch (Exception e) {
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
