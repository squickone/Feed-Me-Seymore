package com.feedme.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:24 PM
 */
public class SettingsHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "settingsData";

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

    public SettingsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LIQUID + " TEXT,"
                + KEY_LENGTH + " TEXT," + KEY_SETTINGS_WEIGHT + " TEXT," + KEY_TEMPERATURE + " TEXT," + KEY_SOUND + " TEXT," + KEY_VIBRATE + " TEXT)";
        db.execSQL(CREATE_DATA_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        // Create tables again
        onCreate(db);
    }
}