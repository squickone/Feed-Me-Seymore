package com.feedmesomore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import com.feedmesomore.R;
import com.feedmesomore.model.Baby;

/**
 * User: steve quick
 * Date: 1/28/12
 * Time: 6:57 PM
 */
public abstract class ChildActivity extends BaseActivity
{
    public int mYear;
    public int mMonth;
    public int mDay;

    public View.OnClickListener takePictureListener(final String babyId, final int activity_id)
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), TakePictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", buildBaby(babyId));
                bundle.putInt("intentId", activity_id);

                intent.putExtras(bundle);

                startActivityForResult(intent, activity_id);
            }
        };
    }

    public View.OnClickListener selectPictureListener(final String babyId, final int activity_id)
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), SelectPictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", buildBaby(babyId));
                bundle.putInt("intentId", activity_id);
                
                intent.putExtras(bundle);

                startActivityForResult(intent, activity_id);
            }
        };
    }

    public View.OnClickListener showDateDialog()
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        };
    }

    public Baby buildBaby(String babyId)
    {
        final EditText babyName = (EditText) findViewById(R.id.babyName);
        final Spinner babySex = (Spinner) findViewById(R.id.babySex);
        final EditText babyHeight = (EditText) findViewById(R.id.babyHeight);
        final EditText babyWeight = (EditText) findViewById(R.id.babyWeight);
        final Button babyDob = (Button) findViewById(R.id.babyDob);
        final ImageView babyImage = (ImageView) findViewById(R.id.babyPicture);
        
        return new Baby(babyId,
                        babyName.getText().toString(),
                        babySex.getSelectedItem().toString(),
                        babyHeight.getText().toString(),
                        babyWeight.getText().toString(),
                        babyDob.getText().toString(),
                        babyImage.toString());
    }
}
