package com.edupay.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private DateUtils() {}

    public static String format(OffsetDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }

    public static OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}
