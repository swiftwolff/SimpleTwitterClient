package com.codepath.apps.mysimpletweets.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by jeffhsu on 2/8/15.
 */
public class TimeStamp {
    private static final SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");

    public static String getTimeStamp(String createdAt) {

        String timestamp = null;

        try {
            Date createTimeStamp = sf.parse(createdAt);
            Date now = new Date();

            long diff = now.getTime() - createTimeStamp.getTime();

            if(TimeUnit.MILLISECONDS.toDays(diff)>=1) {
                timestamp = TimeUnit.MILLISECONDS.toDays(diff) + "d";
            } else if (TimeUnit.MILLISECONDS.toHours(diff)>=1) {
                timestamp =TimeUnit.MILLISECONDS.toHours(diff) + "h";
            } else if (TimeUnit.MILLISECONDS.toMinutes(diff)>=1) {
                timestamp = TimeUnit.MILLISECONDS.toMinutes(diff) + "m";
            } else {
                timestamp = TimeUnit.MILLISECONDS.toSeconds(diff) + "s";
                if (timestamp.equals("0s")||TimeUnit.MILLISECONDS.toSeconds(diff)<0) timestamp = "now";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
