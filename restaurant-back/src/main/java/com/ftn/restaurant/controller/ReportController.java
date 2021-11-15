package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.reports.MonthlyIncomeReportDTO;
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
    public List<MonthlyIncomeReportDTO> incomeAndSoldMonthlyReport() {
        LOG.info("Monthly income and sold items report...");
        return reportService.getMonthlyIncomeAndSoldItemReport();
    }

}
