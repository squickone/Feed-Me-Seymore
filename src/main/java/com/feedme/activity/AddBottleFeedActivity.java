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
import com.feedme.dao.JournalDao;
import com.feedme.model.Journal;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddBottleFeedActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_bottle_feed_entry);
        final JournalDao journalDao = new JournalDao(getApplicationContext());

        // button listener for add child button
        final EditText entryDate = (EditText) findViewById(R.id.entryDate);
        final EditText entryTime = (EditText) findViewById(R.id.entryTime);
        final EditText entryMinLeft = (EditText) findViewById(R.id.entryMinLeft);
        final EditText entryMinRight = (EditText) findViewById(R.id.entryMinRight);
        final EditText entryOunces = (EditText) findViewById(R.id.entryOunces);
        final EditText entryChild = (EditText) findViewById(R.id.entryChild);
        Button addEntryButton = (Button)findViewById(R.id.addEntryButton);

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /**
                 * CRUD Operations
                 * */
                // Inserting entry
                Log.d("Insert: ", "Inserting ..");
                journalDao.addEntry(new Journal(entryDate.getText().toString(), entryTime.getText().toString(), " ", " ", entryOunces.getText().toString(), Integer.parseInt(entryChild.getText().toString())));

                // Reading all entries
                Log.d("Reading: ", "Reading all entries..");
                List<Journal> entries = journalDao.getAllEntries();

                for (Journal entry : entries) {
                    String log = "Id: "+entry.getID()+" ,Date: " + entry.getDate() + " ,Time: " + entry.getTime() + " ,Ounces: " + entry.getOunces() + " ,Child: " + entry.getChild();
                    // Writing entries to log
                    Log.d("Name: ", log);
                }

                entryDate.setText("");
                entryTime.setText("");
                entryOunces.setText("");
                entryChild.setText("");
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
                startActivity(new Intent(AddBottleFeedActivity.this,
                        FamilyHomeActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(AddBottleFeedActivity.this,
                        HomeActivity.class));
                break;
        }
        return true;
    }
}