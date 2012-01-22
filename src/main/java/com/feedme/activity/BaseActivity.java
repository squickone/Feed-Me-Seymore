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
