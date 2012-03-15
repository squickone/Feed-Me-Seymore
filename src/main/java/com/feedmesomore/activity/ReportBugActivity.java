package com.feedmesomore.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedmesomore.R;

import android.os.Build;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:27 PM
 */
public class ReportBugActivity extends BaseActivity
{

    private Button reportBug;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_bug);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Report-Bug");

        final EditText bugName = (EditText) findViewById(R.id.bugName);
        final EditText bugEmail = (EditText) findViewById(R.id.bugName);
        final EditText bugDevice = (EditText) findViewById(R.id.bugDevice);
        final EditText bugVersion = (EditText) findViewById(R.id.bugVersion);
        final EditText bugDesc = (EditText) findViewById(R.id.bugDesc);

        bugDevice.setText(Build.CPU_ABI);

        reportBug = (Button) findViewById(R.id.sendReportButton);

        //declare alert dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        //Send report Button
        reportBug.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                //if name, weight, and height aren't filled out, throw an alert
                if (bugName.getText().toString().equals("") ||
                        bugEmail.getText().toString().equals("") ||
                        bugDevice.getText().toString().equals("") ||
                        bugVersion.getText().toString().equals("") ||
                        bugDesc.getText().toString().equals("")
                        ) {
                    alertDialog.setTitle("Oops!");
                    alertDialog.setMessage("Please fill out the form completely.");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {      // else add child to database

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"whitney.ellis.powell@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "FeedMe Bug Report - " + bugName.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT, "User name: " + bugName.getText().toString() + "\r\n" +
                            "\r\nUser email: " + bugEmail.getText().toString() + "\r\n" +
                            "\r\nDevice: " + bugDevice.getText().toString() + "\r\n" +
                            "\r\nAndroid version: " + bugVersion.getText().toString() + "\r\n" +
                            "\r\nBug Description: " + bugDesc.getText().toString());
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ReportBugActivity.this, "There are no email clients installed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                startActivity(new Intent(ReportBugActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(ReportBugActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(ReportBugActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

}
