package com.birds;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String currentDate() {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
        return dt.format(new Date(System.currentTimeMillis()));
    }
}
