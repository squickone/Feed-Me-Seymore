package com.feedme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedme.database.DiaperColumn;
import com.feedme.model.Diaper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This DAO class handles all CRUD operations on the Diapers table in the SQLLite database.
 */
public class DiaperDao {

    private static final SimpleDateFormat ISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat ANDROID_TIME = new SimpleDateFormat("M-d-yyyy:hh:mm:ss");

    // Contacts table name
    private static final String TABLE_DATA = DiaperColumn.TABLE_NAME;

    // Contacts Table Columns names
    private static final String KEY_ID = DiaperColumn.ID.columnName();
    private static final String KEY_TYPE = DiaperColumn.TYPE.columnName();
    private static final String KEY_CONSISTENCY = DiaperColumn.CONSISTENCY.columnName();
    private static final String KEY_COLOR = DiaperColumn.COLOR.columnName();
    private static final String KEY_DATE = DiaperColumn.DATE.columnName();
    private static final String KEY_TIME = DiaperColumn.TIME.columnName();
    private static final String KEY_DATE_TIME = DiaperColumn.DATE_TIME.columnName();
    private static final String KEY_CHILD_ID = DiaperColumn.CHILD_ID.columnName();
    private static final String KEY_LATITUDE = DiaperColumn.LATITUDE.columnName();
    private static final String KEY_LONGITUDE = DiaperColumn.LONGITUDE.columnName();
    private static final String KEY_CREATED_DATE = DiaperColumn.CREATED_DATE.columnName();
    private static final String KEY_LAST_MOD_DATE = DiaperColumn.LAST_MOD_DATE.columnName();

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public DiaperDao(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = databaseHandler.getWritableDatabase();
    }

    public void close() {
        databaseHandler.close();
    }

