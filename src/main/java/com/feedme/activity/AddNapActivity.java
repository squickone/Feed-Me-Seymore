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
public class AddNapActivity extends NapActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_nap);
        final NapDao napDao = new NapDao(getApplicationContext());

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        styleActivity(baby.getSex());

        //declare fields
        Button addNapButton = (Button) findViewById(R.id.addNapButton);

        napDate = (Button) findViewById(R.id.napDate);
        napStartTime = (Button) findViewById(R.id.addStartTime);
        napEndTime = (Button) findViewById(R.id.addEndTime);
        napLocation = (EditText) findViewById(R.id.napLocation);

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

                    Nap nap = new Nap(napDate.getText().toString(),
                                        napStartTime.getText().toString(),
                                        napEndTime.getText().toString(),
                                        napLocation.getText().toString(),
                                        baby.getID());
                    Log.d("NAP-INSERT:", nap.dump());
                    
                    napDao.addNap(nap);
                    
                    Intent intent = new Intent(v.getContext(), ViewNapsActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 3);

                }

            }
        });
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

}