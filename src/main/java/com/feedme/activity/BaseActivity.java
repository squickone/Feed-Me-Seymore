package com.feedme.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.RelativeLayout;
import com.feedme.R;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
/**
 * User: steve quick
 * Date: 1/22/12
 * Time: 9:34 AM
 */

public abstract class BaseActivity extends Activity
{
    public GoogleAnalyticsTracker googleAnalyticsTracker = GoogleAnalyticsTracker.getInstance();
    public static final String TRACKING_ID = "UA-29019171-1";

    public static final int DATE_DIALOG_ID = 0;
    public static final int START_TIME_DIALOG_ID = 1;
    public static final int END_TIME_DIALOG_ID = 2;
    public static final int VIEW_BABY_ACTIVITY_ID = 3;
    public static final int ADD_CHILD_ACTIVITY_ID = 5;
    public static final int EDIT_CHILD_ACTIVITY_ID = 6;
    public static final int ADD_DIAPER_ACTIVITY_ID = 7;
    public static final int EDIT_DIAPER_ACTIVITY_ID = 8;
    public static final int VIEW_DIAPER_ACTIVITY_ID = 9;

    public void styleActivity(String babyGender)
    {
        final RelativeLayout topBanner = (RelativeLayout) findViewById(R.id.topBanner);
        final RelativeLayout bottomBanner = (RelativeLayout) findViewById(R.id.bottomBanner);

        if (babyGender.equals("Male")) {
            topBanner.setBackgroundColor(0xFF7ED0FF);
            bottomBanner.setBackgroundColor(0xFF7ED0FF);
        } else {
            topBanner.setBackgroundColor(0xFFFF99CC);
            bottomBanner.setBackgroundColor(0xFFFF99CC);
        }
    }


    /**
     * Resizes a Bitmap based on the passed in newHeight and newWidth and rotates the image by rotateInDegrees.
     *
     * @param bitMap
     * @param newHeight
     * @param newWidth
     * @param rotateInDegrees
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap bitMap, int newHeight, int newWidth, int rotateInDegrees)
    {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        //matrix.postRotate(rotateInDegrees);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }
    
}
