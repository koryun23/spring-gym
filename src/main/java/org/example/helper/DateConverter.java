package org.example.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter {

    private final DateFormat dateFormat;

    public DateConverter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /** A method for obtaining a Date object from the String representation of the date.
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
}
