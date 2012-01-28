package com.feedme.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class Nap implements Serializable
{

    //private variables
    int _id;
    String _date;
    String _start_time;
    String _end_time;
    String _location;
    int _child_id;

    // Empty constructor
    public Nap()
    {

    }

    // constructor
    public Nap(int id, String date, String start_time, String end_time, String location, int child_id)
    {
        this._id = id;
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._location = location;
        this._child_id = child_id;
    }

    // constructor
    public Nap(String date, String start_time, String end_time, String location, int child_id)
    {
        this._date = date;
        this._start_time = start_time;
        this._end_time = end_time;
        this._location = location;
        this._child_id = child_id;
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

    // getting location
    public String getLocation()
    {
        return this._location;
    }

    // setting location
    public void setLocation(String location)
    {
        this._location = location;
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

}