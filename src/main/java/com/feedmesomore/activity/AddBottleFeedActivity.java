package com.feedmesomore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedmesomore.R;
import com.feedmesomore.dao.JournalDao;
import com.feedmesomore.dao.SettingsDao;
import com.feedmesomore.model.Baby;
import com.feedmesomore.model.Journal;
import com.feedmesomore.model.Settings;

import java.text.ParseException;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddBottleFeedActivity extends FeedActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bottle_feed_entry);

        final JournalDao journalDao = new JournalDao(getApplicationContext());
        //final FeedMeLocationService feedMeLocationService = FeedMeLocationService.getInstance(getApplicationContext(), null);
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        styleActivity(baby.getSex());

        Button addEntryButton = (Button) findViewById(R.id.addEntryButton);

        entryDate = (Button) findViewById(R.id.entryDate);
        startTime = (Button) findViewById(R.id.addStartTime);
        endTime = (Button) findViewById(R.id.addEndTime);

        // prepare settings data
        final SettingsDao settingsDao = new SettingsDao(getApplicationContext());
        Settings setting = settingsDao.getSetting(1);

        //populate settings data
        TextView entryUnits = (TextView) findViewById(R.id.entryUnits);
        final Spinner feedAmt = (Spinner) findViewById(R.id.feedAmt);

        if (setting.getLiquid().equals("oz")) {
            //populate ounces spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.feedingAmountOz, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feedAmt.setAdapter(adapter);
            entryUnits.setText("Ounces: ");
        } else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.feedingAmountMl, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feedAmt.setAdapter(adapter);
            entryUnits.setText("Milliliters: ");
        }

        // add a click listener to the button
        entryDate.setOnClickListener(showDateDialog());

        // add a click listener to the button
        startTime.setOnClickListener(showStartTimeDialog());

        // add a click listener to the button
        endTime.setOnClickListener(showEndTimeDialog());

        setupCalendar();

        // display the current date
        updateDateDisplay();
        updateStartDisplay();
        updateEndDisplay();

        addEntryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Journal insertJournal = new Journal(entryDate.getText().toString(),
                                                    startTime.getText().toString(),
                                                    endTime.getText().toString(),
                                                    "",
                                                    "",
                                                    feedAmt.getSelectedItem().toString(),
                                                    baby.getId());
                //insertJournal.setLatitude(Double.toString(feedMeLocationService.getLatitude()));
                //insertJournal.setLongitude(Double.toString(feedMeLocationService.getLongitude()));
                Log.d("JOURNAL-ADD:", insertJournal.dump());

                try {
                    journalDao.addEntry(insertJournal);
                } catch (ParseException e){
                    Log.d("AddBottleFeedActivity", "Could not parse Date and StartTime into a ISO8601 format");
                }

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(AddBottleFeedActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(AddBottleFeedActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(AddBottleFeedActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

}