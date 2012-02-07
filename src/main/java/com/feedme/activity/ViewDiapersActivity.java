package com.feedme.activity;

import android.app.Activity;
import android.os.Bundle;
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

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        if(baby!=null){
            styleActivity(baby.getSex());
        }
        
        final DiaperDao diaperDao = new DiaperDao(getApplicationContext());
        List<Diaper> allDiapers = diaperDao.getLastDiapersByChild(baby.getId(), 10);

    }
}