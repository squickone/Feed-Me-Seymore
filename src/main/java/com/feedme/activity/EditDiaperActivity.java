package com.feedme.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.feedme.R;
import com.feedme.dao.DiaperDao;
import com.feedme.model.Baby;
import com.feedme.model.Diaper;

import java.util.Calendar;

/**
 * User: dayelostraco
 * Date: 2/4/12
 * Time: 1:35 PM
 */
public class EditDiaperActivity extends DiaperActivity {

    final Bundle bundle = new Bundle();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diaper);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Edit-Diaper");

        final DiaperDao diaperDao = new DiaperDao(getApplicationContext());

        //Baby Data
        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");
        bundle.putSerializable("baby", baby);
        styleActivity(baby.getSex());

        //Diaper Data
        final Diaper diaper = (Diaper) getIntent().getSerializableExtra("diaper");

        //Date and Time Fields
        entryDate = (Button) findViewById(R.id.editDiaperDate);
        startTime = (Button) findViewById(R.id.editDiaperTime);
        entryDate.setText(diaper.getDate());
        startTime.setText(diaper.getStartTime());
        entryDate.setOnClickListener(showDateDialog());
        startTime.setOnClickListener(showStartTimeDialog());

        // get/set the current date
        final Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        startHour = calendar.get(Calendar.HOUR_OF_DAY);
        startMinute = calendar.get(Calendar.MINUTE);
        startSecond = calendar.get(Calendar.SECOND);

        endHour = calendar.get(Calendar.HOUR_OF_DAY);
        endMinute = calendar.get(Calendar.MINUTE);
        endSecond = calendar.get(Calendar.SECOND);

        //Spinners
        final Spinner diaperType = (Spinner) findViewById(R.id.editDiaperType);
        final Spinner diaperConsistency = (Spinner) findViewById(R.id.editDiaperConsistency);
        final Spinner diaperColor = (Spinner) findViewById(R.id.editDiaperColor);

        ArrayAdapter adapter = (ArrayAdapter) diaperType.getAdapter();
        diaperType.setSelection(getIndexFromElement(adapter, diaper.getType()));

        adapter = (ArrayAdapter) diaperConsistency.getAdapter();
        diaperConsistency.setSelection(getIndexFromElement(adapter, diaper.getConsistency()));

        adapter = (ArrayAdapter) diaperColor.getAdapter();
        diaperColor.setSelection(getIndexFromElement(adapter, diaper.getColor()));

        //Delete Button
        Button deleteButton = (Button) findViewById(R.id.deleteDiaper);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDiaper(diaper.getId(), baby);
            }
        });

        //Save Button
        Button saveButton = (Button) findViewById(R.id.updateDiaperButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaper.setDate(entryDate.getText().toString());
                diaper.setStartTime(startTime.getText().toString());
                diaper.setType(diaperType.getSelectedItem().toString());
                diaper.setConsistency(diaperConsistency.getSelectedItem().toString());
                diaper.setColor(diaperColor.getSelectedItem().toString());
                diaperDao.updateDiaper(diaper, diaper.getId());

                Intent intent = new Intent(EditDiaperActivity.this, ViewDiapersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_DIAPER_ACTIVITY_ID);
            }
        });
    }

    private void deleteDiaper(final int diaperId, final Baby baby) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditDiaperActivity.this);
        myAlertDialog.setTitle(getString(R.string.deleteDiaperDialogTitle));
        myAlertDialog.setMessage(getString(R.string.deleteDiaperDialogMsg));
        myAlertDialog.setPositiveButton(getString(R.string.deleteDiaperDialogPositiveButton), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                DiaperDao diaperDao = new DiaperDao(getApplicationContext());
                diaperDao.deleteDiaperByID(diaperId);

                Intent intent = new Intent(EditDiaperActivity.this, ViewDiapersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baby", baby);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_DIAPER_ACTIVITY_ID);

            }
        });

        myAlertDialog.setNegativeButton(getString(R.string.deleteDiaperDialogNegativeButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        myAlertDialog.show();

    }

    /**
     * Returns the position in a spinner based on the passed in element.
     *
     * @param adapter - ArrayAdapter
     * @param element - String
     * @return - int position of Spinner
     */
    private int getIndexFromElement(ArrayAdapter adapter, String element) {
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(element)) {
                return i;
            }
        }

        return 0;
    }
}