package com.feedme.activity;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.BabyDao;
import com.feedme.dao.JournalDao;
import com.feedme.dao.NapDao;
import com.feedme.dao.SettingsDao;
import com.feedme.model.Baby;
import com.feedme.model.Nap;
import com.feedme.model.Settings;

import java.util.Calendar;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class EditNapActivity extends BaseActivity
{
    private Button napDate;
    private Button napStartTime;
    private Button napEndTime;
    private EditText napLocation;

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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_nap);
        final NapDao napDao = new NapDao(getApplicationContext());

        napDate = (Button) findViewById(R.id.napDate);
        napStartTime = (Button) findViewById(R.id.napStartTime);
        napEndTime = (Button) findViewById(R.id.napEndTime);
        napLocation = (EditText) findViewById(R.id.napLocation);
        Button editNapButton = (Button) findViewById(R.id.editNapButton);

        //get baby properties
        final Bundle b = getIntent().getExtras();

        final int napID = b.getInt("napID");
        final String napDateValue = b.getString("napDate");
        final String napStartTimeValue = b.getString("napStartTime");
        final String napEndTimeValue = b.getString("napEndTime");
        final String napLocationValue = b.getString("napLocation");
        final int napChildValue = b.getInt("napChildID");

        styleActivity(b.getString("babyGender"));

        //populate nap data
        napDate.setText(napDateValue);
        napStartTime.setText(napStartTimeValue);
        napEndTime.setText(napEndTimeValue);
        napLocation.setText(napLocationValue);

        // add a click listener to the button
        napDate.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // add a click listener to the button
        napStartTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(STARTTIME_DIALOG_ID);
            }
        });

        // add a click listener to the button
        napEndTime.setOnClickListener(new View.OnClickListener() {
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


       editNapButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                /**
                 * CRUD Operations
                 * */
                // Inserting entry

                napDao.updateNap(new Nap(napDate.getText().toString(),
                        napStartTime.getText().toString(),
                        napEndTime.getText().toString(),
                        napLocation.getText().toString(),
                        napChildValue), napID);

                final BabyDao babyDao = new BabyDao(getApplicationContext());
                Baby baby = babyDao.getBaby(napChildValue);
                final String babyName = baby.getName();

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtra("babyName", babyName);
                startActivityForResult(intent, 3);

            }
        });

        //Add Delete Button`
        Button deleteButton = (Button) findViewById(R.id.deleteNap);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteNap(napID, napChildValue, b.getString("babyGender"));
            }
        });
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
                startActivity(new Intent(EditNapActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(EditNapActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(EditNapActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

    // updates the date we display in the TextView
    private void updateDateDisplay()
    {
        napDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    // add leading zero to times
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    // updates the date we display in the TextView
    private void updateStartDisplay()
    {
        napStartTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pad(startHour)).append(":")
                        .append(pad(startMinute)).append(":")
                        .append(pad(startSecond)));
    }

    // updates the date we display in the TextView
    private void updateEndDisplay()
    {
        napEndTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pad(endHour)).append(":")
                        .append(pad(endMinute)).append(":")
                        .append(pad(endSecond)));
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


    private void deleteNap(final int napID, final int babyId, final String babyGender) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditNapActivity.this);
        myAlertDialog.setTitle("Delete Nap");
        myAlertDialog.setMessage("Are you sure?");
        myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                NapDao napDao = new NapDao(getApplicationContext());
                napDao.deleteNap(napID);
                Intent intent = new Intent(EditNapActivity.this, ViewNapsActivity.class);
                intent.putExtra("babyId", babyId);
                intent.putExtra("babyGender", babyGender);
                startActivityForResult(intent, 3);

            }});
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }});
        myAlertDialog.show();

    }
}