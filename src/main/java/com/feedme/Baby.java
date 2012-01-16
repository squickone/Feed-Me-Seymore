package com.feedme;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class Baby {

    //private variables
    int _id;
    String _name;
    String _sex;

    // Empty constructor
    public Baby(){

    }
    // constructor
    public Baby(int id, String name, String _sex){
        this._id = id;
        this._name = name;
        this._sex = _sex;
    }

    // constructor
    public Baby(String name, String _sex){
        this._name = name;
        this._sex = _sex;
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
}