package com.ftn.restaurant.dto.reports;

public class PreparationCostReportDTO {
    private double totalPreparationCosts;
    private String period;

    public PreparationCostReportDTO() {
    }

    public PreparationCostReportDTO(double totalPreparationCosts, String period) {
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
