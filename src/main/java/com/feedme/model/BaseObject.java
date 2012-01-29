package com.feedme.model;

import java.io.Serializable;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 9:17 AM
 */
public abstract class BaseObject implements Serializable {

    int _id;
    String _createdDate;
    String _lastModDate;
    
    public int getId(){
        return _id;
    }
    
    public void setId(int id){
        this._id = id;
    }

    public String getCreatedDate() {
        return _createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this._createdDate = createdDate;
    }

    public String getLastModDate() {
        return _lastModDate;
    }

    public void setLastModDate(String lastModDate) {
        this._lastModDate = lastModDate;
    }
}