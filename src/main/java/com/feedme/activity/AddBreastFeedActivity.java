package com.feedme.activity;

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
import com.feedme.service.FeedMeLocationService;
import com.feedme.util.DateUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddBreastFeedActivity extends FeedActivity
{
    private TextView timerDuration;
    private Spinner entrySide;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_breast_feed_entry);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Add-Breast-Feeding");

        final JournalDao journalDao = new JournalDao(getApplicationContext());
        final FeedMeLocationService feedMeLocationService = FeedMeLocationService.getInstance(getApplicationContext(), null);
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        timerDuration = (TextView) findViewById(R.id.timerDuration);
        entrySide = (Spinner) findViewById(R.id.entrySide);

        // get the current date
        Calendar calendar = new GregorianCalendar();
        Calendar calendarStart = new GregorianCalendar();
        Calendar calendarStop = new GregorianCalendar();

        //populate left/right spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.entrySide, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner s = (Spinner) findViewById(R.id.entrySide);
        s.setAdapter(adapter);

        Bundle b = getIntent().getExtras();

        long timerStart = 0L;
        long timerStop = 0L;
        long duration = 0L;

        if (b.get("timerStart") != null)
        {
            timerStart = b.getLong("timerStart");
            timerStop = b.getLong("timerStop");
            duration = b.getLong("duration");

            Log.d("Journal: ", String.valueOf(duration));

            Date dateStart = new Date(timerStart);
            Date dateStop = new Date(timerStop);
            Date dateDuration = new Date(duration);

            DateUtil dateUtil = new DateUtil();
            timerDuration.setText(dateUtil.convertDateLongToTimeString(dateDuration.getTime()));
            entrySide.setSelection(b.getInt("entrySide"));

            calendarStart.setTime(dateStart);
            calendarStop.setTime(dateStop);
        }

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        startHour = calendarStart.get(Calendar.HOUR_OF_DAY);
        startMinute = calendarStart.get(Calendar.MINUTE);
        startSecond = calendarStart.get(Calendar.SECOND);

        endHour = calendarStop.get(Calendar.HOUR_OF_DAY);
        endMinute = calendarStop.get(Calendar.MINUTE);
        endSecond = calendarStop.get(Calendar.SECOND);

        styleActivity(baby.getSex());

        // button listener for add child button
        final Spinner entrySide = (Spinner) findViewById(R.id.entrySide);
        Button addEntryButton = (Button) findViewById(R.id.addEntryButton);
        Button startTimer = (Button) findViewById(R.id.startTimer);

        entryDate = (Button) findViewById(R.id.entryDate);
        startTime = (Button) findViewById(R.id.addStartTime);
        endTime = (Button) findViewById(R.id.addEndTime);

        // add a click listener to the button
        entryDate.setOnClickListener(showDateDialog());

        // add a click listener to the button
        startTime.setOnClickListener(showStartTimeDialog());

        // add a click listener to the button
        endTime.setOnClickListener(showEndTimeDialog());

        // display the current date
        updateDateDisplay();
        updateStartDisplay();
        updateEndDisplay();

        final long dur = duration;

        addEntryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Inserting entry
                Journal insertJournal = new Journal(entryDate.getText().toString(),
                                                    startTime.getText().toString(),
                                                    endTime.getText().toString(),
                                                    String.valueOf(dur),
                                                    entrySide.getSelectedItem().toString(),
                                                    "",
                                                    baby.getId());
                insertJournal.setLatitude(Double.toString(feedMeLocationService.getLatitude()));
                insertJournal.setLongitude(Double.toString(feedMeLocationService.getLongitude()));

                Log.d("JOURNAL-ADD: ", insertJournal.dump());

                try {
                    journalDao.addEntry(insertJournal);
                } catch (ParseException e){
                    Log.d("AddBreastFeedActivity", "Could not parse Date and StartTime into a ISO8601 format");
                }

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), TimerActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("entrySide", entrySide.getSelectedItemPosition());
                startActivityForResult(intent, 3);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(AddBreastFeedActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(AddBreastFeedActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(AddBreastFeedActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

}
