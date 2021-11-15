package com.ftn.restaurant.dto.reports;

public class QuarterlyIncomeReportDTO extends IncomeReportDTO {

    private String quarter;

    public QuarterlyIncomeReportDTO() {
    }

    public QuarterlyIncomeReportDTO(double earnings, int totalSoldItems, String quarter) {
        super(earnings, totalSoldItems);
        this.quarter = quarter;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
}
