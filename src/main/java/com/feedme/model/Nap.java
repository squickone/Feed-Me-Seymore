package com.feedme.model;

import java.io.Serializable;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Nap extends TrackableObject {

    //private variables
    String _end_time;
    String _location;
    int _child_id;

    // Empty constructor
    public Nap() {

    }

    // constructor
    public Nap(int id, String date, String start_time, String end_time, String location, int child_id) {
        this._id = id;
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._location = location;
        this._child_id = child_id;
    }

    // constructor
    public Nap(String date, String start_time, String end_time, String location, int child_id) {
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._location = location;
        this._child_id = child_id;
    }

    // constructor
    public Nap(int id, String date, String start_time, String end_time, String location, int childId, String latitude,
               String longitude, String createdDate, String lastModDate) {

        this._id = id;
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._location = location;
        this._child_id = childId;
        this._latitude = latitude;
        this._longitude = longitude;
        this._createdDate = createdDate;
        this._lastModDate = lastModDate;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getEndTime() {
        return this._end_time;
    }

    public void setEndTime(String end_time) {
        this._end_time = end_time;
    }

    public String getLocation() {
        return this._location;
    }

    public void setLocation(String location) {
        this._location = location;
    }

    public int getChild() {
        return this._child_id;
    }

    public void setChild(int child_id) {
        this._child_id = child_id;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + _id + " - ");
        sb.append("DATE: " + _date + " - ");
        sb.append("START TIME: " + _start_time + " - ");
        sb.append("END TIME: " + _end_time + " - ");
        sb.append("LOCATION: " + _location + " - ");
        sb.append("CHILD ID: " + _child_id + " - ");
        sb.append("LATITUDE: " + _latitude + " - ");
        sb.append("LONGITUDE: " + _longitude + " - ");
        sb.append("CREATED_DATE: " + _createdDate + " - ");
        sb.append("LAST_MOD_DATE: " + _lastModDate);
        return sb.toString();
    }
}