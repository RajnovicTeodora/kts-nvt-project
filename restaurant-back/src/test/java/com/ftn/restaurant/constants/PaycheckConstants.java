package com.ftn.restaurant.constants;

import com.ftn.restaurant.dto.UpdatePaycheckDTO;

import java.time.LocalDate;

public class PaycheckConstants {

    public static long DB_EMPLOYEE_ID = 2;
    public static double SUM_PAYCHECKS = 85;

    public static String NON_EXISTENT_EMPLOYEE_USERNAME = "Velimir";
    public static String DELETED_EMPLOYEE_USERNAME = "Deleted";

    public static LocalDate DB_DATE_FROM_NOV = LocalDate.of(2021, 11, 1);
    public static LocalDate DB_DATE_FROM_DEC = LocalDate.of(2021, 12, 1);
    public static LocalDate DB_DATE_TO = LocalDate.of(2022, 1, 1);
    public static double SUM_PAYCHECKS1 = 85;
    public static double SUM_PAYCHECKS2 = 30;
    public static double SUM_PAYCHECKS3 = 0;

    public static String DB_EMPLOYEE_USERNAME = "manager";

    public static double OLD_PAYCHECK = 199.9;
    public static double NEW_PAYCHECK = 500;
    public static double NEW_PAYCHECK1 = 400;
    public static double INVALID_PAYCHECK = 0;

    public static UpdatePaycheckDTO UPDATE_PAYCHECK_DTO = new UpdatePaycheckDTO("chef", 199);
    public static UpdatePaycheckDTO UPDATE_PAYCHECK_DTO1 = new UpdatePaycheckDTO("chef", 29);
    public static UpdatePaycheckDTO INVALID_USERNAME_PAYCHECK_DTO = new UpdatePaycheckDTO("none", 199);
    public static UpdatePaycheckDTO INVALID_NUMBER_PAYCHECK_DTO = new UpdatePaycheckDTO("chef", 0);

}
