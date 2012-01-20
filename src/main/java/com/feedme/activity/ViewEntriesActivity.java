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
import com.feedme.model.Baby;
import com.feedme.model.Journal;

import java.util.IllegalFormatFlagsException;
import java.util.List;


/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:29 PM
 */
public class ViewEntriesActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_home);

        Bundle bundle = getIntent().getExtras();
        final int babyId = bundle.getInt("babyId");

        final JournalDao journalDao = new JournalDao(getApplicationContext());
        List<Journal> lsJournal = journalDao.getLastFeedingsByChild(babyId, 10);

        Log.d("BABYID ENTRIES:", String.valueOf(babyId));
        
        TableLayout tl = (TableLayout) findViewById(R.id.myTableLayout);

        int j = 0;
        for (Journal journal : lsJournal)
        {
            final String entryDate = journal.getDate();
            TableRow tr1 = new TableRow(this);  //create new row

            if (j == 0)
            {
                tr1.setBackgroundColor(0xFFD2EDFC);
            }
            else
            {
                tr1.setBackgroundColor(0xFFFFFFFF);
            }
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr1.setLayoutParams(layoutParams);
            tr1.setClickable(true);
            tr1.setPadding(5, 5, 5, 5);

            LinearLayout linearLayoutHorizontal = new LinearLayout(this);

            ImageView imageView = new ImageView(this);
            imageView.setMinimumHeight(60);
            imageView.setMinimumWidth(60);
            imageView.setBackgroundResource(R.drawable.icon_border);

            if (journal.getSide().trim().isEmpty())
            {
                imageView.setImageResource(R.drawable.icon_bottle);
            }
            else
            {
                imageView.setImageResource(R.drawable.icon_breastfeed);
            }

            linearLayoutHorizontal.addView(imageView);

            LinearLayout linearLayoutVertical = new LinearLayout(this);

            linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);

            linearLayoutVertical.setPadding(10,0,0,0);

            TextView bottleBreast = new TextView(this);
            bottleBreast.setTextColor(0xFF000000);
            if (journal.getSide().trim().isEmpty())
            {
                bottleBreast.setText("Bottle");
            }
            else
            {
                bottleBreast.setText("Breastfeeding - " + journal.getSide());
            }

            linearLayoutVertical.addView(bottleBreast);

//            TextView entryId = new TextView(this);
//            entryId.setTextColor(0xFF000000);
//            entryId.setText("Entry ID: " + journal.getID());
//
//            linearLayoutVertical.addView(entryId);

            TextView feedAmount = new TextView(this);
            feedAmount.setTextColor(0xFF000000);
            feedAmount.setText(journal.getDate());

            linearLayoutVertical.addView(feedAmount);

            TextView babyWake = new TextView(this);
            babyWake.setTextColor(0xFF000000);
            babyWake.setText(journal.getOunces());

            linearLayoutVertical.addView(babyWake);

            TextView babyDiaper = new TextView(this);
            babyDiaper.setTextColor(0xFF000000);
            babyDiaper.setText(journal.getStartTime() + " - " + journal.getEndTime());

            linearLayoutVertical.addView(babyDiaper);

            linearLayoutHorizontal.addView(linearLayoutVertical);

            tr1.addView(linearLayoutHorizontal);

            tr1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), ViewEntryActivity.class);
                    intent.putExtra("entryDate", entryDate);
                    startActivityForResult(intent, 3);
                }
            });

            final BabyDao babyDao = new BabyDao(getApplicationContext());
            Baby baby = babyDao.getBaby(journal.getChild());

            /* Add row to TableLayout. */
            tl.addView(tr1, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            j++;
            if (j == 2)
            {
                j = 0;
            }
        }

    }

    public void handleButtons()
    {

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
            case R.id.settings:
                startActivity(new Intent(ViewEntriesActivity.this,
                        SettingsActivity.class));
                break;
        }
        return true;
    }

}