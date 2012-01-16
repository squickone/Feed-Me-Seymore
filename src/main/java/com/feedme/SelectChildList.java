package com.feedme;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 11:29 AM
 */
public class SelectChildList extends ListActivity {

    //TODO: Needs to be populated from the DB
    String[] children = {"Sadie", "Baby #2"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.select_child_list_item, children));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();

                //TODO: Link to new Intent
            }
        });
    }
}