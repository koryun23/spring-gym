package org.example.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter {

    private final DateFormat dateFormat;

    public DateConverter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * A method for obtaining a Date object from the String representation of the date.
     *
     * @param date String representation of the given date
     * @return Date object of the corresponding String representation
     */
    public Date stringToDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String dateToString(Date date) {
        return dateFormat.format(date);
    }

    // TODO: These methods are currently unused.
    // Why: There may not yet be a need in the application to convert between Date and String representations.
    // What I offer: Consider where these conversions might be useful (e.g., dto, database storage)
    // and refactor code to utilize this helper where needed to avoid duplicated date handling logic.
}
