package com.ftn.restaurant.constants;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeConstants {

    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    public static final LocalDate TWO_DAYS_AGO = LocalDate.now().minusDays(1);

    public static final LocalTime NOW = LocalTime.now();
}
