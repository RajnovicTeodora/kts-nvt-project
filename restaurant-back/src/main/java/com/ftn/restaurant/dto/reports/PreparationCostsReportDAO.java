package com.ftn.restaurant.dto.reports;

public class PreparationCostsReportDAO {
    private double totalPreparationCosts;
    private String period;

    public PreparationCostsReportDAO() {
    }

    public PreparationCostsReportDAO(double totalPreparationCosts, String period) {
        this.totalPreparationCosts = totalPreparationCosts;
        this.period = period;
    }

    public double getTotalPreparationCosts() {
        return totalPreparationCosts;
    }

    public void setTotalPreparationCosts(double totalPreparationCosts) {
        this.totalPreparationCosts = totalPreparationCosts;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
