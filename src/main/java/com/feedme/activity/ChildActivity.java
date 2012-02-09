package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.feedme.R;
import com.feedme.model.Baby;

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

    public View.OnClickListener takePictureListener(final int activity_id)
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), TakePictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", buildBaby());
                bundle.putInt("intentId", activity_id);

                intent.putExtras(bundle);

                startActivityForResult(intent, activity_id);
            }
        };
    }

    public View.OnClickListener selectPictureListener(final int activity_id)
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), SelectPictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", buildBaby());
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

    public Baby buildBaby()
    {
        final EditText babyName = (EditText) findViewById(R.id.babyName);
        final Spinner babySex = (Spinner) findViewById(R.id.babySex);
        final EditText babyHeight = (EditText) findViewById(R.id.babyHeight);
        final EditText babyWeight = (EditText) findViewById(R.id.babyWeight);
        final Button babyDob = (Button) findViewById(R.id.babyDob);;
        
        return new Baby(babyName.getText().toString(),
                        babySex.getSelectedItem().toString(),
                        babyHeight.getText().toString(),
                        babyWeight.getText().toString(),
                        babyDob.getText().toString(),
                        "");
    }
}
