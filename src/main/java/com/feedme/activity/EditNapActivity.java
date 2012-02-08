package com.feedme.activity;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.NapDao;
import com.feedme.model.Baby;
import com.feedme.model.Nap;

import java.util.Calendar;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class EditNapActivity extends NapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_nap);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Edit-Nap");

        final NapDao napDao = new NapDao(getApplicationContext());

        entryDate = (Button) findViewById(R.id.entryDate);
        startTime = (Button) findViewById(R.id.startTime);
        endTime = (Button) findViewById(R.id.endTime);
        location = (EditText) findViewById(R.id.location);
        Button editNapButton = (Button) findViewById(R.id.editNapButton);

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        final Nap nap = (Nap) getIntent().getSerializableExtra("nap");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        styleActivity(baby.getSex());

        //populate nap data
        entryDate.setText(nap.getDate());
        startTime.setText(nap.getStartTime());
        endTime.setText(nap.getEndTime());
        location.setText(nap.getLocation());

        // add a click listener to the button
        entryDate.setOnClickListener(showDateDialog());

        // add a click listener to the button
        startTime.setOnClickListener(showStartTimeDialog());

        // add a click listener to the button
        endTime.setOnClickListener(showEndTimeDialog());

        // get the current date
        final Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        startHour = calendar.get(Calendar.HOUR_OF_DAY);
        startMinute = calendar.get(Calendar.MINUTE);
        startSecond = calendar.get(Calendar.SECOND);

        endHour = calendar.get(Calendar.HOUR_OF_DAY);
        endMinute = calendar.get(Calendar.MINUTE);
        endSecond = calendar.get(Calendar.SECOND);

        editNapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * CRUD Operations
                 * */
                // Inserting entry

                napDao.updateNap(new Nap(entryDate.getText().toString(),
                        startTime.getText().toString(),
                        endTime.getText().toString(),
                        location.getText().toString(),
                        baby.getId()), nap.getId());

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);

            }
        });

        //Add Delete Button`
        Button deleteButton = (Button) findViewById(R.id.deleteNap);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteNap(nap.getId(), baby);
            }
        });
    }

    private void deleteNap(final int napID, final Baby baby) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditNapActivity.this);
        myAlertDialog.setTitle("Delete Nap");
        myAlertDialog.setMessage("Are you sure?");
        myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                NapDao napDao = new NapDao(getApplicationContext());
                napDao.deleteNapByID(napID);
                Intent intent = new Intent(EditNapActivity.this, ViewNapsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("baby", baby);
                intent.putExtras(b);
                startActivityForResult(intent, 3);

            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        myAlertDialog.show();

    }
}