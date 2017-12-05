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

import android.content.Context;
import android.util.Log;

import com.example.lit.habit.NormalHabit;
import com.example.lit.habitevent.NormalHabitEvent;
import com.example.lit.userprofile.UserProfile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import io.searchbox.annotations.JestId;


//TODO: BONUS, upgrade to java.nio.files.Files and introduce file locking
    //The above TO DO is just a bonus and should be used to ensure
    //file consistency but ensuring that there is only one writer or reader
    // at a time.

/**
 * The DataHandler class is to be used to save an object for longtime storage.
 * This object should be used for a single object or a collection of the same type
 * of objects.
 * Objects that are not the same type should be separated into two different
 * DataHandler objects and as such two different files.
 *
 * An alternative to having the user provide the filename would
 * be to use class reflection and have that determine the file name.
 *
 * @author Riley Dixon
 * @param <T> The type of data we are trying to save.
 */
//public class DataHandler<T extends Saveable> {
public class DataHandler<T> implements Serializable{
    private long lastOfflineSave;
    private long lastOnlineSave;
    private String username;
    private String nameOfObject;
    private String FILENAME;
    private Type typeOfObject;
    private String jestID;

    public String getJestID() {
        return jestID;
    }

    public void setJestID(String jestID) {
        this.jestID = jestID;
    }

    /**
     * Builds a handler that is used to save data to both local storage for offline use as
     * well as online via ElasticSearch. T is the type of object that is to be stored.
     *
     * @param username What the username of the current logged in user is.
     *                 This is required to give a cleaner file hierarchy.
     * @param typeOfObject What object is currently being accessed? This designates the file name
     *                     to be read from and written to.
     * @param context The context of the activity. In most cases when constructing
     *                the object this parameter is "this". This is used for accessing
     *                the file system for offline/local storage.
     *
     * @see Gson
     */
    public DataHandler(String username, String nameOfObject, Context context, Type typeOfObject){
        this.FILENAME = context.getFilesDir().getAbsolutePath() + File.separator
                + username;
        this.username = username;
        this.nameOfObject = nameOfObject;
        this.typeOfObject = typeOfObject;


        File filePath = new File(FILENAME);
        //Check if the subdirectory has been created yet or not
        //Create subdirectory if it hasn't been created yet.
        if(!filePath.exists()){
            filePath.mkdirs();
        }

        FILENAME += File.separator + typeOfObject + ".sav";
    }

    /**
     * Saves the data both offline and online.
     *
     * @param element The element or object that we want to save
     */
    public void saveData(T element){
        long currentTime = System.currentTimeMillis();

        try{
            saveToOnline(element,typeOfObject,currentTime);
            lastOnlineSave = currentTime;
        }catch (Exception e){
            Log.i("Server","Offline");
        }
        try{
            saveToOffline(element, currentTime);
            lastOfflineSave = currentTime;
        }catch (IOException e){
            throw new RuntimeException();
        }
        /*
        try{
            saveToOnline(element, currentTime);
            lastOnlineSave = currentTime;
        }catch (NotOnlineException e){
            throw new RuntimeException();
        }*/
    }

    /**
     * Returns the newest version of the data from either online or offline.
     *
     * @throws NoDataException If no data was able to be loaded this exception is thrown.
     * @return Returns the save data for object type T.
     */
    public T loadData() throws NoDataException {
        T loadedElementOffline;
        T loadedElementOnline;

      /*  try{
            loadedElementOnline = loadFromOnline(typeOfObject,element);
        }catch (Exception e){
            this.lastOnlineSave = 0;
            loadedElementOnline = null;
        }*/

        try {
            loadedElementOffline = loadFromOffline();
        } catch (IOException e) {
            this.lastOfflineSave = 0;
            loadedElementOffline = null;
        }
        /*
        try {
            loadedElementOnline = loadFromOnline();
        } catch (NotOnlineException e) {
            this.lastOnlineSave = 0;
            loadedElementOnline = null;
        }
        */

        if (this.lastOfflineSave == 0 && this.lastOnlineSave == 0) {
            //If no data was loaded, was it lost or no data existed in the first place.
            throw new NoDataException("No data to load.");
        }else if(this.lastOfflineSave > this.lastOnlineSave){
            //NOTE: If timestamps are equal than either can be chosen
            //However in a matter of testing the objects will be saved at the same time
            //Thus for testing purposes online will be preferred to verify it is saving
            //and loading correctly. This inequality may be changed to greater than or equals
            //if online fails or wanting to test offline features.
            return loadedElementOffline;
        }else{
            return loadedElementOffline;//loadedElementOnline;
        }
    }

