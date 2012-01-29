package com.feedme.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.activity.EditBottleFeedActivity;
import com.feedme.activity.EditBreastFeedActivity;
import com.feedme.dao.SettingsDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;
import com.feedme.model.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: steve quick
 * Date: 1/28/12
 * Time: 1:19 PM
 */
public class JournalTable
{
    private SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

    public void buildRows(final Activity activity, List<Journal> lsJournal, Baby baby, TableLayout tableLayout)
    {
        SettingsDao settingsDao = new SettingsDao(activity.getApplicationContext());
        
        Settings setting = settingsDao.getSetting(1);
        int j = 0;
        for (Journal journal : lsJournal)
        {
            Log.d("BABY:JOURNAL:", journal.dump());

            TableRow tr1 = new TableRow(activity.getApplicationContext());  //create new row

            if (j == 0) {
                if (baby.getSex().equals("Male")) {
                    tr1.setBackgroundColor(0xFFD2EDFC);
                } else {
                    tr1.setBackgroundColor(0xFFFCD2d2);
                }
            } else {
                tr1.setBackgroundColor(0xFFFFFFFF);
            }
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr1.setLayoutParams(layoutParams);
            tr1.setClickable(true);
            tr1.setPadding(5, 5, 5, 5);

            LinearLayout linearLayoutHorizontal = new LinearLayout(activity.getApplicationContext());

            ImageView imageView = new ImageView(activity.getApplicationContext());
            imageView.setMinimumHeight(60);
            imageView.setMinimumWidth(60);
            imageView.setBackgroundResource(R.drawable.icon_border);

            if (journal.getSide().trim().isEmpty()) {
                imageView.setImageResource(R.drawable.icon_bottle);
            } else {
                imageView.setImageResource(R.drawable.icon_breastfeed);
            }

            linearLayoutHorizontal.addView(imageView);

            LinearLayout linearLayoutVertical = new LinearLayout(activity.getApplicationContext());

            linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);

            linearLayoutVertical.setPadding(5, 0, 0, 0);

            TextView bottleBreast = new TextView(activity.getApplicationContext());
            bottleBreast.setTextColor(0xFF000000);

            final String side = journal.getSide();

            if (side.trim().isEmpty()) {
                bottleBreast.setText("Bottle");
            } else {
                bottleBreast.setText("Breastfeeding - " + journal.getSide());
            }

            linearLayoutVertical.addView(bottleBreast);

            TextView feedDate = new TextView(activity.getApplicationContext());
            feedDate.setTextColor(0xFF000000);
            feedDate.setText(journal.getDate());

            linearLayoutVertical.addView(feedDate);

            if (!journal.getOunces().isEmpty())
            {
                TextView feedAmt = new TextView(activity.getApplicationContext());
                feedAmt.setTextColor(0xFF000000);

                if (side.trim().isEmpty()) {
                    feedAmt.setText(journal.getOunces() + " " + setting.getLiquid());
                } else {
                    feedAmt.setText(journal.getOunces());
                }

                linearLayoutVertical.addView(feedAmt);
            }

            Log.d("BABY:JOURNAL:", String.valueOf(journal.getFeedTime().length()));

            if (journal.getFeedTime().trim().length() > 0)
            {
                TextView feedTime = new TextView(activity.getApplicationContext());
                feedTime.setTextColor(0xFF000000);

//                Date dateDuration = new Date(Long.valueOf(journal.getFeedTime()));
//                feedTime.setText(simpleTimeFormat.format(dateDuration));

                feedTime.setText(journal.getFeedTime());
                linearLayoutVertical.addView(feedTime);
            }

            TextView babyDiaper = new TextView(activity.getApplicationContext());
            babyDiaper.setTextColor(0xFF000000);
            babyDiaper.setText(journal.getStartTime() + " - " + journal.getEndTime());

            linearLayoutVertical.addView(babyDiaper);

            linearLayoutHorizontal.addView(linearLayoutVertical);

            tr1.addView(linearLayoutHorizontal);

            final Bundle jBundle = new Bundle();
            jBundle.putSerializable("baby", baby);
            jBundle.putSerializable("journal", journal);

            tr1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    Intent intent;
                    if (side.trim().isEmpty()) {
                        intent = new Intent(v.getContext(), EditBottleFeedActivity.class);
                    } else {
                        intent = new Intent(v.getContext(), EditBreastFeedActivity.class);
                    }
                    intent.putExtras(jBundle);
                    activity.startActivityForResult(intent, 3);
                }
            });

            /* Add row to TableLayout. */
            tableLayout.addView(tr1, new TableLayout.LayoutParams(
                                    TableRow.LayoutParams.FILL_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));

            j++;
            if (j == 2) {
                j = 0;
            }
        }
    }
}
