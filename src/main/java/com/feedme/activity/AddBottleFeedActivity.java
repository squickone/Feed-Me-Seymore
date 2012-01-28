package com.feedme.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.JournalDao;
import com.feedme.dao.SettingsDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;
import com.feedme.model.Settings;

import java.util.Calendar;

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
                                                    baby.getID());
                Log.d("JOURNAL-ADD:", insertJournal.dump());
                journalDao.addEntry(insertJournal);

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