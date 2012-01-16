package com.feedme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedme.model.Journal;

import java.util.ArrayList;
import java.util.List;

/**
 * This DAO class handles all CRUD operations on the Entries table in the SQLLite database.
 */
public class JournalDao {

    // Contacts table name
    private static final String TABLE_DATA = "journal";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_MIN_LEFT = "minutes_left";
    private static final String KEY_MIN_RIGHT = "minutes_right";
    private static final String KEY_OUNCES = "ounces";

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public JournalDao(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = databaseHandler.getWritableDatabase();
    }

    public void close() {
        databaseHandler.close();
    }

    /**
     * Adds a new Entry to the Database.
     *
     * @param entry - Journal POJO
     */
    public void addEntry(Journal entry) {

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, entry.getDate()); // Baby name
        values.put(KEY_TIME, entry.getTime()); // Baby sex
        values.put(KEY_MIN_LEFT, entry.getMinLeft()); // Baby height
        values.put(KEY_MIN_RIGHT, entry.getMinRight()); // Baby weight
        values.put(KEY_OUNCES, entry.getOunces()); // Baby dob

        // Inserting Row
        database.insert(TABLE_DATA, null, values);
    }

    /**
     * Getting single entry from the database by its ID.
     *
     * @param id - The ID of the Entry in the database.
     *
     * @return - Journal POJO representation of a specific Entry in the database.
     */
    Journal getEntry(int id) {

        Cursor cursor = database.query(TABLE_DATA, new String[]{KEY_ID,
                KEY_DATE, KEY_TIME, KEY_MIN_LEFT, KEY_MIN_RIGHT, KEY_OUNCES}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        // return baby
        return cursorToJournal(cursor);
    }

    /**
     * Get All Entries from the Database
     *
     * @return - Collection of all Entries located in the database.
     */
    public List<Journal> getAllEntries() {
        List<Journal> entryList = new ArrayList<Journal>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                entryList.add(cursorToJournal(cursor));
            } while (cursor.moveToNext());
        }

        return entryList;
    }

    /**
     * Returns a String[] of all entries by date.
     *
     * @return
     */
    public String[] getAllEntriesAsStringArray(){
        List<Journal> entries = getAllEntries();
        
        String[] entriesAsStrings = new String[entries.size()];
        for(int i=0 ; i<entries.size() ; i++){
            Journal entry = entries.get(i);
            entriesAsStrings[i] = entry.getDate();
        }

        return entriesAsStrings;
    }

    /**
     * Updates an existing Entry in the database.
     *
     * @return
     */
    public int updateEntry(Journal entry) {

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, entry.getDate());
        values.put(KEY_TIME, entry.getTime());
        values.put(KEY_MIN_LEFT, entry.getMinLeft());
        values.put(KEY_MIN_RIGHT, entry.getMinRight());
        values.put(KEY_OUNCES, entry.getOunces());

        // updating row
        return database.update(TABLE_DATA, values, KEY_ID + " = ?", new String[] { String.valueOf(entry.getID()) });
    }

    /**
     * Deletes an entry from the Database
     */
    public void deleteEntry(Journal entry) {
        database.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[]{String.valueOf(entry.getID())});
    }

    /**
     * Returns the total number of entries that exist in the Database.
     *
     * @return - Total number of entries in the entries table.
     */
    public int getEntriesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    /**
     * Converts a Cursor from the Entries Table to a Journal Object.
     *
     * @param cursor - Cursor object that contains the Entry data from the Database
     *
     * @return - Journal
     */
    private Journal cursorToJournal(Cursor cursor) {
        return new Journal(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
    }
}
