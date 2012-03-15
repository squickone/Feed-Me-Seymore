package com.feedmesomore.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.feedmesomore.model.Settings;

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