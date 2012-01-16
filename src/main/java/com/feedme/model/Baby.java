package com.feedme.model;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Baby {

    //private variables
    int _id;
    String _name;
    String _sex;
    String _height;
    String _weight;
    String _dob;

    // Empty constructor
    public Baby(){

    }

    // constructor
    public Baby(int id, String name, String sex, String height, String weight, String dob){
        this._id = id;
        this._name = name;
        this._sex = sex;
        this._height = height;
        this._weight = weight;
        this._dob = dob;
   }

    // constructor
    public Baby(String name, String sex, String height, String weight, String dob){
        this._name = name;
        this._sex = sex;
        this._height = height;
        this._weight = weight;
        this._dob = dob;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting sex
    public String getSex(){
        return this._sex;
    }

    // setting sex
    public void setSex(String sex){
        this._sex = sex;
    }

    // getting height
    public String getHeight(){
        return this._height;
    }

    // setting height
    public void setHeight(String height){
        this._height = height;
    }

    // getting weight
    public String getWeight(){
        return this._weight;
    }

    // setting weight
    public void setWeight(String weight){
        this._weight = weight;
    }

    // getting dob
    public String getDob(){
        return this._dob;
    }

    // setting dob
    public void setDob(String dob){
        this._dob = dob;
    }

}