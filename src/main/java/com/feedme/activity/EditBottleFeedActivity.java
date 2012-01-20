package com.feedme.activity;

import android.app.Activity;
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
import com.feedme.dao.BabyDao;
import com.feedme.dao.JournalDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;

import java.util.Calendar;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class EditBottleFeedActivity extends Activity
{
    private Button entryDate;
    private Button startTime;
    private Button endTime;
    private Spinner entryOunces;

    private int mYear;
    private int mMonth;
    private int mDay;

    private int startHour;
    private int startMinute;
    private int startSecond;

    private int endHour;
    private int endMinute;
    private int endSecond;

    static final int DATE_DIALOG_ID = 0;
    static final int STARTTIME_DIALOG_ID = 1;
    static final int ENDTIME_DIALOG_ID = 2;

    private String feedQty;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_bottle_feed_entry);
        final JournalDao journalDao = new JournalDao(getApplicationContext());

        // button listener for add child button

        Bundle b = getIntent().getExtras();
        final int babyId = b.getInt("babyId");


        entryDate = (Button) findViewById(R.id.entryDate);
        startTime = (Button) findViewById(R.id.addStartTime);
        endTime = (Button) findViewById(R.id.addEndTime);
        entryOunces = (Spinner) findViewById(R.id.entryOunces);
        Button editEntryButton = (Button) findViewById(R.id.editEntryButton);

        final int entryID = getIntent().getExtras().getInt("entryID");
        final String entryDateValue = getIntent().getExtras().getString("entryDate");
        final String entryStartTimeValue = getIntent().getExtras().getString("entryStartTime");
        final String entryEndTimeValue = getIntent().getExtras().getString("entryEndTime");
        final String entryOuncesValue = getIntent().getExtras().getString("entryOunces");
        final int entryChildValue = getIntent().getExtras().getInt("entryChild");

        entryDate.setText(entryDateValue);
        startTime.setText(entryStartTimeValue);
        endTime.setText(entryEndTimeValue);
//        entryOunces.setSelection(2);

        // add a click listener to the button
        entryDate.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // add a click listener to the button
        startTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(STARTTIME_DIALOG_ID);
            }
        });

        // add a click listener to the button
        endTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(ENDTIME_DIALOG_ID);
            }
        });

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

        // display the current date
        //updateDateDisplay();
        //updateStartDisplay();
        //updateEndDisplay();

       editEntryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                /**
                 * CRUD Operations
                 * */
                // Inserting entry

                journalDao.updateEntry(new Journal(entryDate.getText().toString(),
                        startTime.getText().toString(),
                        endTime.getText().toString(),
                        " ",
                        entryOunces.getSelectedItem().toString(),
                        entryChildValue), entryID);

                final BabyDao babyDao = new BabyDao(getApplicationContext());
                Baby baby = babyDao.getBaby(entryChildValue);
                final String babyName = baby.getName();

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtra("babyName", babyName);
                startActivityForResult(intent, 3);

            }
        });  

        Spinner feedAmt = (Spinner) findViewById(R.id.feedAmt);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        this, R.array.feedingAmount, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedAmt.setAdapter(adapter);

        feedAmt.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            case STARTTIME_DIALOG_ID:
                return new TimePickerDialog(this,
                startTimeListener, startHour, startMinute, false);
            case ENDTIME_DIALOG_ID:
                return new TimePickerDialog(this,
                endTimeListener, endHour, endMinute, false);
        }
        return null;
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
                startActivity(new Intent(EditBottleFeedActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(EditBottleFeedActivity.this,
                        SettingsActivity.class));
                break;
        }
        return true;
    }

    // updates the date we display in the TextView
    private void updateDateDisplay()
    {
        entryDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    // updates the date we display in the TextView
    private void updateStartDisplay()
    {
        startTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(startHour + 1).append(":")
                        .append(startMinute).append(":")
                        .append(startSecond));
    }

    // updates the date we display in the TextView
    private void updateEndDisplay()
    {
        endTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(endHour + 1).append(":")
                        .append(endMinute).append(":")
                        .append(endSecond));
    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener()
            {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth)
                {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDateDisplay();
                }
            };

    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener startTimeListener =
    new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            startHour = hourOfDay;
            startMinute = minute;
            updateStartDisplay();
        }
    };

    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener endTimeListener =
    new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            endHour = hourOfDay;
            endMinute = minute;
            updateEndDisplay();
        }
    };


    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            feedQty = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent)
        {
            // Do nothing.
        }
    }

}