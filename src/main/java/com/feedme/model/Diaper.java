package com.feedme.model;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Diaper extends TrackableObject {

    //private variables
    String _type;
    String _consistency;
    String _color;
    int _child_id;

    // Empty constructor
    public Diaper() {

    }

    // constructor
    public Diaper(int id, String type, String consistency, String color, String date, String time, String dateTime, int childId) {
        this._id = id;
        this._type = type;
        this._consistency = consistency;
        this._color = color;
        this._date = date;
        this._start_time = time;
        this._dateTime = dateTime;
        this._child_id = childId;
    }

    // constructor
    public Diaper(String type, String consistency, String color, String date, String time, String dateTime, int childId) {
        this._type = type;
        this._consistency = consistency;
        this._color = color;
        this._date = date;
        this._start_time = time;
        this._dateTime = dateTime;
        this._child_id = childId;
    }

    // constructor
    public Diaper(int id, String type, String consistency, String color, String date, String time, String dateTime,
                  int childId, String latitude, String longitude, String createdDate, String lastModDate) {

        this._id = id;
        this._type = type;
        this._consistency = consistency;
        this._color = color;
        this._date = date;
        this._start_time = time;
        this._dateTime = dateTime;
        this._child_id = childId;
        this._latitude = latitude;
        this._longitude = longitude;
        this._createdDate = createdDate;
        this._lastModDate = lastModDate;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public String getConsistency() {
        return _consistency;
    }

    public void setConsistency(String consistency) {
        this._consistency = consistency;
    }

    public String getColor() {
        return _color;
    }

    public void setColor(String color) {
        this._color = color;
    }

    public int getChildId() {
        return _child_id;
    }

    public void setChildId(int childId) {
        this._child_id = childId;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: " + _id + " - ");
        sb.append("TYPE: " + _type + " - ");
        sb.append("CONSISTENCY: " + _consistency + " - ");
        sb.append("COLOR: " + _color + " - ");
        sb.append("DATE: " + _date + " - ");
        sb.append("TIME: " + _start_time + " - ");
        sb.append("LATITUDE: " + _latitude + " - ");
        sb.append("LONGITUDE: " + _longitude + " - ");
        sb.append("CREATED_DATE: " + _createdDate + " - ");
        sb.append("LAST_MOD_DATE: " + _lastModDate);

        return sb.toString();
    }
}