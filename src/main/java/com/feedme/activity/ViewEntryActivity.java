package com.feedme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.feedme.R;
import com.feedme.dao.BabyDao;
import com.feedme.dao.JournalDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;

/**
 * User: whitney.powell
 * Date: 1/17/12
 * Time: 11:23 AM
 */
public class ViewEntryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_baby);

        final JournalDao journalDao = new JournalDao(getApplicationContext());
        
        String entryDateStr = "";
        if(getIntent().getExtras()!=null && getIntent().getExtras().getString("entryDate")!=null){
            entryDateStr = getIntent().getExtras().getString("entryDate");
        }

        Journal entry = null;
        if(!entryDateStr.equals("")){
            entry = journalDao.getEntryByDate(entryDateStr);
        }
        
        // Populate Baby Data
        if(entry!=null){
            final TextView entryDate = (TextView) findViewById(R.id.entryDate);
            entryDate.setText(entry.getDate());
            final TextView entryTime = (TextView) findViewById(R.id.entryTime);
            entryTime.setText(entry.getTime());
            final TextView entryMinLeft = (TextView) findViewById(R.id.entryMinLeft);
            entryMinLeft.setText(entry.getMinLeft());
            final TextView entryMinRight = (TextView) findViewById(R.id.entryMinRight);
            entryMinRight.setText(entry.getMinRight());
            final TextView entryOunces = (TextView) findViewById(R.id.entryOunces);
            entryOunces.setText(entry.getOunces());
            final TextView entryChild = (TextView) findViewById(R.id.entryChild);
            entryChild.setText(entry.getOunces());
        }
    }
}