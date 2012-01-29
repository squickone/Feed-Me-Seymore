package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import com.feedme.model.Baby;

/**
 * User: dayel.ostraco
 * Date: 1/17/12
 * Time: 9:31 PM
 */
public class SelectPictureActivity extends Activity
{
    public static final int SELECT_PICTURE_ID = 11;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pickPicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == SELECT_PICTURE_ID)
        {
            Baby baby = (Baby) getIntent().getSerializableExtra("baby");

            Log.d("BABY:PIC:", baby.dump());

            Integer intentId = (Integer) getIntent().getExtras().get("intentId");

            Intent intent;
            if (intentId == AddChildActivity.ADD_CHILD_ACTIVITY_ID) {
                intent = new Intent(getApplicationContext(), AddChildActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), EditChildActivity.class);
            }
            
            Bundle b = new Bundle();
            b.putSerializable("baby", baby);

            if (data != null && data.getData() != null)
            {
                Uri photoUri = data.getData();
                b.putString("picturePath", getPath(photoUri));
            }

            intent.putExtras(b);

            startActivityForResult(intent, SELECT_PICTURE_ID);
        }
    }

    private void pickPicture()
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(Intent.createChooser(pickPhoto, "Choose a picture"), SELECT_PICTURE_ID);
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