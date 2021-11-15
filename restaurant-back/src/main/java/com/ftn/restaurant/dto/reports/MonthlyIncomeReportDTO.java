package com.ftn.restaurant.dto.reports;

import java.time.YearMonth;

public class MonthlyIncomeReportDTO {
    private double earnings;
    private int totalSoldItems;
    private YearMonth month;

    public MonthlyIncomeReportDTO() {
    }

    public MonthlyIncomeReportDTO(double earnings, int totalSoldItems, YearMonth month) {
        this.earnings = earnings;
        this.totalSoldItems = totalSoldItems;
        this.month = month;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public int getTotalSoldItems() {
        return totalSoldItems;
    }

    public void setTotalSoldItems(int totalSoldItems) {
        this.totalSoldItems = totalSoldItems;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }
}
