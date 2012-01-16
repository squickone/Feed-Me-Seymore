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
    private static final String DATABASE_NAME = "babyData";

    // Contacts table name
    private static final String TABLE_DATA = "babies";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_DOB = "dob";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SEX + " TEXT," + KEY_HEIGHT + " TEXT," + KEY_WEIGHT + " TEXT," + KEY_DOB + " TEXT" + ")";
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
        values.put(KEY_NAME, baby.getName()); // Baby name
        values.put(KEY_SEX, baby.getSex()); // Baby sex
        values.put(KEY_HEIGHT, baby.getHeight()); // Baby height
        values.put(KEY_WEIGHT, baby.getWeight()); // Baby weight
        values.put(KEY_DOB, baby.getDob()); // Baby dob

        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close(); // Closing database connection
    }

    // Getting single baby
    Baby getBaby(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID,
                KEY_NAME, KEY_SEX, KEY_HEIGHT, KEY_WEIGHT, KEY_DOB }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Baby baby = new Baby(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
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
                data.setHeight(cursor.getString(3));
                data.setWeight(cursor.getString(4));
                data.setDob(cursor.getString(5));
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
        values.put(KEY_HEIGHT, baby.getHeight());
        values.put(KEY_WEIGHT, baby.getWeight());
        values.put(KEY_DOB, baby.getDob());

        // updating row
        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(baby.getID()) });
    }

    // Deleting single baby
    public void deleteBaby(Baby baby) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[] { String.valueOf(baby.getID()) });
        db.close();
    }

    // Getting baby count
    public int getBabyCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
