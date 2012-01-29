package com.feedme.model;

import java.io.Serializable;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Journal implements Serializable
{

    //private variables
    int _id;
    String _date;
    String _start_time;
    String _end_time;
    String _feed_time;
    String _side;
    String _ounces;
    int _child_id;
    String _latitude;
    String _longitude;
    String _createdDate;
    String _lastModDate;

    // Empty constructor
    public Journal()
    {

    }

    // constructor
    public Journal(int id,
                   String date,
                   String start_time,
                   String end_time,
                   String feed_time,
                   String side,
                   String ounces,
                   int child_id)
    {
        this._id = id;
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._feed_time = feed_time;
        this._side = side;
        this._ounces = ounces;
        this._child_id = child_id;
    }

    // constructor
    public Journal(String date,
                   String start_time,
                   String end_time,
                   String feed_time,
                   String side,
                   String ounces,
                   int child_id)
    {
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._feed_time = feed_time;
        this._side = side;
        this._ounces = ounces;
        this._child_id = child_id;
    }

    public Journal(int id, String date, String startTime, String endTime, String feedTime, String side,
                   String ounces, int childId, String latitude, String longitude, String createdDate,
                   String lastModDate) {
        this._id = id;
        this._date = date;
        this._start_time = startTime;
        this._end_time = endTime;
        this._feed_time = feedTime;
        this._side = side;
        this._ounces = ounces;
        this._child_id = childId;
        this._latitude = latitude;
        this._longitude = longitude;
        this._createdDate = createdDate;
        this._lastModDate = lastModDate;
    }

    // getting ID
    public int getID()
    {
        return this._id;
    }

    // setting id
    public void setID(int id)
    {
        this._id = id;
    }

    // getting date
    public String getDate()
    {
        return this._date;
    }

    // setting date
    public void setDate(String date)
    {
        this._date = date;
    }

    // getting start time
    public String getStartTime()
    {
        return this._start_time;
    }

    // setting start time
    public void setStartTime(String start_time)
    {
        this._start_time = start_time;
    }

    // getting end time
    public String getEndTime()
    {
        return this._end_time;
    }

    // setting end time
    public void setEndTime(String end_time)
    {
        this._end_time = end_time;
    }

    // getting feed start time
    public String getFeedTime()
    {
        return this._feed_time;
    }

    // setting feed end time
    public void setFeedTime(String feed_time)
    {
        this._feed_time = feed_time;
    }

    // getting side
    public String getSide()
    {
        return this._side;
    }

    // setting side
    public void setSide(String side)
    {
        this._side = side;
    }

    // getting ounces
    public String getOunces()
    {
        return this._ounces;
    }

    // setting ounces
    public void setOunces(String ounces)
    {
        this._ounces = ounces;
    }

    // getting child
    public int getChild()
    {
        return this._child_id;
    }

    // setting child
    public void setChild(int child_id)
    {
        this._child_id = child_id;
    }

    public String getLastModDate() {
        return _lastModDate;
    }

    public void setLastModDate(String lastModDate) {
        this._lastModDate = lastModDate;
    }

    public String getCreatedDate() {
        return _createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this._createdDate = createdDate;
    }

    public String getLongitude() {
        return _longitude;
    }

    public void setLongitude(String longitude) {
        this._longitude = longitude;
    }

    public String getLatitude() {
        return _latitude;
    }

    public void setLatitude(String latitude) {
        this._latitude = latitude;
    }

    public String dump()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + _id + " - ");
        sb.append("DATE: " + _date + " - ");
        sb.append("START TIME: " + _start_time + " - ");
        sb.append("END TIME: " + _end_time + " - ");
        sb.append("FEED TIME: " + _feed_time + " - ");
        sb.append("SIDE: " + _side + " - ");
        sb.append("OZ: " + _ounces + " - ");
        sb.append("CHILD ID: " + _child_id + " - ");
        sb.append("LATITUDE: " + _latitude + " - ");
        sb.append("LONGITUDE: " + _longitude + " - ");
        sb.append("CREATED_DATE: " + _createdDate + " - ");
        sb.append("LAST_MOD_DATE: " + _lastModDate);
        return sb.toString();
    }
}