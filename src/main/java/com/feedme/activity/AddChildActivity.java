package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.feedme.R;
import com.feedme.dao.BabyDao;
import com.feedme.model.Baby;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddChildActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_child);
        final BabyDao babyDao = new BabyDao(getApplicationContext());

        // button listener for add child button
        final EditText babyName = (EditText) findViewById(R.id.babyName);
        final EditText babySex = (EditText) findViewById(R.id.babySex);
        final EditText babyHeight = (EditText) findViewById(R.id.babyHeight);
        final EditText babyWeight = (EditText) findViewById(R.id.babyWeight);
        final EditText babyDob = (EditText) findViewById(R.id.babyDob);
        Button addChildButton = (Button)findViewById(R.id.addChildButton);

        //Take Picture Button
        Button takePicture = (Button) findViewById(R.id.takePicture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TakePictureActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Select Picture Button
        Button selectPicture = (Button) findViewById(R.id.pickPicture);
        selectPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectPictureActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        addChildButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /**
                 * CRUD Operations
                 * */
                // Inserting baby
                Log.d("Insert: ", "Inserting ..");
                babyDao.addBaby(new Baby(babyName.getText().toString(), babySex.getText().toString(), babyHeight.getText().toString(), babyWeight.getText().toString(), babyDob.getText().toString()));

                // Reading all babies
                Log.d("Reading: ", "Reading all babies..");
                List<Baby> babies = babyDao.getAllBabies();

                for (Baby baby : babies) {
                    String log = "Id: "+baby.getID()+" ,Name: " + baby.getName() + " ,Sex: " + baby.getSex() + " ,Height: " + baby.getHeight() + " ,Weight: " + baby.getWeight() + " ,DOB: " + baby.getDob();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.family:
                startActivity(new Intent(AddChildActivity.this,
                        FamilyHomeActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(AddChildActivity.this,
                        HomeActivity.class));
                break;
        }
        return true;
    }
}