    /**
     * Saves the object to the local file system. This should not be accessed outside of this class.
     *
     * @param dataToSave The piece of data that is to be saved.
     * @param currentTime The time that we are saving the data. This time is when the request was
     *                    started, not necessarily when the data was written.
     * @throws IOException Does the file not exist?
     */
    private void saveToOffline(T dataToSave, long currentTime) throws IOException{
        //Build the file streams
        FileOutputStream fos = new FileOutputStream(new File(FILENAME));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
        Type typeOfElement = new TypeToken<T>(){}.getType();

        //Write to the stream.
        Gson gson = new Gson();
        Collection collection = new ArrayList();
        collection.add(currentTime);
        collection.add(dataToSave);
        gson.toJson(collection, out);

        //Close the writing stream.
        out.flush();
        fos.close();
    }

    /**
     * Saves the object to online. This should not be accessed outside of this class.
     *
     * @param dataToSave The piece of data that is to be saved.
     * @param currentTime The time that we are saving the data. This time is when the request was
     *                    started, not necessarily when the data was written.
     * @throws NotOnlineException If we cannot connect to the server
     */
    private void saveToOnline(T dataToSave,Type typeOfObject, long currentTime) throws NotOnlineException{
        if (typeOfObject == UserProfile.class){
            try{
                ElasticSearchHabitController.AddUserTaskDH addUserTaskDH = new ElasticSearchHabitController.AddUserTaskDH();
                addUserTaskDH.execute(dataToSave);
            }catch (Exception e){
            }
        }

        if (typeOfObject == NormalHabit.class){
            try{
                ElasticSearchHabitController.AddHabitsTaskDH addHabitsTaskDH = new ElasticSearchHabitController.AddHabitsTaskDH();
                addHabitsTaskDH.execute(dataToSave);
            }catch (Exception e){
            }
        }

        if (typeOfObject == NormalHabitEvent.class){
            try{
                ElasticSearchHabitController.AddHabitEventTaskDH addHabitEventTaskDH = new ElasticSearchHabitController.AddHabitEventTaskDH();
                addHabitEventTaskDH.execute(dataToSave);
            }catch (Exception e){
            }
        }
    }

    /**
     * Loads data from the offline save file. This should not be accessed outside of
     * this class.
     *
     * @return Returns the object
     * @throws IOException If there was a problem writing to the file
     */
    private T loadFromOffline() throws IOException{
        T loadedElement;
        long tempTime; //TempTime is used incase we do not load the actual data successfully.
                       //lastOfflineSave is used for the last successful load of data.

        //Build the FileInputStream
        FileInputStream fis = new FileInputStream(new File(FILENAME));
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        Type typeOfElement = new TypeToken<T>(){}.getType();

        //Read from input stream
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(in.readLine()).getAsJsonArray();

        //JsonArray jsonArray = jsonParser.parse(gson.fromJson(in, String.class)).getAsJsonArray();
        tempTime = gson.fromJson(jsonArray.get(0), long.class);
        loadedElement = gson.fromJson(jsonArray.get(1), typeOfObject);

        //Close stream
        fis.close();

        this.lastOfflineSave = tempTime;
        return loadedElement;
    }

    /**
     * Load data from online. This should not be accessed outside of
     * this class.
     *
     *
     * @return Returns the requested data of type T
     * @throws NotOnlineException If ES cannot connect to the internet throw this
     * @throws NoDataException If ES pulls no data from the requested query throw this
     */
    private T loadFromOnline(Type typeOfObject,T dataToLoad) throws NotOnlineException, NoDataException {
        ElasticSearchTimestampWrapper<T> loadedElement = null;

        /*if (typeOfObject == UserProfile.class){
            try{
                ElasticSearchHabitController.GetUserTaskDH getUserTaskDH = new ElasticSearchHabitController.GetUserTaskDH();
                getUserTaskDH.execute(dataToLoad.toString());
                return getUserTaskDH.get();
            }catch (Exception e){
            }
        }

        if (typeOfObject == NormalHabit.class){
            try{
                ElasticSearchHabitController.GetCurrentHabitsTaskDH getCurrentHabitsTaskDH = new ElasticSearchHabitController.GetCurrentHabitsTaskDH();
                getCurrentHabitsTaskDH.execute(dataToLoad.toString());

            }catch (Exception e){
            }
        }

        if (typeOfObject == NormalHabitEvent.class){
            try{

            }catch (Exception e){
            }
        }*/

        //NOTE: Im not sure what ES returns on fail, however this at least prevents
        //NullPointerExceptions.
        if(loadedElement == null){
            throw new NoDataException();
        }

        this.lastOnlineSave = loadedElement.getTimestamp();
        return loadedElement.getData();
    }

}
