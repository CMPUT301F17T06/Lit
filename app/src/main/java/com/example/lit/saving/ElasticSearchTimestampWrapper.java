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

import java.io.Serializable;

/**
 * Created by Riley Dixon on 28/11/2017.
 */

//Would have preferred to have this approach work instead of sending a gson string.

/**
 * A class simply used to also contain the timestamp of when the object was last saved
 * for when passing to ElasticSearch. Saving the timestamp with the actual data
 * is thought to be unnecessary as we only need its last modified date to be
 * comparing the online and offline versions of the file. Also saving in a Collection
 * would not work as the types are different.
 *
 * Documentation was unavailable for saving two objects under the same set of search parameters
 * with ElasticSearch and as such that method was ultimately not chosen.
 *
 * This class is to be paired with DataHandler
 *
 * @param <T> The type of object being saved.
 *
 * @see DataHandler
 * @author Riley Dixon
 */
class ElasticSearchTimestampWrapper<T extends Saveable> implements Serializable{
    private T data;
    private long timestamp;

    ElasticSearchTimestampWrapper(T data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }

    T getData() {
        return data;
    }

    void setData(T data) {
        this.data = data;
    }

    long getTimestamp() {
        return timestamp;
    }

    void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
