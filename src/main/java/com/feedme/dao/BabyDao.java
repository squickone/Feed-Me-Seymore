package com.feedme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.feedme.database.BabyColumn;
import com.feedme.model.Baby;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This DAO class handles all CRUD operations on the Babies table in the SQLLite database.
 */
public class BabyDao {

    // Babies table name
    private static final String TABLE_DATA = BabyColumn.TABLE_NAME;

    // Babies Table Columns names
    private static final String KEY_ID = BabyColumn.ID.columnName();
    private static final String KEY_NAME = BabyColumn.NAME.columnName();
    private static final String KEY_SEX = BabyColumn.SEX.columnName();
    private static final String KEY_HEIGHT = BabyColumn.HEIGHT.columnName();
    private static final String KEY_WEIGHT = BabyColumn.WEIGHT.columnName();
    private static final String KEY_DOB = BabyColumn.DOB.columnName();
    private static final String KEY_PICTURE = BabyColumn.PICTURE.columnName();
    private static final String KEY_LATITUDE = BabyColumn.LATITUDE.columnName();
    private static final String KEY_LONGITUDE = BabyColumn.LONGITUDE.columnName();
    private static final String KEY_CREATED_DATE = BabyColumn.CREATED_DATE.columnName();
    private static final String KEY_LAST_MOD_DATE = BabyColumn.LAST_MOD_DATE.columnName();

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
    public void addBaby(Baby baby) {

        SimpleDateFormat sdf = new SimpleDateFormat("M-dd-yyyy HH:mm:ss");
        Calendar now = Calendar.getInstance();

        open();
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, baby.getName()); // Baby name
        values.put(KEY_SEX, baby.getSex()); // Baby sex
        values.put(KEY_HEIGHT, baby.getHeight()); // Baby height
        values.put(KEY_WEIGHT, baby.getWeight()); // Baby weight
        values.put(KEY_DOB, baby.getDob()); // Baby dob
        values.put(KEY_PICTURE, baby.getPicturePath()); // Baby picturePath
        values.put(KEY_LATITUDE, baby.getLatitude());
        values.put(KEY_LONGITUDE, baby.getLongitude());
        values.put(KEY_CREATED_DATE, sdf.format(now.getTime()));
        values.put(KEY_LAST_MOD_DATE, sdf.format(now.getTime()));

        // Inserting Row
        database.insert(TABLE_DATA, null, values);
        
        close();
    }

    /**
     * Getting single baby from the database by its ID.
     *
     * @param id - The ID of the Baby in the database.
     *
     * @return - Baby POJO representation of a specific Baby in the database.
     */
    public Baby getBaby(int id) {

        open();
        
        Cursor cursor = database.query(TABLE_DATA, new String[] { KEY_ID,
                KEY_NAME, KEY_SEX, KEY_HEIGHT, KEY_WEIGHT, KEY_DOB, KEY_PICTURE, KEY_LATITUDE, KEY_LONGITUDE,
                KEY_CREATED_DATE, KEY_LAST_MOD_DATE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Baby baby =  cursorToBaby(cursor);

        close();

        return baby;
    }

    /**
     * Getting single baby from the database by its ID.
     *
     * @param name - The full name of the Baby in the database.
     *
     * @return - Baby POJO representation of a specific Baby in the database.
     */
    public Baby getBabyByName(String name) {

        open();

        Cursor cursor = database.query(TABLE_DATA, new String[] { KEY_ID,
                KEY_NAME, KEY_SEX, KEY_HEIGHT, KEY_WEIGHT, KEY_DOB, KEY_PICTURE, KEY_LATITUDE, KEY_LONGITUDE,
                KEY_CREATED_DATE, KEY_LAST_MOD_DATE}, KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Baby baby =  cursorToBaby(cursor);

        close();

        return baby;
    }

    /**
     * Get All Babies from the Database
     *
     * @return - Collection of all Babies located in the database.
     */
    public List<Baby> getAllBabies() {

        open();

        List<Baby> babyList = new ArrayList<Baby>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                babyList.add(cursorToBaby(cursor));
            } while (cursor.moveToNext());
        }

        close();

        return babyList;
    }

    /**
     * Returns a String[] of all babies by name.
     *
     * @return
     */
    public Baby[] getAllBabiesAsStringArray(){

        open();

        List<Baby> babies = getAllBabies();
        
        Baby[] babyArray = new Baby[babies.size()];
        for(int i=0 ; i<babies.size() ; i++){
            Baby baby = babies.get(i);
            babyArray[i] = baby;
        }

        close();

        return babyArray;
    }

    /**
     * Updates an existing Baby in the database.
     *
     * @param baby
     * @param id
     * @return
     */
    public int updateBaby(Baby baby, int id) {

        SimpleDateFormat sdf = new SimpleDateFormat("M-dd-yyyy HH:mm:ss");
        Calendar now = Calendar.getInstance();

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, baby.getName());
        values.put(KEY_SEX, baby.getSex());
        values.put(KEY_HEIGHT, baby.getHeight());
        values.put(KEY_WEIGHT, baby.getWeight());
        values.put(KEY_DOB, baby.getDob());
        values.put(KEY_PICTURE, baby.getPicturePath());
        values.put(KEY_LAST_MOD_DATE, sdf.format(now.getTime()));

        // updating row
        int result = database.update(TABLE_DATA, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });

        close();

        return result;
    }

    /**
     * Deletes a Baby from the Database
     *
     * @param baby
     * @param id
     */
    public void deleteBaby(Baby baby, int id) {
        open();
        database.delete(TABLE_DATA, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        close();
    }

    /**
     * Returns the total number of Babies that exist in the Database.
     *
     * @return - Total number of babies in the Babies table.
     */
    public int getBabiesCount() {
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
     * Converts a Cursor from the Babies Table to a Baby Object.
     *
     * @param cursor - Cursor object that contains the Baby data from the Database
     *
     * @return - Baby
     */
    private Baby cursorToBaby(Cursor cursor) {
        return new Baby(Integer.parseInt(cursor.getString(0)),
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