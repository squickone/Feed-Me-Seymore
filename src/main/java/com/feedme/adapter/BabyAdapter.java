package com.feedme.adapter;

import com.feedme.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feedme.model.Baby;

import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 5:14 PM
 */
public class BabyAdapter extends ArrayAdapter {

    int resource;
    String response;
    Context context;

    public BabyAdapter(Context context, int resource, List<Baby> babies) {
        super(context, resource, babies);
        this.resource=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout babyView;
        //Get the current alert object
        Baby baby = (Baby) getItem(position);

        //Inflate the view
        if(convertView==null)
        {
            babyView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, babyView, true);
        }
        else
        {
            babyView = (LinearLayout) convertView;
        }

        //Get the text boxes from the select_child_list_item.xml file
        TextView babyName = (TextView) babyView.findViewById(R.id.babyItem);
        TextView babyId = (TextView) babyView.findViewById(R.id.babyPk);

        //Assign the appropriate data from our baby object above
        babyName.setText(baby.getName());
//        babyId.setText(baby.getID()); //TODO: Figure out why this is not working.

        return babyView;
    }
}