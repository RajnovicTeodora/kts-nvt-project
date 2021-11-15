package com.ftn.restaurant.dto.reports;

import java.time.YearMonth;

public class MonthlyIncomeReportDTO extends IncomeReportDTO {

    private YearMonth month;

    public MonthlyIncomeReportDTO() {
    }

    public MonthlyIncomeReportDTO(double earnings, int totalSoldItems, YearMonth month) {
        super(earnings, totalSoldItems);
        this.month = month;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }
}
