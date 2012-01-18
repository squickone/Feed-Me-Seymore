package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * User: dayel.ostraco
 * Date: 1/17/12
 * Time: 9:31 PM
 */
public class SelectPictureActivity extends Activity {
    
    private static final int SELECT_PICTURE_REQUEST = 11;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        pickPicture();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == SELECT_PICTURE_REQUEST){
            Uri photoUri = data.getData();
            
            if(photoUri!=null){
                //TODO: Save picture path to the Baby table.
            }
            
            Intent intent = new Intent(getApplicationContext(), AddChildActivity.class);
            startActivityForResult(intent, SELECT_PICTURE_REQUEST);
        }
    }

    private void pickPicture(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(Intent.createChooser(pickPhoto, "Choose a picture"), SELECT_PICTURE_REQUEST);
    }
}