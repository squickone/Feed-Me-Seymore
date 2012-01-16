package com.feedme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: 1/16/12
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dataManager";

    // Contacts table name
    private static final String TABLE_DATA = "data";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SEX + " TEXT" + ")";
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


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new baby
    void addBaby(Baby baby) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, baby.getName()); // Contact Name
        values.put(KEY_SEX, baby.getSex()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close(); // Closing database connection
    }

    // Getting single baby
    Baby getBaby(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID,
                KEY_NAME, KEY_SEX }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Baby baby = new Baby(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return baby
        return baby;
    }

    // Getting All Babies
    public List<Baby> getAllBabies() {
        List<Baby> babyList = new ArrayList<Baby>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Baby data = new Baby();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setSex(cursor.getString(2));
                // Adding contact to list
                babyList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return babyList;
    }

    // Updating single contact
    public int updateBaby(Baby baby) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, baby.getName());
        values.put(KEY_SEX, baby.getSex());

        // updating row
        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(baby.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Baby baby) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[] { String.valueOf(baby.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
