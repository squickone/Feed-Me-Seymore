package com.feedme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedme.model.Settings;

/**
 * This DAO class handles all CRUD operations on the Babies table in the SQLLite database.
 */
public class SettingsDao {

    // Contacts table name
    private static final String TABLE_DATA = "settings";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LIQUID = "liquid";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_SETTINGS_WEIGHT = "weight";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_SOUND = "sound";
    private static final String KEY_VIBRATE = "vibrate";

    private SQLiteDatabase database;
    private SettingsHandler databaseHandler;

    public SettingsDao(Context context) {
        databaseHandler = new SettingsHandler(context);
    }

    public void open() throws SQLException {
        database = databaseHandler.getWritableDatabase();
    }

    public void close() {
        databaseHandler.close();
    }

    /**
     * Adds a new settings to the Database.
     *
     * @param settings - settings POJO
     */
    public void addSettings(Settings settings) {

        open();
        
        ContentValues values = new ContentValues();
        values.put(KEY_LIQUID, settings.getLiquid());
        values.put(KEY_LENGTH, settings.getLength());
        values.put(KEY_SETTINGS_WEIGHT, settings.getSettingsWeight());
        values.put(KEY_TEMPERATURE, settings.getTemperature());
        values.put(KEY_SOUND, settings.getSound());
        values.put(KEY_VIBRATE, settings.getVibrate());

        // Inserting Row
        database.insert(TABLE_DATA, null, values);
        
        close();
    }

    /**
     * Getting settings from the database by its ID.
     *
     * @param id - The ID of the settings in the database.
     *
     * @return - Settings POJO representation of a specific settings in the database.
     */
    public Settings getSetting(int id) {

        open();
        
        Cursor cursor = database.query(TABLE_DATA, new String[] { KEY_ID, KEY_LIQUID,
                KEY_LENGTH, KEY_SETTINGS_WEIGHT, KEY_TEMPERATURE, KEY_SOUND, KEY_VIBRATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Settings setting =  cursorToSetting(cursor);

        close();

        return setting;
    }



    /**
     * Updates an existing settings in the database.
     *
     * @return
     */
    public int updateSettings(Settings setting, int id) {

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_LIQUID, setting.getLiquid());
        values.put(KEY_LENGTH, setting.getLength());
        values.put(KEY_SETTINGS_WEIGHT, setting.getSettingsWeight());
        values.put(KEY_TEMPERATURE, setting.getTemperature());
        values.put(KEY_SOUND, setting.getSound());
        values.put(KEY_VIBRATE, setting.getVibrate());

        // updating row
        int result = database.update(TABLE_DATA, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });

        close();

        return result;
    }

    /**
     * Deletes a Baby from the Database
     */
    public void deleteSettings(Settings setting) {
        open();
        database.delete(TABLE_DATA, KEY_ID + " = ?", new String[]{String.valueOf(setting.getId())});
        close();
    }


    /**
     * Converts a Cursor from the Settings Table to a Settings Object.
     *
     * @param cursor - Cursor object that contains the Settings data from the Database
     *
     * @return - Settings
     */
    private Settings cursorToSetting(Cursor cursor) {
        return new Settings(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
    }
}
