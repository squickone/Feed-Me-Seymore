package com.feedme.util;

import android.os.Environment;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 11:55 AM
 */
public class FeedMeUtil {

    /**
     * Determines if the application has only read access to the external storage.
     *
     * @return
     */
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    /**
     * Determines if the external storage is mounted and ready for use.
     *
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}