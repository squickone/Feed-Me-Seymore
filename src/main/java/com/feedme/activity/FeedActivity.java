package com.feedme.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.feedme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User: steve quick
 * Date: 1/27/12
 * Time: 8:01 PM
 */

public abstract class FeedActivity extends JournalActivity
{



    public String feedQty;


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

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
