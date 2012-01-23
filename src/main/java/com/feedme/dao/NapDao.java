package com.feedme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedme.model.Nap;

import java.util.ArrayList;
import java.util.List;

/**
 * This DAO class handles all CRUD operations on the Entries table in the SQLLite database.
 */
public class NapDao {

    // Contacts table name
    private static final String TABLE_DATA = "naps";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CHILD_ID = "child_id";

    private SQLiteDatabase database;
    private NapDatabaseHandler databaseHandler;

    public NapDao(Context context) {
        databaseHandler = new NapDatabaseHandler(context);
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
     * @param nap - Nap POJO
     */
    public void addNap(Nap nap) {

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, nap.getDate()); // Date
        values.put(KEY_START_TIME, nap.getStartTime()); // Time
        values.put(KEY_END_TIME, nap.getEndTime()); // Time
        values.put(KEY_LOCATION, nap.getLocation()); // Location
        values.put(KEY_CHILD_ID, nap.getChild()); // Child ID

        // Inserting Row
        database.insert(TABLE_DATA, null, values);

        close();
    }

    /**
     * Getting single entry from the database by its ID.
     *
     * @param id - The ID of the Entry in the database.
     *
     * @return - Journal POJO representation of a specific Entry in the database.
     */
    public Nap getEntry(int id) {

        open();

        Cursor cursor = database.query(TABLE_DATA, new String[]{KEY_ID,
                KEY_DATE, KEY_START_TIME, KEY_END_TIME, KEY_LOCATION, KEY_CHILD_ID}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Nap nap =  cursorToNap(cursor);

        close();

        // return nap
        return nap;
    }


    /**
     * Getting single nap from the database by its date.
     *
     * @param date - The date of the nap in the database.
     *
     * @return - Nap POJO representation of a specific nap in the database.
     */
    public Nap getNapByDate(String date) {

        open();

        Cursor cursor = database.query(TABLE_DATA, new String[] { KEY_ID,
                KEY_DATE, KEY_START_TIME, KEY_END_TIME, KEY_LOCATION, KEY_CHILD_ID }, KEY_DATE + "=?",
                new String[] { String.valueOf(date) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Nap nap =  cursorToNap(cursor);

        close();

        return nap;
    }

    /**
     * Get All Naps from the Database
     *
     * @return - Collection of all Entries located in the database.
     */
    public List<Nap> getAllNaps() {

        open();

        List<Nap> napList = new ArrayList<Nap>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                napList.add(cursorToNap(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return napList;
    }

    /**
     * Returns a Nap[] of all naps.
     *
     * @return
     */
    public Nap[] getAllNapsAsArray(){

        List<Nap> naps = getAllNaps();

        Nap[] napArray = new Nap[naps.size()];
        for(int i=0 ; i<naps.size() ; i++){
            Nap entry = naps.get(i);
            napArray[i] = entry;
        }

        return napArray;
    }

    /**
     * Returns the last X naps (where X=limit) based on the passed in childId in the form of a List<Nap>.
     *
     * @param childId
     * @param limit
     * @return
     */
    public List<Nap> getLastNapsByChild(int childId, int limit){

        open();

        List<Nap> napList = new ArrayList<Nap>();

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID + "=" + childId + " ORDER BY " + KEY_ID + " DESC LIMIT " + limit;
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do{
                napList.add(cursorToNap(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return napList;
    }

    /**
     * Returns the last X naps (where X=limit) based on the passed in childId in the form of a Nap[].
     *
     * @param childId
     * @param limit
     * @return
     */
    public Nap[] getLastNapsByChildAsArray(int childId, int limit){

        List<Nap> naps = getLastNapsByChild(childId, limit);

        Nap[] napArray = new Nap[naps.size()];
        for(int i=0 ; i<naps.size() ; i++){
            Nap nap = naps.get(i);
            napArray[i] = nap;
        }

        return napArray;
    }

    /**
     * Updates an existing nap in the database.
     *
     * @return
     */
    public int updateNap(Nap entry, int id) {

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, entry.getDate());
        values.put(KEY_START_TIME, entry.getStartTime());
        values.put(KEY_END_TIME, entry.getEndTime());
        values.put(KEY_LOCATION, entry.getLocation());
        values.put(KEY_CHILD_ID, entry.getChild());

        // updating row
        int result = database.update(TABLE_DATA, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });

        close();

        return result;
    }

    /**
     * Deletes naps for babyId from db
     */
    public void deleteNap(int babyId) {
        open();
        database.delete(TABLE_DATA, KEY_CHILD_ID + " = ?", new String[]{String.valueOf(babyId)});
        close();
    }

    /**
     * Deletes nap with ID from db
     */
    public void deleteNapByID(int ID) {
        open();
        database.delete(TABLE_DATA, KEY_ID + " = ?", new String[]{String.valueOf(ID)});
        close();
    }



    /**
     * Returns the total number of naps that exist in the Database.
     *
     * @return - Total number of naps in the naps table.
     */
    public int getNapsCount() {
        open();
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();

        // return count
        int count = cursor.getCount();

        close();

        return count;
    }

    /**
     * Returns the total number of naps for a baby on a given date.
     *
     * @param babyId
     * @param date
     * @return
     */
    public int getNapsCountByBabyAndDate(String babyId, String date){
        open();

        String countQuery = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID  + " = " + babyId + " AND "
                + KEY_DATE + " = '" + date + "'";

        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        close();

        return count;
    }

    /**
     * Converts a Cursor from the naps Table to a nap Object.
     *
     * @param cursor - Cursor object that contains the nap data from the Database
     *
     * @return - Nap
     */
    private Nap cursorToNap(Cursor cursor) {
        return new Nap(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)));
    }
}
