package com.feedme.model;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class Journal {

    //private variables
    int _id;
    String _date;
    String _time;
    String _min_left;
    String _min_right;
    String _ounces;
    int _child_id;

    // Empty constructor
    public Journal(){

    }
    // constructor
    public Journal(int id, String date, String time, String min_left, String min_right, String ounces, int child_id){
        this._id = id;
        this._date = date;
        this._time = time;
        this._min_left = min_left;
        this._min_right = min_right;
        this._ounces = ounces;
        this._child_id = child_id;
   }

    // constructor
    public Journal(String date, String time, String min_left, String min_right, String ounces, int child_id){
        this._date = date;
        this._time = time;
        this._min_left = min_left;
        this._min_right = min_right;
        this._ounces = ounces;
        this._child_id = child_id;
   }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting date
    public String getDate(){
        return this._date;
    }

    // setting date
    public void setDate(String date){
        this._date = date;
    }

    // getting time
    public String getTime(){
        return this._time;
    }

    // setting time
    public void setTime(String time){
        this._time = time;
    }

    // getting minutes left
    public String getMinLeft(){
        return this._min_left;
    }

    // setting minutes left
    public void setMinLeft(String min_left){
        this._min_left = min_left;
    }

    // getting minutes right
    public String getMinRight(){
        return this._min_right;
    }

    // setting minutes right
    public void setMinRight(String min_right){
        this._min_right = min_right;
    }

    // getting ounces
    public String getOunces(){
        return this._ounces;
    }

    // setting ounces
    public void setOunces(String ounces){
        this._ounces = ounces;
    }

    // getting child
    public int getChild(){
        return this._child_id;
    }

    // setting child
    public void setChild(int child_id){
        this._child_id = child_id;
    }

}