package seeyes.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seeyes.exception.InvalidCommandException;

/**
 * Utility class for date and time operations.
 */
public class DateTimeUtils {
    /**
     * Parses a date-time string into a LocalDateTime object.
     * 
     * @param dateTimeString the date-time string to parse
     * @return the parsed LocalDateTime
     * @throws InvalidCommandException if parsing fails
     */
    public static LocalDateTime parse(String dateTimeString) throws InvalidCommandException {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException("can't format " + dateTimeString + " into datetime.");
        }
    }

    /**
     * Converts a LocalDateTime to a human-readable string.
     * 
     * @param dateTime the LocalDateTime to convert
     * @return the formatted string
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, ha"));
    }

    /**
     * Converts a LocalDateTime to a string for saving to file.
     * 
     * @param dateTime the LocalDateTime to convert
     * @return the formatted string for saving
     */
    public static String dateTimeToSaveString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }
}
