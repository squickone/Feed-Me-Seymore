package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.SettingsDao;
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
  
        final TextView settingsLiquid = (TextView) findViewById(R.id.settingsLiquid);
        final TextView settingsLength = (TextView) findViewById(R.id.settingsLength);
        final TextView settingsWeight = (TextView) findViewById(R.id.settingsWeight);
        final TextView settingsTemperature = (TextView) findViewById(R.id.settingsTemperature);
        final TextView settingsSound = (TextView) findViewById(R.id.settingsSound);
        final TextView settingsVibrate = (TextView) findViewById(R.id.settingsVibrate);
        Settings setting = null;

        try {
            setting = settingsDao.getSetting(1);
            Log.d("Didn't add ", " Setting" + setting.getLiquid());
            settingsLiquid.setText(setting.getLiquid());
            settingsLength.setText(setting.getLength());
            settingsWeight.setText(setting.getSettingsWeight());
            settingsTemperature.setText(setting.getTemperature());
            settingsSound.setText(setting.getSound());
            settingsVibrate.setText(setting.getVibrate());
        } catch (CursorIndexOutOfBoundsException ex) {
            settingsDao.addSettings(new Settings("oz", "in", "lbs", "F", "off", "off"));
            setting = settingsDao.getSetting(1);
            settingsLiquid.setText(setting.getLiquid());
            settingsLength.setText(setting.getLength());
            settingsWeight.setText(setting.getSettingsWeight());
            settingsTemperature.setText(setting.getTemperature());
            settingsSound.setText(setting.getSound());
            settingsVibrate.setText(setting.getVibrate());
            Log.d("Added", " Setting");
        }

         // button listener for edit settings button
         Button saveSettingsButton = (Button)findViewById(R.id.saveSettingsButton);
         saveSettingsButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                  /**
                 * CRUD Operations
                 * */
                // Inserting baby
                Log.d("Insert: ", "Inserting ..");
                settingsDao.updateSettings(new Settings(settingsLiquid.getText().toString(), settingsLength.getText().toString(),
                        settingsWeight.getText().toString(), settingsTemperature.getText().toString(), settingsSound.getText().toString(), settingsVibrate.getText().toString()), 1);
                Log.d("Insert: ", "Inserted ..");

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