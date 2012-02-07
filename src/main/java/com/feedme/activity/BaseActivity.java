package com.feedme.activity;

import android.app.Activity;
import android.widget.RelativeLayout;
import com.feedme.R;

/**
 * User: steve quick
 * Date: 1/22/12
 * Time: 9:34 AM
 */

public abstract class BaseActivity extends Activity
{
    public static final int DATE_DIALOG_ID = 0;
    public static final int START_TIME_DIALOG_ID = 1;
    public static final int END_TIME_DIALOG_ID = 2;
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
    
}
