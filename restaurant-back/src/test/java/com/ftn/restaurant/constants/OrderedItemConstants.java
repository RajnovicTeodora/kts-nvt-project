package com.ftn.restaurant.constants;

import java.time.LocalDate;

public class OrderedItemConstants {

    //TODO REPORT CONSTANTS
    public static LocalDate DB_DATE_FROM = LocalDate.of(2021, 10, 20);
    public static LocalDate DB_DATE_TO = LocalDate.now().minusDays(2);
    public static int SUM_QUANTITY = 24;
    public static double SUM_PREPARATION_COSTS = 100;
}
