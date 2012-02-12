package com.feedme.util;

import java.util.Calendar;

/**
 * User: dayelostraco
 * Date: 2/12/12
 * Time: 6:20 AM
 */
public class DateUtil {

    public synchronized String convertFeedTime(String feedTime) {

        long feedTimeLong = new Long(feedTime);

        //int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((feedTimeLong / (1000*60)) % 60);
        int hours   = (int) ((feedTimeLong / (1000*60*60)) % 24);

        return String.format("%d hour, %d min", hours, minutes);
    }

    public synchronized String getDuration(String startTime, String endTime) {
        
        String[] startTimeArray = startTime.split(":");
        String[] endTimeArray = endTime.split(":");

        Calendar startTimeCal = Calendar.getInstance();
        Calendar endTimeCal = Calendar.getInstance();

        /* If the Hour of the End Time is less than the hour for the Start time, it must have occurred on the next day*/
        if(new Integer(startTimeArray[0]) > new Integer(endTimeArray[0])){
            endTimeCal.add(Calendar.DAY_OF_YEAR, 1);
        }

        startTimeCal.set(Calendar.HOUR_OF_DAY, new Integer(startTimeArray[0]));
        startTimeCal.set(Calendar.MINUTE, new Integer(startTimeArray[1]));

        endTimeCal.set(Calendar.HOUR_OF_DAY, new Integer(endTimeArray[0]));
        endTimeCal.set(Calendar.MINUTE, new Integer(endTimeArray[1]));

        long duration = endTimeCal.getTimeInMillis() - startTimeCal.getTimeInMillis();

        //int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);
        int hours   = (int) ((duration / (1000*60*60)) % 24);

        if(hours==0){
            return String.format("%d min", minutes);

        } else {
            return String.format("%d hour, %d min", hours, minutes);
        }
    }
}