    /**
     * Adds a new Diaper to the Database.
     *
     * @param diaper - Diaper POJO
     */
    public void addDiaper(Diaper diaper) throws ParseException {

        Calendar now = Calendar.getInstance();

        //NOTE: DateTime is a combination of Date and StartTime converted into ISO8601 format. Used for date sorting.
        Date dateTime = ANDROID_TIME.parse(diaper.getDate().trim() + ":" + diaper.getStartTime());

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, diaper.getType());
        values.put(KEY_CONSISTENCY, diaper.getConsistency());
        values.put(KEY_COLOR, diaper.getColor());
        values.put(KEY_DATE, diaper.getDate());
        values.put(KEY_TIME, diaper.getStartTime());
        values.put(KEY_DATE_TIME, ISO8601.format(dateTime));
        values.put(KEY_CHILD_ID, diaper.getChildId());
        values.put(KEY_LATITUDE, diaper.getLatitude());
        values.put(KEY_LONGITUDE, diaper.getLongitude());
        values.put(KEY_CREATED_DATE, ISO8601.format(now.getTime()));
        values.put(KEY_LAST_MOD_DATE, ISO8601.format(now.getTime()));

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
    public Diaper getEntry(int id) {

        open();

        Cursor cursor = database.query(TABLE_DATA, DiaperColumn.getColumnNames(), KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Diaper diaper =  cursorToDiaper(cursor);

        close();

        return diaper;
    }


    /**
     * Getting single diaper from the database by its date.
     *
     * @param date - The date of the diaper in the database.
     *
     * @return - Diaper POJO representation of a specific diaper in the database.
     */
    public Diaper getDiaperByDate(String date) {

        open();

        Cursor cursor = database.query(TABLE_DATA, DiaperColumn.getColumnNames(), KEY_DATE + "=?",
                new String[] { String.valueOf(date) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Diaper diaper =  cursorToDiaper(cursor);

        close();

        return diaper;
    }

    /**
     * Get All Diapers from the Database
     *
     * @return - Collection of all Entries located in the database.
     */
    public List<Diaper> getAllDiapers() {

        open();

        List<Diaper> diaperList = new ArrayList<Diaper>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                diaperList.add(cursorToDiaper(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return diaperList;
    }

    /**
     * Returns a Diaper[] of all diapers.
     *
     * @return
     */
    public Diaper[] getAllDiapersAsArray(){

        List<Diaper> diapers = getAllDiapers();

        Diaper[] diaperArray = new Diaper[diapers.size()];
        for(int i=0 ; i<diapers.size() ; i++){
            Diaper entry = diapers.get(i);
            diaperArray[i] = entry;
        }

        return diaperArray;
    }

    /**
     * Returns the last X diapers (where X=limit) based on the passed in childId in the form of a List<Diaper>.
     *
     * @param childId
     * @param limit
     * @return
     */
    public List<Diaper> getLastDiapersByChild(int childId, int limit){

        open();

        List<Diaper> diaperList = new ArrayList<Diaper>();

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID + "=" + childId + " ORDER BY "
                + KEY_DATE_TIME + " DESC LIMIT " + limit;
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do{
                diaperList.add(cursorToDiaper(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return diaperList;
    }

    /**
     * Returns all diapers based on the passed in childId in the form of a List<Diaper>.
     *
     * @param childId
     * @return
     */
    public List<Diaper> getAllDiapersByChild(int childId){

        open();

        List<Diaper> diaperList = new ArrayList<Diaper>();

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE " + KEY_CHILD_ID + "=" + childId + " ORDER BY "
                + KEY_DATE_TIME + " DESC";
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do{
                diaperList.add(cursorToDiaper(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return diaperList;
    }

    /**
     * Returns the last X diapers (where X=limit) based on the passed in childId in the form of a Diaper[].
     *
     * @param childId
     * @param limit
     * @return
     */
    public Diaper[] getLastDiapersByChildAsArray(int childId, int limit){

        List<Diaper> diapers = getLastDiapersByChild(childId, limit);

        Diaper[] diaperArray = new Diaper[diapers.size()];
        for(int i=0 ; i<diapers.size() ; i++){
            Diaper diaper = diapers.get(i);
            diaperArray[i] = diaper;
        }

        return diaperArray;
    }

    /**
     * Updates an existing diaper in the database.
     *
     * @return
     */
    public int updateDiaper(Diaper diaper, int id) throws ParseException {

        Calendar now = Calendar.getInstance();

        //NOTE: DateTime is a combination of Date and StartTime converted into ISO8601 format. Used for date sorting.
        Date dateTime = ANDROID_TIME.parse(diaper.getDate().trim() + ":" + diaper.getStartTime());

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, diaper.getType());
        values.put(KEY_CONSISTENCY, diaper.getConsistency());
        values.put(KEY_COLOR, diaper.getColor());
        values.put(KEY_DATE, diaper.getDate());
        values.put(KEY_TIME, diaper.getStartTime());
        values.put(KEY_DATE_TIME, ISO8601.format(dateTime));
        values.put(KEY_CHILD_ID, diaper.getChildId());
        values.put(KEY_LAST_MOD_DATE, ISO8601.format(now.getTime()));

        // updating row
        int result = database.update(TABLE_DATA, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });

        close();

        return result;
    }

    /**
     * Deletes diapers for babyId from db
     */
    public void deleteDiaper(int babyId) {
        open();
        database.delete(TABLE_DATA, KEY_CHILD_ID + " = ?", new String[]{String.valueOf(babyId)});
        close();
    }

    /**
     * Deletes diaper with ID from db
     */
    public void deleteDiaperByID(int id) {
        open();
        database.delete(TABLE_DATA, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        close();
    }

    /**
     * Returns the total number of diapers that exist in the Database.
     *
     * @return - Total number of diapers in the diaper table.
     */
    public int getDiapersCount() {
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
     * Returns the total number of diapers for a baby on a given date.
     *
     * @param babyId
     * @param date
     * @return
     */
    public int getDiapersCountByBabyAndDate(String babyId, String date){
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
     * Converts a Cursor from the diaper Table to a Diaper Object.
     *
     * @param cursor - Cursor object that contains the Diaper data from the Database
     *
     * @return - Diaper
     */
    private Diaper cursorToDiaper(Cursor cursor) {
        return new Diaper(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                Integer.parseInt(cursor.getString(7)),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11));
    }
}
