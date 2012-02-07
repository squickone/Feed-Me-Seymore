package com.feedme.activity;

import android.content.Intent;
import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.feedme.model.Baby;

/**
 * User: steve quick
 * Date: 2/6/12
 * Time: 9:59 PM
 */
public class FeedTypeActivity extends ListActivity
{
    final Bundle bundle = new Bundle();

    public void onCreate(Bundle feedBundle)
    {
		super.onCreate(feedBundle);

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        bundle.putSerializable("baby", baby);

		String[] values = new String[] { "Bottle Feeding", "Breast Feeding" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
    {

        if (position == 0)
        {
            Intent intent = new Intent(FeedTypeActivity.this, AddBottleFeedActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (position == 1)
        {
            Intent intent = new Intent(FeedTypeActivity.this, AddBreastFeedActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

	}
}
