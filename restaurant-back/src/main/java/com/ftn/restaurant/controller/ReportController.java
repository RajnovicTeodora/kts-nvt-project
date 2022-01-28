package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.reports.IncomeReportDTO;
import com.ftn.restaurant.dto.reports.PaychecksReportDTO;
import com.ftn.restaurant.dto.reports.PreparationCostReportDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<IncomeReportDTO> incomeAndSoldMonthlyReport(@AuthenticationPrincipal User user) {
        LOG.info("Monthly income and sold items report...");
        return reportService.getIncomeAndSoldItemReport(0);
    }

    @ResponseBody
    @GetMapping(path = "/quarterly/incomeAndSold")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<IncomeReportDTO> incomeAndSoldQuarterlyReport(@AuthenticationPrincipal User user) {
        LOG.info("Monthly income and sold items report...");
        return reportService.getIncomeAndSoldItemReport(1);
    }

    @ResponseBody
    @GetMapping(path = "/annual/incomeAndSold")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<IncomeReportDTO> incomeAndSoldAnnualReport(@AuthenticationPrincipal User user) {
        LOG.info("Monthly income and sold items report...");
        return reportService.getIncomeAndSoldItemReport(2);
    }

    @ResponseBody
    @GetMapping(path = "/monthly/preparationCosts")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<PreparationCostReportDTO> preparationCostsMonthlyReport(@AuthenticationPrincipal User user) {
        LOG.info("Monthly preparation costs report...");
        return reportService.getPreparationsCostReport(0);
    }

    @ResponseBody
    @GetMapping(path = "/quarterly/preparationCosts")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<PreparationCostReportDTO> preparationCostsQuarterlyReport(@AuthenticationPrincipal User user) {
        LOG.info("Quarterly preparation costs report...");
        return reportService.getPreparationsCostReport(1);
    }

    @ResponseBody
    @GetMapping(path = "/annual/preparationCosts")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<PreparationCostReportDTO> preparationCostsAnnualReport(@AuthenticationPrincipal User user) {
        LOG.info("Annual preparation costs report...");
        return reportService.getPreparationsCostReport(2);
    }

    @ResponseBody
    @GetMapping(path = "/monthly/paychecks")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<PaychecksReportDTO> paychecksMonthlyReport(@AuthenticationPrincipal User user) {
        LOG.info("Monthly paychecks report...");
        return reportService.getPaycheckReport(0);
    }

    @ResponseBody
    @GetMapping(path = "/quarterly/paychecks")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<PaychecksReportDTO> paychecksQuarterlyReport(@AuthenticationPrincipal User user) {
        LOG.info("Quarterly paychecks report...");
        return reportService.getPaycheckReport(1);
    }

    @ResponseBody
    @GetMapping(path = "/annual/paychecks")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<PaychecksReportDTO> paychecksAnnualReport(@AuthenticationPrincipal User user) {
        LOG.info("Annual paychecks report...");
        return reportService.getPaycheckReport(2);
    }
}
