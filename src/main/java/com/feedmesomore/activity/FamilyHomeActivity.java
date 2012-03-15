package com.feedmesomore.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.*;
import com.feedmesomore.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.feedmesomore.dao.BabyDao;
import com.feedmesomore.model.Baby;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 12:29 PM
 */
public class FamilyHomeActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_home);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Famil-Home");

        handleButtons();
        
        final Bundle bundle = new Bundle();

        final BabyDao babyDao = new BabyDao(getApplicationContext());
        Baby[] myList = babyDao.getAllBabiesAsStringArray();

        TableLayout tl = (TableLayout) findViewById(R.id.myTableLayout);

        int j = 0;
        while (j < myList.length) {

            final Baby baby = myList[j];

            TableRow tr = new TableRow(this);  //create new row
            tr.setLayoutParams(new TableRow.LayoutParams(    //set params of row
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.FILL_PARENT));

            final Button button = new Button(this);                    //create button for child
            tr.setPadding(5, 5, 5, 5);

            if (baby.getSex().equals("Male")) {     //change bg color of row and button
                tr.setBackgroundColor(0xFF7ED0FF);
                button.setBackgroundColor(0xFF7ED0FF);
            } else {
                tr.setBackgroundColor(0xFFFF99CC);
                button.setBackgroundColor(0xFFFF99CC);
            }

            button.setText(baby.getName());                     //put child's name on button

            button.setLayoutParams(new TableRow.LayoutParams(        //set params of button
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.FILL_PARENT));

            final ImageView babyImage = new ImageView(this);

            if (baby.getPicturePath() != null && !baby.getPicturePath().equals("")) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 12;
                Bitmap bmImg = BitmapFactory.decodeFile(baby.getPicturePath(), options);

                try {
                    babyImage.setImageBitmap(getResizedBitmap(bmImg, 75, 75, 90));

                } catch (Exception e){
                    Log.d("FamilyHomeActivity", "Could not parse the picture taken by the user");
                    babyImage.setImageResource(R.drawable.babyicon);
                    babyImage.setMaxWidth(300);
                    babyImage.setMaxHeight(300);
                    babyImage.setMinimumWidth(150);
                    babyImage.setMinimumHeight(150);
                }
            }

            //button listener for each baby
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ViewBabyActivity.class);
                    bundle.putSerializable("baby", baby);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 3);
                }
            });

            /* Add Button to row. */
            tr.addView(button);

            /* Add row to TableLayout. */
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            j++; //iterator
        }
    }

    public void handleButtons() {

        //Add Baby Button
        Button addChild = (Button) findViewById(R.id.addChild);
        addChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddChildActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        //Add Settings Button
        Button addSettingsButton = (Button) findViewById(R.id.settings);
        addSettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }
}