package com.feedmesomore.activity;

import android.app.*;
import android.content.DialogInterface;
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
public class EditBottleFeedActivity extends FeedActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_bottle_feed_entry);

        final JournalDao journalDao = new JournalDao(getApplicationContext());

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        final Journal journal = (Journal) getIntent().getSerializableExtra("journal");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        styleActivity(baby.getSex());

        entryDate = (Button) findViewById(R.id.entryDate);
        startTime = (Button) findViewById(R.id.addStartTime);
        endTime = (Button) findViewById(R.id.addEndTime);

        Button editEntryButton = (Button) findViewById(R.id.editEntryButton);

        // prepare settings data
        final SettingsDao settingsDao = new SettingsDao(getApplicationContext());
        Settings setting = settingsDao.getSetting(1);

        //populate entry data
        entryDate.setText(journal.getDate());
        startTime.setText(journal.getStartTime());
        endTime.setText(journal.getEndTime());

        //populate settings data
        TextView entryUnits = (TextView) findViewById(R.id.entryUnits);
        final Spinner feedAmt = (Spinner) findViewById(R.id.feedAmt);

        double ounces = Double.parseDouble(journal.getOunces());

        if (setting.getLiquid().equals("oz")) {

            //populate ounces spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.feedingAmountOz, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feedAmt.setAdapter(adapter);
            entryUnits.setText("Ounces: ");
            double feedAmtPos = ounces / 0.5;
            feedAmt.setSelection((int) feedAmtPos - 1);

        } else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.feedingAmountMl, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feedAmt.setAdapter(adapter);
            entryUnits.setText("Milliliters: ");
            feedAmt.setSelection(((int)ounces / 10) - 1);
        }

        // add a click listener to the button
        entryDate.setOnClickListener(showDateDialog());

        // add a click listener to the button
        startTime.setOnClickListener(showStartTimeDialog());

        // add a click listener to the button
        endTime.setOnClickListener(showEndTimeDialog());

        setupCalendar();

        editEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    journalDao.updateEntry(new Journal(entryDate.getText().toString(),
                            startTime.getText().toString(),
                            endTime.getText().toString(),
                            " ",
                            " ",
                            feedAmt.getSelectedItem().toString(),
                            baby.getId()), journal.getId());
                } catch (ParseException e) {
                    Log.d("EditBottleFeedActivity", "Could not parse Date and StartTime into a ISO8601 format");
                }

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

        //Add Delete Button`
        Button deleteButton = (Button) findViewById(R.id.deleteEntry);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteEntry(journal.getId(), baby);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(EditBottleFeedActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(EditBottleFeedActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(EditBottleFeedActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

    private void deleteEntry(final String entryID, final Baby baby) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditBottleFeedActivity.this);
        myAlertDialog.setTitle("Delete Entry");
        myAlertDialog.setMessage("Are you sure?");
        myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                JournalDao journalDao = new JournalDao(getApplicationContext());
                journalDao.deleteEntryByID(entryID);
                Intent intent = new Intent(EditBottleFeedActivity.this, ViewBabyActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);
                intent.putExtras(bundle);

                startActivityForResult(intent, 3);

            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        myAlertDialog.show();

    }

    public static void main(String[] args) {

        Integer.parseInt("1.5");


    }

}