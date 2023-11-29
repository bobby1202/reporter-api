package com.bank.reporter.util;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {
    /**
     *
     * @param dateString
     * @return
     */
    public static LocalDateTime convertStringToLocalDateTime(String dateString){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("M/d/yyyy H:mm:ss");
        if (!dateString.matches(".*:\\d{2}:\\d{2}$")) {
            dateString += ":00";
        }
        return LocalDateTime.parse(dateString, formatter);
    }
}
