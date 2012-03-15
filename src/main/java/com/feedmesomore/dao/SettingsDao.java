package com.feedmesomore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedmesomore.database.SettingsColumn;
import com.feedmesomore.model.Settings;
import com.feedmesomore.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This DAO class handles all CRUD operations on the Settings table in the SQLLite database.
 */
public class SettingsDao {

    // Contacts table name
    private static final String TABLE_DATA = SettingsColumn.TABLE_NAME;

    // Contacts Table Columns names
    private static final String KEY_ID = SettingsColumn.ID.columnName();
    private static final String KEY_LIQUID = SettingsColumn.LIQUID.columnName();
    private static final String KEY_LENGTH = SettingsColumn.LENGTH.columnName();
    private static final String KEY_SETTINGS_WEIGHT = SettingsColumn.WEIGHT.columnName();
    private static final String KEY_TEMPERATURE = SettingsColumn.TEMPERATURE.columnName();
    private static final String KEY_SOUND = SettingsColumn.SOUND.columnName();
    private static final String KEY_VIBRATE = SettingsColumn.VIBRATE.columnName();
    private static final String KEY_CREATED_DATE = SettingsColumn.CREATED_DATE.columnName();
    private static final String KEY_LAST_MOD_DATE = SettingsColumn.LAST_MOD_DATE.columnName();

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public SettingsDao(Context context) {
        databaseHandler = new DatabaseHandler(context);
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

        SimpleDateFormat iso8601 = new SimpleDateFormat(DateUtil.ISO8601_FORMAT);
        Calendar now = Calendar.getInstance();

        open();
        
        ContentValues values = new ContentValues();
        values.put(KEY_LIQUID, settings.getLiquid());
        values.put(KEY_LENGTH, settings.getLength());
        values.put(KEY_SETTINGS_WEIGHT, settings.getSettingsWeight());
        values.put(KEY_TEMPERATURE, settings.getTemperature());
        values.put(KEY_SOUND, settings.getSound());
        values.put(KEY_VIBRATE, settings.getVibrate());
        values.put(KEY_CREATED_DATE, iso8601.format(now.getTime()));
        values.put(KEY_LAST_MOD_DATE, iso8601.format(now.getTime()));

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
        
        Cursor cursor = database.query(TABLE_DATA, SettingsColumn.getColumnNames(), KEY_ID + "=?",
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

        SimpleDateFormat iso8601 = new SimpleDateFormat(DateUtil.ISO8601_FORMAT);
        Calendar now = Calendar.getInstance();

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_LIQUID, setting.getLiquid());
        values.put(KEY_LENGTH, setting.getLength());
        values.put(KEY_SETTINGS_WEIGHT, setting.getSettingsWeight());
        values.put(KEY_TEMPERATURE, setting.getTemperature());
        values.put(KEY_SOUND, setting.getSound());
        values.put(KEY_VIBRATE, setting.getVibrate());
        values.put(KEY_LAST_MOD_DATE, iso8601.format(now.getTime()));

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
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8));
    }
}
