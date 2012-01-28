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
public class EditNapActivity extends NapActivity
{

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

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        final Nap nap = (Nap) getIntent().getSerializableExtra("nap");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        styleActivity(baby.getSex());

        //populate nap data
        napDate.setText(nap.getDate());
        napStartTime.setText(nap.getStartTime());
        napEndTime.setText(nap.getEndTime());
        napLocation.setText(nap.getLocation());

        // add a click listener to the button
        napDate.setOnClickListener(showDateDialog());

        // add a click listener to the button
        napStartTime.setOnClickListener(showStartTimeDialog());

        // add a click listener to the button
        napEndTime.setOnClickListener(showEndTimeDialog());

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
                        baby.getID()), nap.getID());

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);

            }
        });

        //Add Delete Button`
        Button deleteButton = (Button) findViewById(R.id.deleteNap);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteNap(nap.getID(), baby);
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

            }});
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }});
        myAlertDialog.show();

    }
}