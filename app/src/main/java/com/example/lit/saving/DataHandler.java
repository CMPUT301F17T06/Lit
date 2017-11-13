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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * The DataHandler class is to be used to save an object for longtime storage.
 * This object should be used for a single object or a collection of the same type
 * of objects.
 * Objects that are not the same type should be separated into two different
 * DataHandler objects and as such two different files.
 *
 * @author Riley Dixon
 */

//TODO: BONUS, upgrade to java.nio.files.Files and introduce file locking
    //The above TO DO is just a bonus and should be used to ensure
    //file consistency but ensuring that there is only one writer or reader
    // at a time.

public class DataHandler<T> {
    private long lastOfflineSave;
    private long lastOnlineSave;
    private String FILENAME;

    /**
     * Builds a handler that is used to save data to both local storage for offline use as
     * well as online via ElasticSearch.
     *
     * @param username What the username of the current logged in user is.
     *                 This is required to give a cleaner file hierarchy.
     * @param savedObject What object is currently being saved? Is this HabitList or
     *                    HabitHistory for example.
     * @param context The context of the activity. In most cases when constructing
     *                the object this parameter is "this".
     * TODO: Fix Javadoc to clarify that the below parameter is the generic T and no longer a parameter
     * @param typeOfDataBeingStored The type of data being stored. As arrayList's
     *                              are being used and we cannot confirm what the
     *                              type of the arrayList is at runtime due to Java's
     *                              type erasure of generics at runtime, the type of
     *                              arrayList being passed MUST be passed explicitly.
     *                              See the GSON Javadoc for how to generate the Type
     *                              object to pass to this constructor.
     *
     * @see Gson
     */
    DataHandler(String username, String savedObject, Context context){
        this.FILENAME = context.getFilesDir().getAbsolutePath() + File.separator
                + username;

        File filePath = new File(FILENAME);
        //Check if the subdirectory has been created yet or not
        //Create subdirectory if it hasn't been created yet.
        if(!filePath.exists()){
            filePath.mkdirs();
        }

        FILENAME += File.separator + savedObject + ".sav";
    }


    public void saveArrayList(ArrayList<T> arrayListToBeSaved){
        long currentTime = System.currentTimeMillis();
        try{
            saveArrayToOffline(arrayListToBeSaved);
            lastOfflineSave = currentTime;
        }catch (IOException e) {
            throw new RuntimeException();
        }
        //TODO: Online part
    }

    public void saveSingularElement(T element){
        long currentTime = System.currentTimeMillis();
        try{
            saveSingularElementToOffline(element);
            lastOfflineSave = currentTime;
        }catch (IOException e){
            throw new RuntimeException();
        }
        //TODO: Online part
    }

    public ArrayList<T> loadArrayList(){
        long currentTime = System.currentTimeMillis();
        ArrayList<T> loadedList;
        //TODO: Compare offline and online file, then decide which file to load from
        //TODO: If the files timestamps are different, sync with the newer file
        try{
            loadedList = loadArrayFromOffline();
        }catch (IOException e){
            throw new RuntimeException();
        }
        return loadedList;
    }

    public T loadSingularElement(){
        long currentTime = System.currentTimeMillis();
        T loadedElement;
        //TODO: Compare offline and online file, then decide which file to load from
        //TODO: If the files timestamps are different, sync with the newer file
        try{
            loadedElement = loadSingularElementFromOffline();
        }catch (IOException e){
            throw new RuntimeException();
        }
        return loadedElement;
    }

    private void saveArrayToOffline(ArrayList<T> arrayListToSave)throws IOException{
        FileOutputStream fos = new FileOutputStream(new File(FILENAME));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
        Type typeOfArrayList = new TypeToken<ArrayList<T>>(){}.getType();
        //Write to the stream.
        Gson gson = new Gson();
        gson.toJson(arrayListToSave, typeOfArrayList, out); //OBJECT, TYPE, APPENDABLE
        //Close the writing stream.
        out.flush();
        fos.close();

    }

    private void saveArrayToOnline(){

    }

    private void saveSingularElementToOffline(T singleElementToSave) throws IOException{
        FileOutputStream fos = new FileOutputStream(new File(FILENAME));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
        Type typeOfElement = new TypeToken<T>(){}.getType();
        //Write to the stream.
        Gson gson = new Gson();
        gson.toJson(singleElementToSave, typeOfElement, out); //OBJECT, TYPE, APPENDABLE
        //Close the writing stream.
        out.flush();
        fos.close();
    }

    private void saveSingularElementToOnline(){

    }

    private ArrayList<T> loadArrayFromOffline() throws IOException{
        ArrayList<T> loadedList;
        try {
            FileInputStream fis = new FileInputStream(new File(FILENAME));
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Type typeOfArrayList = new TypeToken<ArrayList<T>>(){}.getType();
            //Read from the stream
            Gson gson = new Gson();
            loadedList = gson.fromJson(in, typeOfArrayList);
            //Close the stream
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
             return new ArrayList<T>();
        }
        return loadedList;
    }

    private ArrayList<T> loadArrayFromOnline(){
        return null;
    }

    private T loadSingularElementFromOffline() throws IOException{
        T loadedElement;

        //Build the FileInputStream
        FileInputStream fis = new FileInputStream(new File(FILENAME));
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        Type typeOfElement = new TypeToken<T>(){}.getType();
        //Read from inputstream
        Gson gson = new Gson();
        loadedElement = gson.fromJson(in, typeOfElement);
        //Close stream
        fis.close();

        return loadedElement;
    }

    private T loadSingularElementFromOnline(){
        return null;
    }

}