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
import com.feedme.dao.SettingsDao;
import com.feedme.model.Baby;
import com.feedme.model.Settings;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class SettingsActivity extends Activity {
    
    private static final int ADD_SETTINGS_ACTIVITY_ID = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_settings);
        final SettingsDao settingsDao = new SettingsDao(getApplicationContext());

        // button listener for edit settings button
        final EditText settingsLiquid = (EditText) findViewById(R.id.settingsLiquid);
        final EditText settingsLength = (EditText) findViewById(R.id.settingsLength);
        final EditText settingsWeight = (EditText) findViewById(R.id.settingsWeight);
        final EditText settingsTemperature = (EditText) findViewById(R.id.settingsTemperature);
        final EditText settingsSound = (EditText) findViewById(R.id.settingsSound);
        final EditText settingsVibrate = (EditText) findViewById(R.id.settingsVibrate);
        Button saveSettingsButton = (Button)findViewById(R.id.saveSettingsButton);

        //Add Child Button
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                  /**
                 * CRUD Operations
                 * */
                // Inserting baby
                Log.d("Insert: ", "Inserting ..");
                settingsDao.addSettings(new Settings(settingsLiquid.getText().toString(), settingsLength.getText().toString(),
                        settingsWeight.getText().toString(), settingsTemperature.getText().toString(), settingsSound.getText().toString(), settingsVibrate.getText().toString()));

                settingsLiquid.setText("");
                settingsLength.setText("");
                settingsWeight.setText("");
                settingsTemperature.setText("");
                settingsSound.setText("");
                settingsVibrate.setText("");

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, ADD_SETTINGS_ACTIVITY_ID);
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
                startActivity(new Intent(SettingsActivity.this,
                        FamilyHomeActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(SettingsActivity.this,
                        HomeActivity.class));
                break;
        }
        return true;
    }
}