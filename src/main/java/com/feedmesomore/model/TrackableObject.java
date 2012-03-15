package com.feedmesomore.model;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 9:33 AM
 */
public abstract class TrackableObject extends BaseObject {

    String _latitude;
    String _longitude;

    public String getLatitude() {
        return _latitude;
    }

    public void setLatitude(String latitude) {
        this._latitude = latitude;
    }

    public String getLongitude() {
        return _longitude;
    }

    public void setLongitude(String longitude) {
        this._longitude = longitude;
    }
}
