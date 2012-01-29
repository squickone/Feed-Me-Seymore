package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.feedme.R;
import com.feedme.dao.JournalDao;
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
        setContentView(R.layout.view_entry);

        final JournalDao journalDao = new JournalDao(getApplicationContext());
        
        String entryId = "";
        if(getIntent().getExtras()!=null && getIntent().getExtras().getString("entryDate")!=null){
            entryId = getIntent().getExtras().getString("entryDate");
        }

        Journal entry = null;
        if(!entryId.equals("")){
            entry = journalDao.getEntryByDate(entryId);
        }
        final Journal entry2 = entry;

        TextView viewEntry = (TextView) findViewById(R.id.addEntry);
        viewEntry.setText(entry.getDate());

        // Populate Baby Data
        if(entry!=null){
            final EditText entryDate = (EditText) findViewById(R.id.entryDate);
            entryDate.setText(entry.getDate());
            final EditText entryTime = (EditText) findViewById(R.id.entryStartTime);
            entryTime.setText(entry.getStartTime());
            final EditText entryEndTime = (EditText) findViewById(R.id.entryEndTime);
            entryEndTime.setText(entry.getEndTime());

            if (entry.getSide()!=null) {
                final EditText entrySide = (EditText) findViewById(R.id.entrySide);
                entrySide.setText(entry.getSide());
            }
            if (entry.getOunces()!=null) {
                final EditText entryOunces = (EditText) findViewById(R.id.entryOunces);
                entryOunces.setText(entry.getOunces());
            }

        }

        //Select Edit Button
        Button editEntry = (Button) findViewById(R.id.editEntry);
        editEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditBottleFeedActivity.class);

                intent.putExtra("entryID", entry2.getId());
                intent.putExtra("entryDate", entry2.getDate());
                intent.putExtra("entryStartTime", entry2.getStartTime());
                intent.putExtra("entryEndTime", entry2.getEndTime());
                intent.putExtra("entrySide", entry2.getSide());
                intent.putExtra("entryOunces", entry2.getOunces());
                intent.putExtra("entryChild", entry2.getChild());

                startActivityForResult(intent, 3);
            }
        });
    }
}