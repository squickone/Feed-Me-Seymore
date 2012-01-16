package com.feedme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddChild extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_child);


        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting baby
        Log.d("Insert: ", "Inserting ..");
        db.addBaby(new Baby("Roger", "male"));


        // Reading all babies
        Log.d("Reading: ", "Reading all babies..");
        List<Baby> contacts = db.getAllBabies();

        for (Baby cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Sex: " + cn.getSex();
            // Writing babies to log
            Log.d("Name: ", log);
        }
    }
}