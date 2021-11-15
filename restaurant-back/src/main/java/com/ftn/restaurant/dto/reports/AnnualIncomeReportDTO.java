package com.ftn.restaurant.dto.reports;

import java.time.YearMonth;

public class AnnualIncomeReportDTO extends IncomeReportDTO {

    private int year;

    public AnnualIncomeReportDTO() {
    }

    public AnnualIncomeReportDTO(double earnings, int totalSoldItems, int year) {
        super(earnings, totalSoldItems);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
