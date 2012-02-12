package com.feedme.activity;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.JournalDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class EditBreastFeedActivity extends FeedActivity
{
    private Spinner entrySide;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_breast_feed_entry);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Edit-Breast-Feeding");

        final JournalDao journalDao = new JournalDao(getApplicationContext());

        //populate left/right spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.entrySide, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner s = (Spinner) findViewById(R.id.entrySide);
        s.setAdapter(adapter);

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        final Journal journal = (Journal) getIntent().getSerializableExtra("journal");

        Log.d("FEED: ", journal.dump());

        styleActivity(baby.getSex());

        //populate entry data
        entryDate = (Button) findViewById(R.id.entryDate);
        startTime = (Button) findViewById(R.id.addStartTime);
        endTime = (Button) findViewById(R.id.addEndTime);
        TextView timerDuration = (TextView) findViewById(R.id.timerDuration);

        entrySide = (Spinner) findViewById(R.id.entrySide);
        Button editEntryButton = (Button) findViewById(R.id.editEntryButton);

        entryDate.setText(journal.getDate());
        startTime.setText(journal.getStartTime());
        endTime.setText(journal.getEndTime());

        if (journal.getFeedTime().trim().length() > 0)
        {
            Date dateDuration = new Date(Long.valueOf(journal.getFeedTime()));
            timerDuration.setText(simpleTimeFormat.format(dateDuration));
        }

        if (journal.getSide().equals("Left")) {
            entrySide.setSelection(0);
        } else {
            entrySide.setSelection(1);
        }

        // add a click listener to the button
        entryDate.setOnClickListener(showDateDialog());

        // add a click listener to the button
        startTime.setOnClickListener(showStartTimeDialog());

        // add a click listener to the button
        endTime.setOnClickListener(showEndTimeDialog());

        // get the current date
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        startHour = c.get(Calendar.HOUR_OF_DAY);
        startMinute = c.get(Calendar.MINUTE);
        startSecond = c.get(Calendar.SECOND);

        endHour = c.get(Calendar.HOUR_OF_DAY);
        endMinute = c.get(Calendar.MINUTE);
        endSecond = c.get(Calendar.SECOND);

        editEntryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    journalDao.updateEntry(new Journal(entryDate.getText().toString(),
                            startTime.getText().toString(),
                            endTime.getText().toString(),
                            journal.getFeedTime(),
                            entrySide.getSelectedItem().toString(),
                            "",
                            baby.getId()), journal.getId());
                } catch (ParseException e){
                    Log.d("EditBreastFeedActivity", "Could not parse Date and StartTime into a ISO8601 format");
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

        //Add Delete Button`
        Button deleteButton = (Button) findViewById(R.id.deleteEntry);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                deleteEntry(journal.getId(), baby);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(EditBreastFeedActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(EditBreastFeedActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(EditBreastFeedActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

    private void deleteEntry(final int entryID, final Baby baby)
    {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditBreastFeedActivity.this);
        myAlertDialog.setTitle("Delete Entry");
        myAlertDialog.setMessage("Are you sure?");
        myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface arg0, int arg1)
            {
                JournalDao journalDao = new JournalDao(getApplicationContext());
                journalDao.deleteEntryByID(entryID);
                Intent intent = new Intent(EditBreastFeedActivity.this, ViewBabyActivity.class);
                
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);
                intent.putExtras(bundle);
                
                startActivityForResult(intent, 3);
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface arg0, int arg1)
            {

            }
        });
        myAlertDialog.show();
    }

}