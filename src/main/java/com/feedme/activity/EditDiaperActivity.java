package com.feedme.activity;

import android.app.Activity;
import android.os.Bundle;
import com.feedme.R;
import com.feedme.model.Baby;
import com.feedme.model.Diaper;

/**
 * User: dayelostraco
 * Date: 2/4/12
 * Time: 1:35 PM
 */
public class EditDiaperActivity extends DiaperActivity {

    final Bundle bundle = new Bundle();

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diaper);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Edit-Diaper");

        //Baby Data
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        bundle.putSerializable("baby", baby);
        styleActivity(baby.getSex());
        
        //Diaper Data
        final Diaper diaper = (Diaper) getIntent().getSerializableExtra("diaper");
        System.out.println("diaper = " + diaper.getId());
    }
}