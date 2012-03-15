package com.feedmesomore.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import com.feedmesomore.R;

/**
 * User: steve quick
 * Date: 1/28/12
 * Time: 6:07 PM
 */
public abstract class NapActivity extends JournalActivity
{
    public EditText location;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            case START_TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                startTimeListener, startHour, startMinute, false);
            case END_TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                endTimeListener, endHour, endMinute, false);
            case R.id.report:
                startActivity(new Intent(this,
                        ReportBugActivity.class));
                break;
        }
        return null;
    }

}
