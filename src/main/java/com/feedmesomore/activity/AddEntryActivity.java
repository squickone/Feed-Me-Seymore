package com.feedmesomore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.feedmesomore.R;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddEntryActivity extends BaseActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entry);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Add-Entry");

        //View All Entries Button
        Button viewAllEntriesButton = (Button) findViewById(R.id.viewAllEntriesButton);
        viewAllEntriesButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(AddEntryActivity.this,
                        ViewEntriesActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(AddEntryActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(AddEntryActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(AddEntryActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }
}