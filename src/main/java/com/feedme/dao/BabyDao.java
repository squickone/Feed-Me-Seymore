package com.feedme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedme.Baby;
import com.feedme.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This DAO class handles all CRUD operations on the Babies table in the SQLLite database.
 */
public class BabyDao {

    //Table
    private static final String TABLE_DATA = "babies";

    //Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_DOB = "dob";

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public BabyDao(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = databaseHandler.getWritableDatabase();
    }

    public void close() {
        databaseHandler.close();
    }

    /**
     * Adds a new Baby to the Database.
     *
     * @param baby - Baby POJO
     */
    void addBaby(Baby baby) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

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

    /**
     * Getting single baby from the database by its ID.
     *
     * @param id - The ID of the Baby in the database.
     *
     * @return - Baby POJO representation of a specific Baby in the database.
     */
    Baby getBaby(int id) {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();

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

    /**
     * Get All Babies from the Database
     *
     * @return - Collection of all Babies located in the database.
     */
    public List<Baby> getAllBabies() {
        List<Baby> babyList = new ArrayList<Baby>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
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

    /**
     * Updates an existing Baby in the database.
     *
     * @return
     */
    public int updateBaby(Baby baby) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

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

    /**
     * Deletes a Baby from the Database
     */
    public void deleteBaby(Baby baby) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[] { String.valueOf(baby.getID()) });
     }

    /**
     * Returns the total number of Babies that exist in the Database.
     *
     * @return - Total number of babies in the Babies table.
     */
    public int getBabyCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    /**
     * Converts a Cursor from the Babies Table to a Baby Object.
     *
     * @param cursor - Cursor object that contains the Baby data from the Database
     *
     * @return - Baby
     */
    private Baby cursorToBaby(Cursor cursor) {
        Baby baby = new Baby();
        baby.setID(Integer.parseInt(cursor.getString(0)));
        baby.setName(cursor.getString(1));
        baby.setSex(cursor.getString(2));

        return baby;
    }
}
