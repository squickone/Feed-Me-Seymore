package com.feedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feedme.R;
import com.feedme.model.Journal;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 5:14 PM
 */
public class JournalAdapter extends ArrayAdapter {

    int resource;
    String response;
    Context context;

    public JournalAdapter(Context context, int resource, List<Journal> babies) {
        super(context, resource, babies);
        this.resource=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout entryView;
        //Get the current alert object
        Journal entry = (Journal) getItem(position);

        //Inflate the view
        if(convertView==null)
        {
            entryView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, entryView, true);
        }
        else
        {
            entryView = (LinearLayout) convertView;
        }

        //Get the text boxes from the select_entry_list_item.xml file
        TextView entryDate = (TextView) entryView.findViewById(R.id.entryDate);
        TextView entryId = (TextView) entryView.findViewById(R.id.entryId);

        //Assign the appropriate data from our journal object above
        entryDate.setText(entry.getDate());
//        entryId.setText(entry.getID()); //TODO: Figure out why this is not working.

        return entryView;
    }
}