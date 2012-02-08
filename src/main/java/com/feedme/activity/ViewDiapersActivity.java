package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    final Bundle bundle = new Bundle();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Baby Data
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        bundle.putSerializable("baby", baby);

        //Layout
        setContentView(R.layout.diaper_home);
        styleActivity(baby.getSex());

        //Buttons
        handleButtons(bundle);
        
        final DiaperDao diaperDao = new DiaperDao(getApplicationContext());
        List<Diaper> allDiapers = diaperDao.getLastDiapersByChild(baby.getId(), 10);

        //TODO: Populate Diaper Table
    }

    public void handleButtons(final Bundle bundle)
    {
        Button childButton = (Button) findViewById(R.id.diaperToChildScreen);
        childButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
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