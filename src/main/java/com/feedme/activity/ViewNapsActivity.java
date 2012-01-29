package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.NapDao;
import com.feedme.model.Baby;
import com.feedme.model.Nap;

import java.util.List;


/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:29 PM
 */
public class ViewNapsActivity extends BaseActivity
{

    public static final int VIEW_NAPS_ACTIVITY_ID = 50;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naps_home);

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        styleActivity(baby.getSex());

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        handleButtons(bundle);

        final NapDao napsDao = new NapDao(getApplicationContext());
        List<Nap> lsNap = napsDao.getLastNapsByChild(baby.getId(), 10);

        TableLayout tl = (TableLayout) findViewById(R.id.myTableLayout);

        if (lsNap.size() > 0)
        {
            int j = 0;
            for (Nap nap : lsNap)
            {
                bundle.putSerializable("nap", nap);

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
                imageView.setImageResource(R.drawable.icon_nap);

                linearLayoutHorizontal.addView(imageView);

                LinearLayout linearLayoutVertical = new LinearLayout(this);

                linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);

                linearLayoutVertical.setPadding(5, 0, 0, 0);

                //print nap location
                TextView napLocationText = new TextView(this);
                napLocationText.setTextColor(0xFF000000);

                napLocationText.setText("Location/notes - " + nap.getLocation());
                linearLayoutVertical.addView(napLocationText);

                //print nap date
                TextView napDateText = new TextView(this);
                napDateText.setTextColor(0xFF000000);
                napDateText.setText(nap.getDate());
                linearLayoutVertical.addView(napDateText);

                //print nap time
                TextView napTime = new TextView(this);
                napTime.setTextColor(0xFF000000);
                napTime.setText(nap.getStartTime() + " - " + nap.getEndTime());
                linearLayoutVertical.addView(napTime);

                linearLayoutHorizontal.addView(linearLayoutVertical);

                tr1.addView(linearLayoutHorizontal);

                tr1.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(v.getContext(), EditNapActivity.class);
                        intent.putExtras(bundle);
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

        }

    }

    public void handleButtons(final Bundle bundle)
    {
        Button childButton = (Button) findViewById(R.id.childScreen);
        childButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_NAPS_ACTIVITY_ID);
            }
        });

        Button addNapButton = (Button) findViewById(R.id.addNapButton);
        addNapButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), AddNapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_NAPS_ACTIVITY_ID);
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
                startActivity(new Intent(ViewNapsActivity.this,
                        HomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(ViewNapsActivity.this,
                        SettingsActivity.class));
                break;
            case R.id.report:
                startActivity(new Intent(ViewNapsActivity.this,
                        ReportBugActivity.class));
                break;
        }
        return true;
    }

}