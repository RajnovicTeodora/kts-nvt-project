package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.reports.AnnualIncomeReportDTO;
import com.ftn.restaurant.dto.reports.IncomeReportDTO;
import com.ftn.restaurant.dto.reports.QuarterlyIncomeReportDTO;
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
}
