package com.feedme.activity;

import android.app.*;
import android.content.DialogInterface;
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
import com.feedme.dao.NapDao;
import com.feedme.model.Baby;
import com.feedme.model.Nap;

import java.util.Calendar;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddNapActivity extends BaseActivity
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

        setContentView(R.layout.add_nap);
        final NapDao napDao = new NapDao(getApplicationContext());

        //get associated baby ID
        Bundle b = getIntent().getExtras();
        final int babyId = b.getInt("babyId");

        styleActivity(b.getString("babyGender"));

        //declare fields
        Button addNapButton = (Button) findViewById(R.id.addNapButton);

        napDate = (Button) findViewById(R.id.napDate);
        napStartTime = (Button) findViewById(R.id.addStartTime);
        napEndTime = (Button) findViewById(R.id.addEndTime);
        napLocation = (EditText) findViewById(R.id.napLocation);

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

        // display the current date
        updateDateDisplay();
        updateStartDisplay();
        updateEndDisplay();

        //declare alert dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        addNapButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //if location/notes not filled out, throw error
                if (napLocation.getText().toString().equals("")) {
                    alertDialog.setTitle("Oops!");
                    alertDialog.setMessage("Please fill out the form completely.");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }  else {      // else add nap to database

                        /**
                       * CRUD Operations
                       * */
                    // Inserting entry
                    Log.d("Insert: ", "Inserting ..");
                    Log.d("INSERT ENTRY DATE: ", napDate.getText().toString());
                    Log.d("INSERT START TIME: ", napStartTime.getText().toString());
                    Log.d("INSERT END TIME: ", napEndTime.getText().toString());
                    Log.d("INSERT FEED QTY: ", napLocation.getText().toString());
                    Log.d("INSERT BABY ID: ", String.valueOf(babyId));
                    napDao.addNap(new Nap(napDate.getText().toString(),
                            napStartTime.getText().toString(),
                            napEndTime.getText().toString(),
                            napLocation.getText().toString(),
                            babyId));

                    final BabyDao babyDao = new BabyDao(getApplicationContext());
                    Baby baby = babyDao.getBaby(babyId);
                    final String babyName = baby.getName();

                    Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                    intent.putExtra("babyName", babyName);
                    startActivityForResult(intent, 3);
                }

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
            case R.id.report:
                startActivity(new Intent(AddNapActivity.this,
                        ReportBugActivity.class));
                break;
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
                startActivity(new Intent(AddNapActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(AddNapActivity.this,
                        SettingsActivity.class));
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



}