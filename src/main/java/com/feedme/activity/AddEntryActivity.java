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
import com.feedme.model.Journal;
import com.feedme.R;
import com.feedme.dao.JournalDao;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddEntryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_entry);

        //Add Bottle Feed Entry Button
        Button addBottleFeedButton = (Button) findViewById(R.id.addBottleFeedButton);
        addBottleFeedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AddEntryActivity.this,
                        AddBottleFeedActivity.class));
            }
        });

        //Add Breastfeed Entry Button
        Button addBreastFeedButton = (Button) findViewById(R.id.addBreastFeedButton);
        addBreastFeedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AddEntryActivity.this,
                        AddBreastFeedActivity.class));
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
                startActivity(new Intent(AddEntryActivity.this,
                        FamilyHomeActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(AddEntryActivity.this,
                        HomeActivity.class));
                break;
        }
        return true;
    }
}