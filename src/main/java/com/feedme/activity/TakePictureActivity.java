package com.feedme.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * User: dayel.ostraco
 * Date: 1/17/12
 * Time: 8:26 PM
 */
public class TakePictureActivity extends Activity {

    public static int TAKE_PICTURE_ID = 10;
    private Uri pictureUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        takePhoto("Sadie");
    }

    @Override
    /**
     * Handles the return from the Camera Intent.
     *
     * @param requestCode - The unique id of the Activity.
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == TAKE_PICTURE_ID){

            //TODO: Store taken picture path to Baby table.
            Intent intent = new Intent(getApplicationContext(), AddChildActivity.class);
            intent.putExtra("pictureUri", pictureUri);
            startActivityForResult(intent, TAKE_PICTURE_ID);
        }
    }

    /**
     *
     * @param babyName - The name of the Baby. This name will be used for the file name of the image.
     */
    private void takePhoto(String babyName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //TODO: Create the feed-me-seymore folder on the SD card. May need to do this on the Splash Screen as a initial setup.

        File file = new File(Environment.getExternalStorageDirectory(), "feed-me-seymore/" + babyName + ".jpg");

        pictureUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, TAKE_PICTURE_ID);
    }
}