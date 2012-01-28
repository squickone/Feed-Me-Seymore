package com.feedme.model;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:22 PM
 */

public class Settings
{
    //private variables
    int _id;
    String _liquid;
    String _length;
    String _weight;
    String _temperature;
    String _sound;
    String _vibrate;

    // Empty constructor
    public Settings()
    {

    }

    // constructor
    public Settings(int id, String liquid, String length, String weight, String temperature, String sound,
                    String vibrate)
    {
        this._id = id;
        this._liquid = liquid;
        this._length = length;
        this._weight = weight;
        this._temperature = temperature;
        this._sound = sound;
        this._vibrate = vibrate;
    }

    // constructor
    public Settings(String liquid, String length, String weight, String temperature, String sound, String vibrate)
    {
        this._liquid = liquid;
        this._length = length;
        this._weight = weight;
        this._temperature = temperature;
        this._sound = sound;
        this._vibrate = vibrate;
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

    // getting liquid
    public String getLiquid()
    {
        return this._liquid;
    }

    // setting liquid
    public void setLiquid(String liquid)
    {
        this._liquid = liquid;
    }

    // getting length
    public String getLength()
    {
        return this._length;
    }

    // setting length
    public void setLength(String length)
    {
        this._length = length;
    }

    // getting weight
    public String getSettingsWeight()
    {
        return this._weight;
    }

    // setting weight
    public void setSettingsWeight(String weight)
    {
        this._weight = weight;
    }

    // getting temperature
    public String getTemperature()
    {
        return this._temperature;
    }

    // setting temperature
    public void setTemperature(String temperature)
    {
        this._temperature = temperature;
    }

    // getting sound
    public String getSound()
    {
        return _sound;
    }

    // setting sound
    public void setSound(String sound)
    {
        this._sound = sound;
    }

    // getting vibrate
    public String getVibrate()
    {
        return _vibrate;
    }

    // setting vibrate
    public void Vibrate(String vibrate)
    {
        this._vibrate = vibrate;
    }
}