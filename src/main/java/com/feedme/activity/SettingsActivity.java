package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.SettingsDao;
import com.feedme.model.Settings;

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

        final Spinner settingsLiquid = (Spinner) findViewById(R.id.settingsLiquid);
        final Spinner settingsLength = (Spinner) findViewById(R.id.settingsLength);
        final Spinner settingsWeight = (Spinner) findViewById(R.id.settingsWeight);
        final Spinner settingsTemperature = (Spinner) findViewById(R.id.settingsTemperature);
        final Spinner settingsSound = (Spinner) findViewById(R.id.settingsSound);
        final Spinner settingsVibrate = (Spinner) findViewById(R.id.settingsVibrate);
        Settings setting;

        try {
            setting = settingsDao.getSetting(1);
            Log.d("Didn't add ", " Setting" + setting.getLiquid());

            //Liquid
            ArrayAdapter adapter = (ArrayAdapter) settingsLiquid.getAdapter();
            settingsLiquid.setSelection(getIndexFromElement(adapter, setting.getLiquid()));

            //Length
            adapter = (ArrayAdapter) settingsLength.getAdapter();
            settingsLength.setSelection(getIndexFromElement(adapter, setting.getLength()));

            //Weight
            adapter = (ArrayAdapter) settingsWeight.getAdapter();
            settingsWeight.setSelection(getIndexFromElement(adapter, setting.getSettingsWeight()));

            //Temperature
            adapter = (ArrayAdapter) settingsTemperature.getAdapter();
            settingsTemperature.setSelection(getIndexFromElement(adapter, setting.getTemperature()));

            //Sound
            adapter = (ArrayAdapter) settingsSound.getAdapter();
            settingsSound.setSelection(getIndexFromElement(adapter, setting.getSound()));

            //Vibrate
            adapter = (ArrayAdapter) settingsVibrate.getAdapter();
            settingsVibrate.setSelection(getIndexFromElement(adapter, setting.getVibrate()));

        } catch (CursorIndexOutOfBoundsException ex) {

            settingsDao.addSettings(new Settings("oz", "in", "lbs", "F", "Off", "Off"));
            setting = settingsDao.getSetting(1);

            //Liquid
            ArrayAdapter adapter = (ArrayAdapter) settingsLiquid.getAdapter();
            settingsLiquid.setSelection(getIndexFromElement(adapter, setting.getLiquid()));

            //Length
            adapter = (ArrayAdapter) settingsLength.getAdapter();
            settingsLength.setSelection(getIndexFromElement(adapter, setting.getLength()));

            //Weight
            adapter = (ArrayAdapter) settingsWeight.getAdapter();
            settingsWeight.setSelection(getIndexFromElement(adapter, setting.getSettingsWeight()));

            //Temperature
            adapter = (ArrayAdapter) settingsTemperature.getAdapter();
            settingsTemperature.setSelection(getIndexFromElement(adapter, setting.getTemperature()));

            //Sound
            adapter = (ArrayAdapter) settingsSound.getAdapter();
            settingsSound.setSelection(getIndexFromElement(adapter, setting.getSound()));

            //Vibrate
            adapter = (ArrayAdapter) settingsVibrate.getAdapter();
            settingsVibrate.setSelection(getIndexFromElement(adapter, setting.getVibrate()));

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
                settingsDao.updateSettings(new Settings(settingsLiquid.getSelectedItem().toString(),
                        settingsLength.getSelectedItem().toString(),
                        settingsWeight.getSelectedItem().toString(), settingsTemperature.getSelectedItem().toString(),
                        settingsSound.getSelectedItem().toString(), settingsVibrate.getSelectedItem().toString()), 1);

                Log.d("Insert: ", "Inserted ..");

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, ADD_SETTINGS_ACTIVITY_ID);
             }
         });
    }

    /**
     * Returns the position in a spinner based on the passed in element.
     *
     * @param adapter - ArrayAdapter
     * @param element - String
     * @return - int position of Spinner
     */
    private int getIndexFromElement(ArrayAdapter adapter, String element) {
        for(int i = 0; i < adapter.getCount(); i++) {
            if(adapter.getItem(i).equals(element)) {
                return i;
            }
        }

        return 0;
    }


}