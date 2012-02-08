package com.feedme.activity;

import android.app.Activity;
import android.os.Bundle;
import com.feedme.R;
import com.feedme.model.Diaper;

/**
 * User: dayelostraco
 * Date: 2/4/12
 * Time: 1:35 PM
 */
public class EditDiaperActivity extends DiaperActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diaper);

        final Diaper diaper = (Diaper) getIntent().getSerializableExtra("diaper");
        System.out.println("diaper = " + diaper);
    }
}