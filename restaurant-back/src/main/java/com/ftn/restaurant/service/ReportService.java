package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.reports.MonthlyIncomeReportDTO;
import com.ftn.restaurant.model.Order;

import com.ftn.restaurant.repository.OrderRepository;
import com.ftn.restaurant.repository.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;

    @Autowired
    public ReportService(OrderRepository orderRepository, OrderedItemRepository orderedItemRepository) {
        this.orderRepository = orderRepository;
        this.orderedItemRepository = orderedItemRepository;
    }

    public List<MonthlyIncomeReportDTO> getMonthlyIncomeAndSoldItemReport() {

        Optional<Order> minOrder = orderRepository.findTopByIsPaidTrueOrderByDateAsc();
        Optional<Order> maxOrder = orderRepository.findTopByIsPaidTrueOrderByDateDesc();

        List<MonthlyIncomeReportDTO> incomeAndSold = new ArrayList<>();

        //No paid orders in system
        if (!minOrder.isPresent() || !maxOrder.isPresent())
            return incomeAndSold;

        LocalDate from = minOrder.get().getDate();
        LocalDate to = maxOrder.get().getDate();

        // From start date to end date calculate monthly income
        for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusMonths(1)) {
            MonthlyIncomeReportDTO report = new MonthlyIncomeReportDTO(0, 0, YearMonth.of(date.getYear(), date.getMonthValue()));

            report.setEarnings(orderRepository.sumTotalPriceByIsPaidAndDateBetween(date, date.plusMonths(1).minusDays(1)));
            report.setTotalSoldItems(orderedItemRepository.sumQuantityByMenuItemIsPaidAndMenuItemDateBetween(date, date.plusMonths(1).minusDays(1)));
            incomeAndSold.add(report);
        }
        return incomeAndSold;
    }

}
