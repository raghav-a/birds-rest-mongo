package com.birds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


    public enum Continent {
        africa, north_america("north america"), south_america("south america"), australia, europe, antarctica, asia;

        private final String stringValue;

        Continent(String stringValue) {
            this.stringValue = stringValue;
        }

        Continent() {
            this.stringValue = this.name();
        }

        public static Continent continentFor(String value) {
            for (Continent continent : Continent.values()) {
                if (value.equals(continent.stringValue))
                    return continent;

            }
            System.out.println("not found for:");
            throw new IllegalStateException("invalid value");
        }
    }


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
