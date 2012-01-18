package com.feedme.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.feedme.R;
import com.feedme.dao.JournalDao;
import com.feedme.model.Journal;

import java.util.Calendar;
import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class AddBottleFeedActivity extends Activity
{
    private Button entryDate;

    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_bottle_feed_entry);
        final JournalDao journalDao = new JournalDao(getApplicationContext());

        // button listener for add child button

        final EditText entryTime = (EditText) findViewById(R.id.entryTime);
        final EditText entryMinLeft = (EditText) findViewById(R.id.entryMinLeft);
        final EditText entryMinRight = (EditText) findViewById(R.id.entryMinRight);
        final EditText entryOunces = (EditText) findViewById(R.id.entryOunces);
        final EditText entryChild = (EditText) findViewById(R.id.entryChild);
        Button addEntryButton = (Button) findViewById(R.id.addEntryButton);

        entryDate = (Button) findViewById(R.id.entryDate);

        // add a click listener to the button
        entryDate.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
        updateDisplay();

        addEntryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                /**
                 * CRUD Operations
                 * */
                // Inserting entry
                Log.d("Insert: ", "Inserting ..");
                journalDao.addEntry(new Journal(entryDate.getText().toString(), entryTime.getText().toString(), " ",
                        " ", entryOunces.getText().toString(), Integer.parseInt(entryChild.getText().toString())));

                // Reading all entries
                Log.d("Reading: ", "Reading all entries..");
                List<Journal> entries = journalDao.getAllEntries();

                for (Journal entry : entries) {
                    String log = "Id: " + entry.getID() + " ,Date: " + entry.getDate() + " ," +
                            "Time: " + entry.getTime() + " ,Left: " + entry.getMinLeft() + " ," +
                            "Right: " + entry.getMinRight() + " ,Ounces: " + entry.getOunces() + " ," +
                            "Child: " + entry.getChild();
                    // Writing entries to log
                    Log.d("Name: ", log);
                }

                entryDate.setText("");
                entryTime.setText("");
                entryOunces.setText("");
                entryChild.setText("");
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
            case R.id.family:
                startActivity(new Intent(AddBottleFeedActivity.this,
                        FamilyHomeActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(AddBottleFeedActivity.this,
                        HomeActivity.class));
                break;
        }
        return true;
    }

    // updates the date we display in the TextView
    private void updateDisplay()
    {
        entryDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
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
                    updateDisplay();
                }
            };
}