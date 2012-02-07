package com.feedme.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.feedme.ui.JournalTable;
import com.feedme.util.BabyExporter;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 4:34 PM
 */
public class ViewBabyActivity extends Activity
{
    private static final int VIEW_BABY_ACTIVITY_ID = 3;

    private JournalTable journalTable = new JournalTable();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_baby);

        final BabyDao babyDao = new BabyDao(getApplicationContext());
        final JournalDao journalDao = new JournalDao(getApplicationContext());

        Baby tempBaby;

        if (getIntent().getSerializableExtra("baby") != null) {
            tempBaby = (Baby) getIntent().getSerializableExtra("baby");
        } else {
            tempBaby = babyDao.getBabyByName(getIntent().getExtras().getString("babyName"));
        }
        final Baby baby = tempBaby;

        Log.d("BABY:", baby.dump());

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        //Get today's feedings
        SimpleDateFormat sdf = new SimpleDateFormat("M-dd-yyyy ");
        Calendar today = Calendar.getInstance();
        int feedingCount = journalDao.getEntriesCountByBabyAndDate(Integer.toString(baby.getId()),
                sdf.format(today.getTime()));
        TextView todayFeedingCount = (TextView) findViewById(R.id.todayFeedings);
        String feedingCountStr = feedingCount == 0 ? "No Feeding's Today" : feedingCount + "";
        todayFeedingCount.setText(feedingCountStr);

        List<Journal> lsJournal = journalDao.getLastFeedingsByChild(baby.getId(), 5);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.myTableLayout);

        // prepare settings data
        final SettingsDao settingsDao = new SettingsDao(getApplicationContext());
        Settings setting = settingsDao.getSetting(1);

        //populate journal data
        journalTable.buildRows(this, lsJournal, baby, tableLayout);

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
                babyFeedings.setBackgroundColor(0xFF7ED0FF);
                feedHistory.setBackgroundColor(0xFF7ED0FF);
            } else {
                topBanner.setBackgroundColor(0xFFFF99CC);
                bottomBanner.setBackgroundColor(0xFFFF99CC);
                babyFeedings.setBackgroundColor(0xFFFF99CC);
                feedHistory.setBackgroundColor(0xFFFF99CC);
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
                startActivityForResult(intent, VIEW_BABY_ACTIVITY_ID);
            }
        });

        //Export Button
        Button exportButton = (Button) findViewById(R.id.exportBaby);
        exportButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                //Run Exporter and save the Xls file
                BabyExporter.exportBabyToXls(v.getContext(), baby);
                emailExport(v.getContext(), baby.getName());
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

        //Add Feeding Button`
        Button feedButton = (Button) findViewById(R.id.feedingTypeButton);
        feedButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewBabyActivity.this, FeedTypeActivity.class);
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

    /**
     * Creates an email with a file attachment. The attached file is the babyName.xls.
     *
     * @param context
     * @param babyName
     */
    private void emailExport(Context context, String babyName)
    {

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.exportEmailSubject) + " " +
                babyName);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.exportEmailText));

        Uri uri = Uri.fromFile(new File(context.getExternalFilesDir(null), babyName + ".xls"));
        emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);

        context.startActivity(Intent.createChooser(emailIntent, "Export to Email..."));
    }
}