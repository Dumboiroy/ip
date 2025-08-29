package com.dumboiroy.seeyes.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.dumboiroy.seeyes.exception.InvalidCommandException;

public class DateTimeUtils {
    public static LocalDateTime parse(String dateTimeString) throws InvalidCommandException {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException("can't format " + dateTimeString + " into datetime.");
        }
    }

    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, ha"));
    }

    public static String dateTimeToSaveString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }
}
