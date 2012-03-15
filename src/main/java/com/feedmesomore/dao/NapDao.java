package com.feedmesomore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedmesomore.database.NapColumn;
import com.feedmesomore.model.Nap;
import com.feedmesomore.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This DAO class handles all CRUD operations on the Nap table in the SQLLite database.
 */
public class NapDao {

    // Contacts table name
    private static final String TABLE_DATA = NapColumn.TABLE_NAME;

    // Contacts Table Columns names
    private static final String KEY_ID = NapColumn.ID.columnName();
    private static final String KEY_DATE = NapColumn.DATE.columnName();
    private static final String KEY_START_TIME = NapColumn.START_TIME.columnName();
    private static final String KEY_END_TIME = NapColumn.END_TIME.columnName();
    private static final String KEY_DATE_TIME = NapColumn.DATE_TIME.columnName();
    private static final String KEY_LOCATION = NapColumn.LOCATION.columnName();
    private static final String KEY_CHILD_ID = NapColumn.CHILD_ID.columnName();
    private static final String KEY_LATITUDE = NapColumn.LATITUDE.columnName();
    private static final String KEY_LONGITUDE = NapColumn.LONGITUDE.columnName();
    private static final String KEY_CREATED_DATE = NapColumn.CREATED_DATE.columnName();
    private static final String KEY_LAST_MOD_DATE = NapColumn.LAST_MOD_DATE.columnName();

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public NapDao(Context context) {
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
     * @param nap - Nap POJO
     */
    public void addNap(Nap nap) throws ParseException {

        Calendar now = Calendar.getInstance();

        //NOTE: DateTime is a combination of Date and StartTime converted into ISO8601 format. Used for date sorting.
        SimpleDateFormat androidTime = new SimpleDateFormat(DateUtil.ANDROID_TIME_FORMAT);
        SimpleDateFormat iso8601 = new SimpleDateFormat(DateUtil.ISO8601_FORMAT);
        Date dateTime = androidTime.parse(nap.getDate() + ":" + nap.getStartTime());

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, UUID.randomUUID().toString());
        values.put(KEY_DATE, nap.getDate()); // Date
        values.put(KEY_START_TIME, nap.getStartTime()); // Time
        values.put(KEY_END_TIME, nap.getEndTime()); // Time
        values.put(KEY_DATE_TIME, iso8601.format(dateTime)); //DateTime
        values.put(KEY_LOCATION, nap.getLocation()); // Location
        values.put(KEY_CHILD_ID, nap.getChild()); // Child ID
        values.put(KEY_LATITUDE, nap.getLatitude());
        values.put(KEY_LONGITUDE, nap.getLongitude());
        values.put(KEY_CREATED_DATE, iso8601.format(now.getTime()));
        values.put(KEY_LAST_MOD_DATE, iso8601.format(now.getTime()));

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
    public Nap getEntry(String id) {

        open();

        Cursor cursor = database.query(TABLE_DATA, NapColumn.getColumnNames(), KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
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

        Cursor cursor = database.query(TABLE_DATA, NapColumn.getColumnNames(), KEY_DATE + "=?",
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
    public List<Nap> getLastNapsByChild(String childId, int limit){

        open();

        List<Nap> napList = new ArrayList<Nap>();

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID + "='" + childId + "' ORDER BY "
                + KEY_DATE_TIME  + " DESC LIMIT " + limit;
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
     * Returns the last X naps (where X=limit) based on the passed in childId in the form of a List<Nap>.
     *
     * @param childId
     * @return
     */
    public List<Nap> getAllNapsByChild(String childId){

        open();

        List<Nap> napList = new ArrayList<Nap>();

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID + "='" + childId + "' ORDER BY " + KEY_DATE_TIME + " DESC";
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do{
                napList.add(cursorToNap(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return napList;
    }
    
    public List<Nap> getNapsForChildByDate(String childId, String date){

        open();

        List<Nap> napList = new ArrayList<Nap>();

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID + "='" + childId + "' AND "
                + KEY_DATE + " = '" + date + "'";
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
    public Nap[] getLastNapsByChildAsArray(String childId, int limit){

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
    public int updateNap(Nap nap, String id) throws ParseException {

        Calendar now = Calendar.getInstance();

        //NOTE: DateTime is a combination of Date and StartTime converted into ISO8601 format. Used for date sorting.
        SimpleDateFormat androidTime = new SimpleDateFormat(DateUtil.ANDROID_TIME_FORMAT);
        SimpleDateFormat iso8601 = new SimpleDateFormat(DateUtil.ISO8601_FORMAT);
        Date dateTime = androidTime.parse(nap.getDate().trim() + ":" + nap.getStartTime());

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, nap.getDate());
        values.put(KEY_START_TIME, nap.getStartTime());
        values.put(KEY_END_TIME, nap.getEndTime());
        values.put(KEY_DATE_TIME, iso8601.format(dateTime));
        values.put(KEY_LOCATION, nap.getLocation());
        values.put(KEY_CHILD_ID, nap.getChild());
        values.put(KEY_LAST_MOD_DATE, iso8601.format(now.getTime()));

        // updating row
        int result = database.update(TABLE_DATA, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });

        close();

        return result;
    }

    /**
     * Deletes naps for babyId from db
     */
    public void deleteNap(String babyId) {
        open();
        database.delete(TABLE_DATA, KEY_CHILD_ID + " = ?", new String[]{babyId});
        close();
    }

    /**
     * Deletes nap with ID from db
     */
    public void deleteNapByID(String ID) {
        open();
        database.delete(TABLE_DATA, KEY_ID + " = ?", new String[]{ID});
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
        return new Nap(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10));
    }
}