package com.feedme.model;

import java.io.Serializable;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Baby extends TrackableObject {

    //private variables
    String _name;
    String _sex;
    String _height;
    String _weight;
    String _dob;
    String _picturePath;

    // Empty constructor
    public Baby() {

    }

    // constructor
    public Baby(String id, String name, String sex, String height, String weight, String dob, String picturePath) {

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
    public Baby(String id, String name, String sex, String height, String weight, String dob, String picturePath,
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

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getSex() {
        return this._sex;
    }

    public void setSex(String sex) {
        this._sex = sex;
    }

    public String getHeight() {
        return this._height;
    }

    public void setHeight(String height) {
        this._height = height;
    }

    public String getWeight() {
        return this._weight;
    }

    public void setWeight(String weight) {
        this._weight = weight;
    }

    public String getDob() {
        return this._dob;
    }

    public void setDob(String dob) {
        this._dob = dob;
    }

    public String getPicturePath() {
        return _picturePath;
    }

    public void setPicturePath(String picturePath) {
        this._picturePath = picturePath;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + _id + " - ");
        sb.append("NAME: " + _name + " - ");
        sb.append("SEX: " + _sex + " - ");
        sb.append("HEIGHT: " + _height + " - ");
        sb.append("WEIGHT: " + _weight + " - ");
        sb.append("DOB: " + _dob + " - ");
        sb.append("PICTURE PATH: " + _picturePath + " - ");
        sb.append("LATITUDE: " + _latitude + " - ");
        sb.append("LONGITUDE: " + _longitude + " - ");
        sb.append("CREATED_DATE: " + _createdDate + " - ");
        sb.append("LAST_MOD_DATE: " + _lastModDate);

        return sb.toString();
    }
}