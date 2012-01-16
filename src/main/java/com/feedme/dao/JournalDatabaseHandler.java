package com.feedme.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User: root
 * Date: 1/16/12
 * Time: 1:24 PM
 */
public class JournalDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "journalData";

    // Contacts table name
    private static final String TABLE_DATA = "entries";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_MIN_LEFT = "min_left";
    private static final String KEY_MIN_RIGHT = "min_right";
    private static final String KEY_OUNCES = "ounces";
    private static final String KEY_CHILD_ID = "child_id";

    public JournalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_MIN_LEFT + " TEXT," + KEY_MIN_RIGHT + " TEXT," + KEY_OUNCES + " TEXT," + KEY_CHILD_ID + " INTEGER" + ")";
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