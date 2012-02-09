package com.feedme.util;

import com.feedme.model.BaseObject;

import java.util.Comparator;

/**
 * User: dayelostraco
 * Date: 2/8/12
 * Time: 9:09 PM
 */
public class BaseObjectComparator implements Comparator {

    /**
     * Basic String comparison of ISO8601 Date Strings
     *
     * @param o
     * @param o1
     * @return
     */
    @Override
    public int compare(Object o, Object o1) {

        BaseObject baseObject1 = (BaseObject) o;
        BaseObject baseObject2 = (BaseObject) o1;

        String dateTime1 = baseObject1.getDateTime();
        String dateTime2 = baseObject2.getDateTime();

        return dateTime1.compareToIgnoreCase(dateTime2);
    }
}