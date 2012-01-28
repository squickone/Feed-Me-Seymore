package com.feedme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.feedme.dao.NapDao;
import com.feedme.dao.SettingsDao;
import com.feedme.model.Baby;
import com.feedme.model.Journal;
import com.feedme.model.Settings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 4:34 PM
 */
public class ViewBabyActivity extends Activity
{

    public SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_baby);

        final BabyDao babyDao = new BabyDao(getApplicationContext());
        final JournalDao journalDao = new JournalDao(getApplicationContext());

        Baby tempBaby;

        if(getIntent().getSerializableExtra("baby") != null)
        {
            tempBaby = (Baby) getIntent().getSerializableExtra("baby");
        }
        else
        {
            tempBaby = babyDao.getBabyByName(getIntent().getExtras().getString("babyName"));
        }
        final Baby baby = tempBaby;

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        //Get today's feedings
        SimpleDateFormat sdf = new SimpleDateFormat("M-dd-yyyy ");
        Calendar today = Calendar.getInstance();
        int feedingCount = journalDao.getEntriesCountByBabyAndDate(new Integer(baby.getID()).toString(),
                sdf.format(today.getTime()));
        TextView todayFeedingCount = (TextView) findViewById(R.id.todayFeedings);
        String feedingCountStr = feedingCount == 0 ? "No Feeding's Today" : feedingCount + "";
        todayFeedingCount.setText(feedingCountStr);

        List<Journal> lsJournal = journalDao.getLastFeedingsByChild(baby.getID(), 5);

        TableLayout tl = (TableLayout) findViewById(R.id.myTableLayout);

        // prepare settings data
        final SettingsDao settingsDao = new SettingsDao(getApplicationContext());
        Settings setting = settingsDao.getSetting(1);

        //populate journal data
        int j = 0;
        for (Journal journal : lsJournal) {

            TableRow tr1 = new TableRow(this);  //create new row

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

            LinearLayout linearLayoutHorizontal = new LinearLayout(this);

            ImageView imageView = new ImageView(this);
            imageView.setMinimumHeight(60);
            imageView.setMinimumWidth(60);
            imageView.setBackgroundResource(R.drawable.icon_border);

            if (journal.getSide().trim().isEmpty()) {
                imageView.setImageResource(R.drawable.icon_bottle);
            } else {
                imageView.setImageResource(R.drawable.icon_breastfeed);
            }

            linearLayoutHorizontal.addView(imageView);

            LinearLayout linearLayoutVertical = new LinearLayout(this);

            linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);

            linearLayoutVertical.setPadding(5, 0, 0, 0);

            TextView bottleBreast = new TextView(this);
            bottleBreast.setTextColor(0xFF000000);
            final String side = journal.getSide();
            if (side.trim().isEmpty()) {
                bottleBreast.setText("Bottle");
            } else {
                bottleBreast.setText("Breastfeeding - " + journal.getSide());
            }

            linearLayoutVertical.addView(bottleBreast);

            TextView feedDate = new TextView(this);
            feedDate.setTextColor(0xFF000000);
            feedDate.setText(journal.getDate());

            linearLayoutVertical.addView(feedDate);

            if (!journal.getOunces().isEmpty())
            {
                TextView feedAmt = new TextView(this);
                feedAmt.setTextColor(0xFF000000);

                if (side.trim().isEmpty()) {
                    feedAmt.setText(journal.getOunces() + " " + setting.getLiquid());
                } else {
                    feedAmt.setText(journal.getOunces());
                }

                linearLayoutVertical.addView(feedAmt);
            }

            if (!journal.getFeedTime().isEmpty())
            {
                TextView feedTime = new TextView(this);
                feedTime.setTextColor(0xFF000000);
                
//                Date dateDuration = new Date(Long.valueOf());
                feedTime.setText(journal.getFeedTime());
            }

            TextView babyDiaper = new TextView(this);
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
                    startActivityForResult(intent, 3);
                }
            });

            /* Add row to TableLayout. */
            tl.addView(tr1, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            j++;
            if (j == 2) {
                j = 0;
            }
        }

        // Populate Baby Data
        if (baby != null) {
            final TextView tBabyName = (TextView) findViewById(R.id.babyName);
            tBabyName.setText(baby.getName());

            final TextView babySex = (TextView) findViewById(R.id.babySex);
            babySex.setText(baby.getSex());

            final RelativeLayout topBanner = (RelativeLayout) findViewById(R.id.topBanner);
            final RelativeLayout bottomBanner = (RelativeLayout) findViewById(R.id.bottomBanner);
            final TextView babyFeedings = (TextView) findViewById(R.id.babyFeedings);
            final TextView feedHistory = (TextView) findViewById(R.id.feedHistory);

            if ((baby.getSex()).equals("Male")) {
                topBanner.setBackgroundColor(0xFF7ED0FF);
                bottomBanner.setBackgroundColor(0xFF7ED0FF);
                babyFeedings.setBackgroundColor(0xFF9090FC);
                feedHistory.setBackgroundColor(0xFF9090FC);
            } else {
                topBanner.setBackgroundColor(0xFFFF99CC);
                bottomBanner.setBackgroundColor(0xFFFF99CC);
                babyFeedings.setBackgroundColor(0xFFFC9090);
                feedHistory.setBackgroundColor(0xFFFC9090);
            }

            final TextView babyHeight = (TextView) findViewById(R.id.babyHeight);
            babyHeight.setText(baby.getHeight());

            final TextView babyWeight = (TextView) findViewById(R.id.babyWeight);
            babyWeight.setText(baby.getWeight());

            final TextView babyDob = (TextView) findViewById(R.id.babyDob);
            babyDob.setText(baby.getDob());

            //populate units of measurement
            final TextView heightMeas = (TextView) findViewById(R.id.heightMeas);
            final TextView weightMeas = (TextView) findViewById(R.id.weightMeas);
            heightMeas.setText(" " + setting.getLength());
            weightMeas.setText(" " + setting.getSettingsWeight());

            final ImageView babyImage = (ImageView) findViewById(R.id.babyPicture);
            if (baby.getPicturePath() != null && !baby.getPicturePath().equals("")) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 12;
                Bitmap bmImg = BitmapFactory.decodeFile(baby.getPicturePath(), options);
                babyImage.setImageBitmap(getResizedBitmap(bmImg, 75, 75, 90));
                babyImage.setMaxWidth(100);
                babyImage.setMaxHeight(100);
                babyImage.setMinimumWidth(100);
                babyImage.setMinimumHeight(100);
            } else {
                babyImage.setImageResource(R.drawable.babyicon);
                babyImage.setMaxWidth(100);
                babyImage.setMaxHeight(100);
                babyImage.setMinimumWidth(100);
                babyImage.setMinimumHeight(100);
            }
        }

        //Add Edit Button`
        Button editButton = (Button) findViewById(R.id.editBaby);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), EditChildActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

        //Add Delete Button`
        Button deleteButton = (Button) findViewById(R.id.deleteBaby);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                deleteBaby(baby.getID(), baby.getName());
            }
        });

        //Add Family Button`
        Button familyButton = (Button) findViewById(R.id.familyButton);
        familyButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(ViewBabyActivity.this,
                        HomeActivity.class));
            }
        });

        //Add Nap Button`
        Button napButton = (Button) findViewById(R.id.naps_Button);
        napButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), ViewNapsActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

        //Journal Button`
        Button journalButton = (Button) findViewById(R.id.journalButton);
        journalButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewBabyActivity.this, ViewEntriesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Add Bottle Feeding Button`
        Button bottleButton = (Button) findViewById(R.id.bottleButton);
        bottleButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewBabyActivity.this, AddBottleFeedActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Add Breast Feeding Button`
        Button breastfeedButton = (Button) findViewById(R.id.breastfeedButton);
        breastfeedButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewBabyActivity.this, AddBreastFeedActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
                startActivity(new Intent(ViewBabyActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(ViewBabyActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(ViewBabyActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

    /**
     * Resizes a Bitmap based on the passed in newHeight and newWidth and rotates the image by rotateInDegrees.
     *
     * @param bitMap
     * @param newHeight
     * @param newWidth
     * @param rotateInDegrees
     * @return
     */
    private Bitmap getResizedBitmap(Bitmap bitMap, int newHeight, int newWidth, int rotateInDegrees)
    {

        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        //matrix.postRotate(rotateInDegrees);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    private void deleteBaby(final int babyID, final String babyName)
    {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(ViewBabyActivity.this);
        myAlertDialog.setTitle("Delete \"" + babyName + "\"?");
        myAlertDialog.setMessage("Are you sure?");
        myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface arg0, int arg1)
            {
                BabyDao babyDao = new BabyDao(getApplicationContext());
                Baby baby = babyDao.getBaby(babyID);
                babyDao.deleteBaby(baby, babyID);
                JournalDao journalDao = new JournalDao(getApplicationContext());
                journalDao.deleteEntry(babyID);
                NapDao napDao = new NapDao(getApplicationContext());
                napDao.deleteNap(babyID);
                startActivity(new Intent(ViewBabyActivity.this,
                        HomeActivity.class));
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface arg0, int arg1)
            {

            }
        });
        myAlertDialog.show();

    }
}