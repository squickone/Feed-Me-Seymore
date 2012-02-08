package com.feedme.activity;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.feedme.model.Baby;

/**
 * User: dayel.ostraco
 * Date: 1/17/12
 * Time: 8:26 PM
 */
public class TakePictureActivity extends BaseActivity
{

    public static int TAKE_PICTURE_ID = 10;
    private Uri picturePath;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Take-Picture");

        takePhoto();
    }

    @Override
    /**
     * Handles the return from the Camera Intent.
     *
     * @param requestCode - The unique id of the Activity.
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == TAKE_PICTURE_ID) {

            final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
            
            Integer intentId = (Integer) getIntent().getExtras().get("intentId");

            Intent intent;
            if (intentId == AddChildActivity.ADD_CHILD_ACTIVITY_ID) {
                intent = new Intent(getApplicationContext(), AddChildActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), EditChildActivity.class);
            }

            Bundle b = new Bundle();
            b.putSerializable("baby", baby);
            intent.putExtras(b);
            
            intent.putExtra("picturePath", getPath(picturePath));
            startActivityForResult(intent, TAKE_PICTURE_ID);
        }
    }

    private void takePhoto()
    {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, Calendar.getInstance().getTimeInMillis() + ".jpg");
        picturePath = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picturePath);
        startActivityForResult(intent, TAKE_PICTURE_ID);
    }

    private String getPath(Uri uri)
    {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);

        } else {
            return null;
        }
    }
}