package com.practise.nasa.satellite.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.practise.nasa.satellite.enums.MESSAGES;
import com.practise.nasa.satellite.exception.ValidationException;
import com.practise.nasa.satellite.validator.Validators;

public interface Parser<K, V> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    Calendar calendar = new GregorianCalendar();

    V parse(K k);

    static Date toDate(String date) {
        Date parsedDate = null;
        Validators.validateString(date);
        try {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            parsedDate = simpleDateFormat.parse(date);
        } catch (java.text.ParseException e) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS + "Date" + date);
        }
        return parsedDate;
    }

    static long delta(Date date1, Date date2) {
        return ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
    }

    static Date addDays(Long days) {
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, days.intValue());
        return calendar.getTime();
    }
}