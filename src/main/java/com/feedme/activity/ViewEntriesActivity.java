package com.feedme.activity;

import android.app.Activity;
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
import com.feedme.dao.JournalDao;
import com.feedme.dao.SettingsDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;
import com.feedme.model.Settings;
import com.feedme.ui.JournalTable;

import java.util.List;


/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:29 PM
 */
public class ViewEntriesActivity extends BaseActivity
{
    private JournalTable journalTable = new JournalTable();
    
    public static final int VIEW_ENTRIES_ACTIVITY_ID = 50;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_home);

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        styleActivity(baby.getSex());
        
        handleButtons(baby.getID());

        final JournalDao journalDao = new JournalDao(getApplicationContext());
        List<Journal> lsJournal = journalDao.getLastFeedingsByChild(baby.getID(), 10);

        // prepare settings data
        final SettingsDao settingsDao = new SettingsDao(getApplicationContext());
        Settings setting = settingsDao.getSetting(1);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.myTableLayout);

        if (lsJournal.size() > 0)
        {
            journalTable.buildRows(this, lsJournal, baby, tableLayout);
        }

    }

    public void handleButtons(final int babyId)
    {
        Button childButton = (Button) findViewById(R.id.childScreen);
        childButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final BabyDao babyDao = new BabyDao(getApplicationContext());

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtra("babyName", babyDao.getBaby(babyId).getName());
                startActivityForResult(intent, VIEW_ENTRIES_ACTIVITY_ID);
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
                startActivity(new Intent(ViewEntriesActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(ViewEntriesActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(ViewEntriesActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

}