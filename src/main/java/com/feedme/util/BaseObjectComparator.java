package com.feedme.util;

import android.util.Log;
import com.feedme.model.BaseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * User: dayelostraco
 * Date: 2/8/12
 * Time: 9:09 PM
 */
public class BaseObjectComparator implements Comparator {

    /**
     * Converts the combination of date and startTime for both objects to a Date and then compares the two. If the date
     * String cannot be parsed, it falls back to a String comparison.
     *
     * @param o
     * @param o1
     * @return
     */
    @Override
    public int compare(Object o, Object o1) {

        SimpleDateFormat sdf = new SimpleDateFormat("M-d-yyyy:hh:mm:ss");

        BaseObject baseObject1 = (BaseObject) o;
        BaseObject baseObject2 = (BaseObject) o1;

        String dateTime1 = baseObject1.getDate().trim() + ":" + baseObject1.getStartTime();
        String dateTime2 = baseObject2.getDate().trim() + ":" + baseObject2.getStartTime();

        try {
            Date date1 = sdf.parse(dateTime1);
            Date date2 = sdf.parse(dateTime2);

            return date1.compareTo(date2);

        } catch (ParseException e) {
            Log.d("BaseObjectComparator", "Could not parse BaseObjectDate");
        }

        return dateTime1.compareToIgnoreCase(dateTime2);
    }
}