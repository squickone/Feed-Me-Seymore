package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.feedme.R;
import com.feedme.dao.DiaperDao;
import com.feedme.model.Baby;

import java.util.Calendar;

/**
 * User: dayelostraco
 * Date: 2/4/12
 * Time: 1:34 PM
 */
public class AddDiaperActivity extends DiaperActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diaper);

        final DiaperDao diaperDao = new DiaperDao(getApplicationContext());

        //Get/Set Baby
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        //Set Layout
        styleActivity(baby.getSex());

        //Set Date and Time Buttons
        entryDate = (Button) findViewById(R.id.diaperDate);
        startTime = (Button) findViewById(R.id.diaperTime);
        entryDate.setOnClickListener(showDateDialog());
        startTime.setOnClickListener(showStartTimeDialog());

        //Get the Current Time and set the Date and Time Buttons
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        startHour = c.get(Calendar.HOUR_OF_DAY);
        startMinute = c.get(Calendar.MINUTE);
        startSecond = c.get(Calendar.SECOND);

        updateDateDisplay();
        updateStartDisplay();

        //Save Diaper Button
        Button addDiaperButton = (Button) findViewById(R.id.addDiaperButton);
        addDiaperButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO: Save Diaper to DAO

                Intent intent = new Intent(v.getContext(), ViewDiapersActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, ADD_DIAPER_ACTIVITY_ID);
            }
        });
    }
}