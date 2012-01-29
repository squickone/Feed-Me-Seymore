package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public View.OnClickListener takePictureListener(final Baby baby, final int activity_id)
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), TakePictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);

                intent.putExtra("intentId", activity_id);

                startActivityForResult(intent, activity_id);
            }
        };
    }

    public View.OnClickListener selectPictureListener(final Baby baby, final int activity_id)
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), SelectPictureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);
                
                intent.putExtra("intentId", activity_id);

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

}
