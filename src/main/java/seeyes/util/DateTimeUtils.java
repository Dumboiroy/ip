package seeyes.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seeyes.exception.InvalidCommandException;

/**
 * Utility class for handling date and time operations.
 * Provides methods for parsing, formatting, and converting date/time strings.
 */
public class DateTimeUtils {
    /**
     * Parses a date/time string into a LocalDateTime object.
     * Expects the format "d/M/yyyy HHmm" (e.g., "25/12/2023 1400").
     *
     * @param dateTimeString the date/time string to parse
     * @return the parsed LocalDateTime object
     * @throws InvalidCommandException if the string cannot be parsed
     */
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
