package com.feedmesomore.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * User: steve quick
 * Date: 1/28/12
 * Time: 11:41 PM
 */
public abstract class JournalActivity extends BaseActivity
{
    public Button entryDate;
    public Button startTime;
    public Button endTime;

    public int mYear;
    public int mMonth;
    public int mDay;

    public int startHour;
    public int startMinute;
    public int startSecond;

    public int endHour;
    public int endMinute;
    public int endSecond;

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
        }
        return null;
    }

    public void setupCalendar()
    {
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
    }

    // the callback received when the user "sets" the date in the dialog
    public DatePickerDialog.OnDateSetListener mDateSetListener =
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
    public TimePickerDialog.OnTimeSetListener startTimeListener =
            new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                {
                    startHour = hourOfDay;
                    startMinute = minute;
                    updateStartDisplay();
                }
            };

    // the callback received when the user "sets" the time in the dialog
    public TimePickerDialog.OnTimeSetListener endTimeListener =
            new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                {
                    endHour = hourOfDay;
                    endMinute = minute;
                    updateEndDisplay();
                }
            };

    public View.OnClickListener showDateDialog()
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        };
    }

    public View.OnClickListener showStartTimeDialog()
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(START_TIME_DIALOG_ID);
            }
        };
    }

    public View.OnClickListener showEndTimeDialog()
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(END_TIME_DIALOG_ID);
            }
        };
    }

    // updates the date we display in the TextView
    public void updateDateDisplay()
    {
        entryDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear));
    }

    // add leading zero to times
    public static String pad(int c)
    {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    // updates the date we display in the TextView
    public void updateStartDisplay()
    {
        startTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pad(startHour)).append(":")
                        .append(pad(startMinute)).append(":")
                        .append(pad(startSecond)));
    }

    // updates the date we display in the TextView
    public void updateEndDisplay()
    {
        endTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pad(endHour)).append(":")
                        .append(pad(endMinute)).append(":")
                        .append(pad(endSecond)));
    }

}
