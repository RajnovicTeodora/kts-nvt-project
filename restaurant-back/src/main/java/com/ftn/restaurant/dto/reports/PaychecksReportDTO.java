package com.ftn.restaurant.dto.reports;

public class PaychecksReportDTO {
    private double totalPaychecks;
    private String period;

    public PaychecksReportDTO() {
    }

    public PaychecksReportDTO(double totalPaychecks, String period) {
        this.totalPaychecks = totalPaychecks;
        this.period = period;
    }

    public double getTotalPaychecks() {
        return totalPaychecks;
    }

    public void setTotalPaychecks(double totalPaychecks) {
        this.totalPaychecks = totalPaychecks;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
