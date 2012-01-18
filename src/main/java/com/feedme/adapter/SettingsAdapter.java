package com.feedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feedme.R;
import com.feedme.model.Settings;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 5:14 PM
 */
public class SettingsAdapter extends ArrayAdapter {

    int resource;
    String response;
    Context context;

    public SettingsAdapter(Context context, int resource, List<Settings> settings) {
        super(context, resource, settings);
        this.resource=resource;

    }


}