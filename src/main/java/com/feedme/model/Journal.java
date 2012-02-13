package com.feedme.model;

import java.io.Serializable;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Journal extends TrackableObject {

    //private variables
    String _end_time;
    String _feed_time;
    String _side;
    String _ounces;
    String _child_id;

    // Empty constructor
    public Journal() {

    }

    // constructor
    public Journal(String id,
                   String date,
                   String start_time,
                   String end_time,
                   String feed_time,
                   String side,
                   String ounces,
                   String child_id) {
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
                   String child_id) {
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._feed_time = feed_time;
        this._side = side;
        this._ounces = ounces;
        this._child_id = child_id;
    }

    public Journal(String id, String date, String startTime, String endTime, String dateTime, String feedTime, String side,
                   String ounces, String childId, String latitude, String longitude, String createdDate,
                   String lastModDate) {
        this._id = id;
        this._date = date;
        this._start_time = startTime;
        this._end_time = endTime;
        this._dateTime = dateTime;
        this._feed_time = feedTime;
        this._side = side;
        this._ounces = ounces;
        this._child_id = childId;
        this._latitude = latitude;
        this._longitude = longitude;
        this._createdDate = createdDate;
        this._lastModDate = lastModDate;
    }

    public String getEndTime() {
        return this._end_time;
    }

    public void setEndTime(String end_time) {
        this._end_time = end_time;
    }

    public String getFeedTime() {
        return this._feed_time;
    }

    public void setFeedTime(String feed_time) {
        this._feed_time = feed_time;
    }

    public String getSide() {
        return this._side;
    }

    public void setSide(String side) {
        this._side = side;
    }

    public String getOunces() {
        return this._ounces;
    }

    public void setOunces(String ounces) {
        this._ounces = ounces;
    }

    public String getChild() {
        return this._child_id;
    }

    public void setChild(String child_id) {
        this._child_id = child_id;
    }

    public String dump() {
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