package com.ftn.restaurant.service;

import com.ftn.restaurant.controller.ReportController;
import com.ftn.restaurant.dto.reports.IncomeReportDTO;
import com.ftn.restaurant.model.Order;

import com.ftn.restaurant.repository.OrderRepository;
import com.ftn.restaurant.repository.OrderedItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;
    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    public ReportService(OrderRepository orderRepository, OrderedItemRepository orderedItemRepository) {
        this.orderRepository = orderRepository;
        this.orderedItemRepository = orderedItemRepository;
    }

    public List<IncomeReportDTO> getIncomeAndSoldItemReport(int type) {

        Optional<Order> minOrder = orderRepository.findTopByIsPaidTrueOrderByDateAsc();
        Optional<Order> maxOrder = orderRepository.findTopByIsPaidTrueOrderByDateDesc();

        List<IncomeReportDTO> incomeAndSold = new ArrayList<>();

        //No paid orders in system
        if (!minOrder.isPresent() || !maxOrder.isPresent())
            return incomeAndSold;

        LocalDate from = minOrder.get().getDate();
        LocalDate to = maxOrder.get().getDate();

        if (type == 0) {
            // From start date to end date calculate monthly income
            for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusMonths(1)) {
                IncomeReportDTO report = new IncomeReportDTO(0, 0, YearMonth.of(date.getYear(), date.getMonthValue()).toString());

                report.setEarnings(orderRepository.sumTotalPriceByIsPaidAndDateBetween(date, date.plusMonths(1).minusDays(1)));
                report.setTotalSoldItems(orderedItemRepository.sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(date, date.plusMonths(1).minusDays(1)));
                incomeAndSold.add(report);
            }
        } else if (type == 1) {
            long q = (from.getMonthValue() / 3);
            for (LocalDate date = from.with(IsoFields.QUARTER_OF_YEAR, q).with(IsoFields.DAY_OF_QUARTER, 1L); to.plusDays(1).isAfter(date); date = date.plusMonths(3)) {
                IncomeReportDTO report = new IncomeReportDTO(0, 0, "Q" + date.get(IsoFields.QUARTER_OF_YEAR) + " - " + date.getYear());

                report.setEarnings(orderRepository
                        .sumTotalPriceByIsPaidAndDateBetween(date, date.plusMonths(3).with(IsoFields.DAY_OF_QUARTER, 1L).minusDays(1)));
                report.setTotalSoldItems(orderedItemRepository
                        .sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(date, date.plusMonths(3).with(IsoFields.DAY_OF_QUARTER, 1L).minusDays(1)));
                incomeAndSold.add(report);
            }
        } else {
            for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusYears(1)) {
                IncomeReportDTO report = new IncomeReportDTO(0, 0, Integer.toString(date.getYear()));

                report.setEarnings(orderRepository.sumTotalPriceByIsPaidAndDateBetween(date, date.plusYears(1).minusDays(1)));
                report.setTotalSoldItems(orderedItemRepository.sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(date, date.plusYears(1).minusDays(1)));
                incomeAndSold.add(report);
            }
        }

        return incomeAndSold;
    }
}

