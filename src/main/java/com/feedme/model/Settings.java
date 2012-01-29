package com.feedme.model;

import java.io.Serializable;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Settings extends BaseObject {

    //private variables
    String _liquid;
    String _length;
    String _weight;
    String _temperature;
    String _sound;
    String _vibrate;

    // Empty constructor
    public Settings() {

    }

    // constructor
    public Settings(int id, String liquid, String length, String weight, String temperature, String sound,
                    String vibrate) {
        this._id = id;
        this._liquid = liquid;
        this._length = length;
        this._weight = weight;
        this._temperature = temperature;
        this._sound = sound;
        this._vibrate = vibrate;
    }

    // constructor
    public Settings(String liquid, String length, String weight, String temperature, String sound, String vibrate) {
        this._liquid = liquid;
        this._length = length;
        this._weight = weight;
        this._temperature = temperature;
        this._sound = sound;
        this._vibrate = vibrate;
    }

    public String getLiquid() {
        return this._liquid;
    }

    public void setLiquid(String liquid) {
        this._liquid = liquid;
    }

    public String getLength() {
        return this._length;
    }

    public void setLength(String length) {
        this._length = length;
    }

    public String getSettingsWeight() {
        return this._weight;
    }

    public void setSettingsWeight(String weight) {
        this._weight = weight;
    }

    public String getTemperature() {
        return this._temperature;
    }

    public void setTemperature(String temperature) {
        this._temperature = temperature;
    }

    public String getSound() {
        return _sound;
    }

    public void setSound(String sound) {
        this._sound = sound;
    }

    public String getVibrate() {
        return _vibrate;
    }

    public void Vibrate(String vibrate) {
        this._vibrate = vibrate;
    }
}