package com.feedmesomore.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.feedmesomore.R;
import com.feedmesomore.adapter.JournalAdapter;
import com.feedmesomore.dao.JournalDao;

/**
 * User: whitney powell
 * Date: 1/17/12
 * Time: 11:22 AM
 */
public class SelectEntryListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final JournalDao journalDao = new JournalDao(getApplicationContext());

        setListAdapter(new JournalAdapter(getApplicationContext(), R.layout.select_entry_list_item, journalDao.getAllEntries()));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView entryDate = (TextView) view.findViewById(R.id.entryDate);
//                TextView entryId = (TextView) view.findViewById(R.id.entryId);

                Toast.makeText(getApplicationContext(), entryDate.getText(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), ViewBabyActivity.class);
                intent.putExtra("entryDate", entryDate.getText());
                startActivityForResult(intent, 3);
            }
        });
    }
}