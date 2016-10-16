package com.birds;

import com.google.common.collect.Sets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class DateUtil {

    public static Set<String> continents = Sets.newHashSet("africa", "north america", "south america", " australia", "europe", "antarctica", "asia");


    private static String ApplicationDateFormat = "yyyy-MM-dd";

    public static String currentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationDateFormat);
        final Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }


    public static void validate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationDateFormat);
        try {
            final Date parsedDate = dateFormat.parse(date);
            final String format = dateFormat.format(parsedDate);
            if (!date.equals(format)) {
                throw new IllegalStateException("invalid date.");
            }
        } catch (ParseException e) {
            throw new IllegalStateException("invalid date.");
        }

    }
}
