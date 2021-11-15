package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.reports.IncomeReportDTO;
import com.ftn.restaurant.dto.reports.PaychecksReportDTO;
import com.ftn.restaurant.dto.reports.PreparationCostsReportDAO;
import com.ftn.restaurant.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ResponseBody
    @GetMapping(path = "/monthly/incomeAndSold")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<IncomeReportDTO> incomeAndSoldMonthlyReport() {
        LOG.info("Monthly income and sold items report...");
        return reportService.getIncomeAndSoldItemReport(0);
    }

    @ResponseBody
    @GetMapping(path = "/quarterly/incomeAndSold")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<IncomeReportDTO> incomeAndSoldQuarterlyReport() {
        LOG.info("Monthly income and sold items report...");
        return reportService.getIncomeAndSoldItemReport(1);
    }

    @ResponseBody
    @GetMapping(path = "/annual/incomeAndSold")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<IncomeReportDTO> incomeAndSoldAnnualReport() {
        LOG.info("Monthly income and sold items report...");
        return reportService.getIncomeAndSoldItemReport(2);
    }

    @ResponseBody
    @GetMapping(path = "/monthly/preparationCosts")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<PreparationCostsReportDAO> preparationCostsMonthlyReport() {
        LOG.info("Monthly preparation costs report...");
        return reportService.getPreparationsCostReport(0);
    }

    @ResponseBody
    @GetMapping(path = "/quarterly/preparationCosts")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<PreparationCostsReportDAO> preparationCostsQuarterlyReport() {
        LOG.info("Quarterly preparation costs report...");
        return reportService.getPreparationsCostReport(1);
    }

    @ResponseBody
    @GetMapping(path = "/annual/preparationCosts")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<PreparationCostsReportDAO> preparationCostsAnnualReport() {
        LOG.info("Annual preparation costs report...");
        return reportService.getPreparationsCostReport(2);
    }

    @ResponseBody
    @GetMapping(path = "/monthly/paychecks")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<PaychecksReportDTO> paychecksMonthlyReport() {
        LOG.info("Monthly paychecks report...");
        return reportService.getPaycheckReport(0);
    }

    @ResponseBody
    @GetMapping(path = "/quarterly/paychecks")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<PaychecksReportDTO> paychecksQuarterlyReport() {
        LOG.info("Quarterly paychecks report...");
        return reportService.getPaycheckReport(1);
    }

    @ResponseBody
    @GetMapping(path = "/annual/paychecks")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<PaychecksReportDTO> paychecksAnnualReport() {
        LOG.info("Annual paychecks report...");
        return reportService.getPaycheckReport(2);
    }
}
