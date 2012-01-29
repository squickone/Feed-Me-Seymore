package com.feedme.model;

import java.io.Serializable;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Baby implements Serializable
{

    //private variables
    int _id;
    String _name;
    String _sex;
    String _height;
    String _weight;
    String _dob;
    String _picturePath;
    String _latitude;
    String _longitude;
    String _createdDate;
    String _lastModDate;

    // Empty constructor
    public Baby()
    {

    }

    // constructor
    public Baby(int id, String name, String sex, String height, String weight, String dob, String picturePath) {

        this._id = id;
        this._name = name;
        this._sex = sex;
        this._height = height;
        this._weight = weight;
        this._dob = dob;
        this._picturePath = picturePath;
    }

    // constructor
    public Baby(String name, String sex, String height, String weight, String dob, String picturePath) {

        this._name = name;
        this._sex = sex;
        this._height = height;
        this._weight = weight;
        this._dob = dob;
        this._picturePath = picturePath;
    }

    // constructor
    public Baby(int id, String name, String sex, String height, String weight, String dob, String picturePath,
                String latitude, String longitude, String createdDate, String lastModDate) {

        this._id = id;
        this._name = name;
        this._sex = sex;
        this._height = height;
        this._weight = weight;
        this._dob = dob;
        this._picturePath = picturePath;
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

    // getting name
    public String getName()
    {
        return this._name;
    }

    // setting name
    public void setName(String name)
    {
        this._name = name;
    }

    // getting sex
    public String getSex()
    {
        return this._sex;
    }

    // setting sex
    public void setSex(String sex)
    {
        this._sex = sex;
    }

    // getting height
    public String getHeight()
    {
        return this._height;
    }

    // setting height
    public void setHeight(String height)
    {
        this._height = height;
    }

    // getting weight
    public String getWeight()
    {
        return this._weight;
    }

    // setting weight
    public void setWeight(String weight)
    {
        this._weight = weight;
    }

    // getting dob
    public String getDob()
    {
        return this._dob;
    }

    // setting dob
    public void setDob(String dob)
    {
        this._dob = dob;
    }

    // getting picturePath
    public String getPicturePath()
    {
        return _picturePath;
    }

    // setting picturePath
    public void setPicturePath(String picturePath)
    {
        this._picturePath = picturePath;
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
        sb.append("NAME: " + _name + " - ");
        sb.append("SEX: " + _sex + " - ");
        sb.append("HEIGHT: " + _height + " - ");
        sb.append("WEIGHT: " + _weight + " - ");
        sb.append("DOB: " + _dob + " - ");
        sb.append("PICTURE PATH: " + _picturePath);
        sb.append("LATITUDE: " + _latitude);
        sb.append("LONGITUDE: " + _longitude);
        sb.append("CREATED_DATE: " + _createdDate);
        sb.append("LAST_MOD_DATE: " + _lastModDate);
        
        return sb.toString();
    }
}