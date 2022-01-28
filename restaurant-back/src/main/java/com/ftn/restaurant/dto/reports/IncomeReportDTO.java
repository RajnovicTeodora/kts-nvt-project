package com.ftn.restaurant.dto.reports;

public class IncomeReportDTO {
    private double earnings;
    private int totalSoldItems;
    private String period;

    public IncomeReportDTO() {
    }

    public IncomeReportDTO(double earnings, int totalSoldItems, String period) {
        this.earnings = earnings;
        this.totalSoldItems = totalSoldItems;
        this.period = period;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
