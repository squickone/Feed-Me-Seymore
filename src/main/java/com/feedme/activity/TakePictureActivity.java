package com.feedme.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.feedme.R;

/**
 * User: dayel.ostraco
 * Date: 1/17/12
 * Time: 8:26 PM
 */
public class TakePictureActivity extends Activity {

    private static int TAKE_PICTURE = 10;
    private Uri outputFileUri;

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

        if (requestCode == TAKE_PICTURE){

            //TODO: Store taken picture path to Baby table.
            Intent intent = new Intent(getApplicationContext(), AddChildActivity.class);
            startActivityForResult(intent, TAKE_PICTURE);
        }
    }

    /**
     *
     * @param babyName - The name of the Baby. This name will be used for the file name of the image.
     */
    private void takePhoto(String babyName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "feed-me-seymore/" + babyName + ".jpg");

        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }
}