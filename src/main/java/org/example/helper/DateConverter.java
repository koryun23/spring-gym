package org.example.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter {

    private final DateFormat dateFormat;

    public DateConverter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

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
