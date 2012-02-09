package com.feedme.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.*;
import com.feedme.model.Baby;
import com.feedme.model.BaseObject;
import com.feedme.ui.JournalTable;
import com.feedme.util.BaseObjectComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:29 PM
 */
public class ViewEntriesActivity extends BaseActivity {

    private static final SimpleDateFormat ANDROID_FORMAT = new SimpleDateFormat("M-d-yyyy ");
    private static final SimpleDateFormat HEADER_FORMAT = new SimpleDateFormat("MMMMMMMMM, d yyyy");
    
    private JournalTable journalTable = new JournalTable();

    public static final int VIEW_ENTRIES_ACTIVITY_ID = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_home);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/View-Entries");

        Calendar calendar = Calendar.getInstance();
        if(getIntent().getExtras()!=null && getIntent().getExtras().get("journalDate")!=null){
            calendar = (Calendar) getIntent().getExtras().get("journalDate");
        }
        
        TextView journalHeader = (TextView) findViewById(R.id.journalHeader);
        journalHeader.setText(HEADER_FORMAT.format(calendar.getTime()));

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);
        
        styleActivity(baby.getSex());
        handleButtons(calendar, bundle);

        List<BaseObject> history = getHistory(baby, ANDROID_FORMAT.format(calendar.getTime()),getApplicationContext());
        TableLayout tableLayout = (TableLayout) findViewById(R.id.myTableLayout);

        if (history.size() > 0) {
            journalTable.buildRows(this, history, baby, tableLayout);
        }

    }

    public void handleButtons(final Calendar calendar, final Bundle bundle) {
        Button childButton = (Button) findViewById(R.id.childScreen);
        childButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_ENTRIES_ACTIVITY_ID);
            }
        });

        Button previousButton = (Button) findViewById(R.id.previousDay);
        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                calendar.add(Calendar.DAY_OF_YEAR, -1);
                Intent intent = new Intent(v.getContext(), ViewEntriesActivity.class);
                bundle.putSerializable("journalDate", calendar);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_ENTRIES_ACTIVITY_ID);
            }
        });

        Button nextButton = (Button) findViewById(R.id.nextDay);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Intent intent = new Intent(v.getContext(), ViewEntriesActivity.class);
                bundle.putSerializable("journalDate", calendar);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_ENTRIES_ACTIVITY_ID);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

    /**
     * Returns a List containing Diapers, Naps and Feedings sorted by Date and then StartTime.
     *
     * @param baby
     * @param date
     * @param context
     * @return
     */
    private List<BaseObject> getHistory(Baby baby, String date, Context context) {

        JournalDao journalDao = new JournalDao(context);
        DiaperDao diaperDao = new DiaperDao(context);
        NapDao napDao = new NapDao(context);

        List<BaseObject> history = new ArrayList<BaseObject>();
        history.addAll(journalDao.getEntriesForChildByDate(baby.getId(), date));
        history.addAll(diaperDao.getDiapersForChildByDate(baby.getId(), date));
        history.addAll(napDao.getNapsForChildByDate(baby.getId(), date));

        Collections.sort(history, new BaseObjectComparator());
        Collections.reverse(history);

        return history;
    }
}