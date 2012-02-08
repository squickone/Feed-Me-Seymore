package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.DiaperDao;
import com.feedme.model.Baby;
import com.feedme.model.Diaper;

import java.util.List;

/**
 * User: dayelostraco
 * Date: 2/4/12
 * Time: 1:36 PM
 */
public class ViewDiapersActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaper_home);

        //Style
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        styleActivity(baby.getSex());

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);

        //Buttons
        handleButtons(bundle);

        //Get last X diapers for Baby
        final DiaperDao diaperDao = new DiaperDao(getApplicationContext());
        List<Diaper> allDiapers = diaperDao.getLastDiapersByChild(baby.getId(), 10);

        //Populate Table Layout with Diapers
        TableLayout diaperTable = (TableLayout) findViewById(R.id.diaperTableLayout);
        if (allDiapers.size() > 0) {
            int j = 0;
            for (Diaper diaper : allDiapers) {
                bundle.putSerializable("diaper", diaper);

                TableRow tableRow = new TableRow(this);  //create new row

                if (j == 0) {
                    if (baby.getSex().equals("Male")) {
                        tableRow.setBackgroundColor(0xFFD2EDFC);
                    } else {
                        tableRow.setBackgroundColor(0xFFFCD2d2);
                    }
                } else {
                    tableRow.setBackgroundColor(0xFFFFFFFF);
                }
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(layoutParams);
                tableRow.setClickable(true);
                tableRow.setPadding(5, 5, 5, 5);

                LinearLayout linearLayoutHorizontal = new LinearLayout(this);

                ImageView imageView = new ImageView(this);
                imageView.setMinimumHeight(60);
                imageView.setMinimumWidth(60);
                imageView.setBackgroundResource(R.drawable.icon_border);
                imageView.setImageResource(R.drawable.icon_bug);

                linearLayoutHorizontal.addView(imageView);

                LinearLayout linearLayoutVertical = new LinearLayout(this);

                linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);

                linearLayoutVertical.setPadding(5, 0, 0, 0);

                //print diaper location
                TextView diaperType = new TextView(this);
                diaperType.setTextColor(0xFF000000);
                diaperType.setText("Type - " + diaper.getType());
                linearLayoutVertical.addView(diaperType);

                //print diaper date
                TextView diaperDate = new TextView(this);
                diaperDate.setTextColor(0xFF000000);
                diaperDate.setText(diaper.getDate());
                linearLayoutVertical.addView(diaperDate);

                //print diaper time
                TextView diaperTime = new TextView(this);
                diaperTime.setTextColor(0xFF000000);
                diaperTime.setText(diaper.getTime() + " - " + diaper.getTime());
                linearLayoutVertical.addView(diaperTime);

                linearLayoutHorizontal.addView(linearLayoutVertical);

                tableRow.addView(linearLayoutHorizontal);

                //TODO : Same Bundle is added to the Intent for each Table Row. Fix this.
                tableRow.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), EditDiaperActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, VIEW_DIAPER_ACTIVITY_ID);
                    }
                });

                /* Add row to TableLayout. */
                diaperTable.addView(tableRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                j++;
                if (j == 2) {
                    j = 0;
                }
            }

        }
    }

    public void handleButtons(final Bundle bundle) {
        Button childButton = (Button) findViewById(R.id.diaperToChildScreen);
        childButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_DIAPER_ACTIVITY_ID);
            }
        });

        Button addDiaperButton = (Button) findViewById(R.id.addDiaperButton);
        addDiaperButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddDiaperActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_DIAPER_ACTIVITY_ID);
            }
        });

    }
}