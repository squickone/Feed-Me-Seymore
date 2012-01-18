package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * User: dayel.ostraco
 * Date: 1/17/12
 * Time: 9:31 PM
 */
public class SelectPictureActivity extends Activity {
    
    public static final int SELECT_PICTURE_ID = 11;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        pickPicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == SELECT_PICTURE_ID){

            Intent intent = new Intent(getApplicationContext(), AddChildActivity.class);

            if(data!=null && data.getData()!=null){
                Uri photoUri = data.getData();
                intent.putExtra("picturePath", getPath(photoUri));
            }

            startActivityForResult(intent, SELECT_PICTURE_ID);
        }
    }

    private void pickPicture(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(Intent.createChooser(pickPhoto, "Choose a picture"), SELECT_PICTURE_ID);
    }

    private String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);

        } else {
            return null;
        }
    }
}