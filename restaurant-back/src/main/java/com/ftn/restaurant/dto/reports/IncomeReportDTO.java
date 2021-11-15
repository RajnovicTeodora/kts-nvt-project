package com.ftn.restaurant.dto.reports;

public class IncomeReportDTO {
    private double earnings;
    private int totalSoldItems;

    public IncomeReportDTO() {
    }

    public IncomeReportDTO(double earnings, int totalSoldItems) {
        this.earnings = earnings;
        this.totalSoldItems = totalSoldItems;
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
}
