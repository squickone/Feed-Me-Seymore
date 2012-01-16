package com.feedme.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.feedme.R;
import com.feedme.dao.BabyDao;

/**
 * User: dayel.ostraco
 * Date: 1/16/12
 * Time: 11:29 AM
 */
public class SelectChildListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BabyDao babyDao = new BabyDao(getApplicationContext());

        setListAdapter(new ArrayAdapter<String>(this, R.layout.select_child_list_item, babyDao.getAllBabiesAsStringArray()));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), ViewBabyActivity.class);
                intent.putExtra("babyName", ((TextView) view).getText().toString());
                startActivityForResult(intent, 3);
            }
        });
    }
}