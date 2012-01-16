package com.feedme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        final DatabaseHandler db = new DatabaseHandler(this);

        // button listener for add child button

        final EditText babyName = (EditText) findViewById(R.id.babyName);
        final EditText babySex = (EditText) findViewById(R.id.babySex);
        final EditText babyHeight = (EditText) findViewById(R.id.babyHeight);
        final EditText babyWeight = (EditText) findViewById(R.id.babyWeight);
        final EditText babyDob = (EditText) findViewById(R.id.babyDob);
        Button addChildButton = (Button)findViewById(R.id.addChildButton);

        addChildButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /**
                 * CRUD Operations
                 * */
                // Inserting baby
                Log.d("Insert: ", "Inserting ..");
                db.addBaby(new Baby(babyName.getText().toString(), babySex.getText().toString(), babyHeight.getText().toString(), babyWeight.getText().toString(), babyDob.getText().toString()));

                // Reading all babies
                Log.d("Reading: ", "Reading all babies..");
                List<Baby> contacts = db.getAllBabies();

                for (Baby cn : contacts) {
                    String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Sex: " + cn.getSex() + " ,Height: " + cn.getHeight() + " ,Weight: " + cn.getWeight() + " ,DOB: " + cn.getDob();
                    // Writing babies to log
                    Log.d("Name: ", log);
                }
                
                babyName.setText("");
                babySex.setText("");
                babyHeight.setText("");
                babyWeight.setText("");
                babyDob.setText("");

             }
        });


    }
}