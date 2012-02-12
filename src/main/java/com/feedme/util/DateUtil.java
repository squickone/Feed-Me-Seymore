package com.feedme.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: dayelostraco
 * Date: 2/12/12
 * Time: 6:20 AM
 */
public class DateUtil {

    private SimpleDateFormat timeStringFormat;
    
    public DateUtil(){
        timeStringFormat = new SimpleDateFormat("HH:mm:ss");
    }

    /**
     * Returns the String format of duration between the two passed in time String formats (hh:mm:ss).
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public String getDurationAsStringMsg(String startTime, String endTime) {

        Calendar startTimeCal = convertTimeString(startTime);
        Calendar endTimeCal = convertTimeString(endTime);

        /* If the Hour of the End Time is less than the hour for the Start time, it must have occurred on the next day*/
        if(startTimeCal.get(Calendar.HOUR_OF_DAY) > endTimeCal.get(Calendar.HOUR_OF_DAY)){
            endTimeCal.add(Calendar.DAY_OF_YEAR, 1);
        }

        long duration = endTimeCal.getTimeInMillis() - startTimeCal.getTimeInMillis();

        int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);
        int hours   = (int) ((duration / (1000*60*60)) % 24);

        if(hours==0){
            return String.format("%d min, %d sec", minutes, seconds);

        } else {
            return String.format("%d hour, %d min, %d sec", hours, minutes, seconds);
        }
    }

    /**
     * Returns a String message based on the passed in duration String in the format of hh:mm:ss.
     *
     * @param duration
     * @return
     */
    public String getDurationAsStringMsg(String duration){

        String[] durationArray = duration.split(":");
        int hours = new Integer(durationArray[0]);
        int minutes = new Integer(durationArray[1]);
        int seconds = new Integer(durationArray[2]);

        if(hours==0){
            return String.format("%d min, %d sec", minutes, seconds);

        } else {
            return String.format("%d hour, %d min, %d sec", hours, minutes, seconds);
        }
    }

    /**
     * Calculates the duration between to Times measured in Longs and converts them to a Time String in the format of
     * hh:mm:ss.
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public String getDurationAsTimeString(Long startTime, Long endTime) {

        Calendar startTimeCal = Calendar.getInstance();
        Calendar endTimeCal = Calendar.getInstance();

        startTimeCal.setTime(new Date(startTime));
        endTimeCal.setTime(new Date(endTime));

        long duration = endTimeCal.getTimeInMillis() - startTimeCal.getTimeInMillis();

        int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);
        int hours   = (int) ((duration / (1000*60*60)) % 24);

        return String.format("%d:%d:%d", hours, minutes, seconds);
    }

    /**
     * Converts a Date represented by a Long into a Time String in the format of hh:mm:ss.
     * NOTE: Synchronized since SimpleDateFormat is not thread safe.
     * 
     * @param feedTime
     * @return
     */
    public synchronized String convertDateLongToTimeString(Long feedTime){
        return timeStringFormat.format(feedTime);
    }

    /**
     * Converts a time String in the format of hh:mm:ss into a Calendar object with that time and the current date.
     *
     * @param timeString
     * @return
     */
    private Calendar convertTimeString(String timeString) {

        String[] timeStringArray = timeString.split(":");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, new Integer(timeStringArray[0]));
        calendar.set(Calendar.MINUTE, new Integer(timeStringArray[1]));
        calendar.set(Calendar.SECOND, new Integer(timeStringArray[2]));
        
        return calendar;
    }
}
