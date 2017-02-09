package com.birds.model;

import com.birds.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationDate {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String value;

    public ApplicationDate(String value) {
        this.value = validate(value);
    }

    private ApplicationDate() {
    }

    public static String currentDate() {
        final Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }


    public  String validate(String date) {
        if(date==null || date.isEmpty())
            return currentDate();

        try {
            final Date parsedDate = dateFormat.parse(date);
            final String format = dateFormat.format(parsedDate);
            if (!date.equals(format)) {
                throw new ApplicationException(HttpStatus.BAD_REQUEST);
            }
            return date;
        } catch (ParseException e) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationDate)) return false;
        ApplicationDate that = (ApplicationDate) o;
        return !(value != null ? !value.equals(that.value) : that.value != null);
    }

    @Override
    public String toString() {
        return value;
    }
}
