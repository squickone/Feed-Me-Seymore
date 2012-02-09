package com.feedme.model;

import java.io.Serializable;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 9:17 AM
 */
public abstract class BaseObject implements Serializable {

    int _id;
    String _date;
    String _start_time;
    String _dateTime;
    String _createdDate;
    String _lastModDate;
    
    public int getId(){
        return _id;
    }
    
    public void setId(int id){
        this._id = id;
    }

    public String getDate() {
        return this._date;
    }

    public void setDate(String date) {
        this._date = date;
    }

    public String getStartTime() {
        return this._start_time;
    }

    public void setStartTime(String start_time) {
        this._start_time = start_time;
    }

    public String getDateTime() {
        return _dateTime;
    }

    public void setDateTime(String dateTime) {
        this._dateTime = dateTime;
    }

    public String getCreatedDate() {
        return _createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this._createdDate = createdDate;
    }

    public String getLastModDate() {
        return _lastModDate;
    }

    public void setLastModDate(String lastModDate) {
        this._lastModDate = lastModDate;
    }
}