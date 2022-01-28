package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.reports.IncomeReportDTO;
import com.ftn.restaurant.dto.reports.PaychecksReportDTO;
import com.ftn.restaurant.dto.reports.PreparationCostReportDTO;
import com.ftn.restaurant.model.Order;

import com.ftn.restaurant.model.Paychecks;
import com.ftn.restaurant.repository.OrderRepository;
import com.ftn.restaurant.repository.OrderedItemRepository;
import com.ftn.restaurant.repository.PaycheckRepository;
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
    private final PaycheckRepository paycheckRepository;

    @Autowired
    public ReportService(OrderRepository orderRepository, OrderedItemRepository orderedItemRepository,
                         PaycheckRepository paycheckRepository) {
        this.orderRepository = orderRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.paycheckRepository = paycheckRepository;
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
            for (LocalDate date = from.withDayOfMonth(1); to.plusDays(1).isAfter(date); date = date.plusMonths(1)) {
                IncomeReportDTO report = new IncomeReportDTO(0, 0, YearMonth.of(date.getYear(), date.getMonthValue()).toString());

                report.setEarnings(orderRepository.sumTotalPriceByIsPaidAndDateBetween(date, date.plusMonths(1).minusDays(1)));
                report.setTotalSoldItems(orderedItemRepository.sumQuantityByOrderIsPaidAndOrderDateBetween(date, date.plusMonths(1).minusDays(1)));
                incomeAndSold.add(report);
            }
        } else if (type == 1) {
            long q = (from.getMonthValue() / 3) + 1;
            for (LocalDate date = from.with(IsoFields.QUARTER_OF_YEAR, q).with(IsoFields.DAY_OF_QUARTER, 1L); to.plusDays(1).isAfter(date); date = date.plusMonths(3)) {
                IncomeReportDTO report = new IncomeReportDTO(0, 0, "Q" + date.get(IsoFields.QUARTER_OF_YEAR) + " - " + date.getYear());

                int sumEarnings = 0;
                int sumSold = 0;

                for (int i = 0; i <= 2; i++) {

                    if (date.plusMonths(i + 1).minusDays(1).isAfter(to.withDayOfMonth(1).plusMonths(1).minusDays(1))) break;
                    sumEarnings += orderRepository
                            .sumTotalPriceByIsPaidAndDateBetween(date.plusMonths(i), date.plusMonths(i + 1).minusDays(1));
                    sumSold += orderedItemRepository
                            .sumQuantityByOrderIsPaidAndOrderDateBetween(date.plusMonths(i), date.plusMonths(i + 1).minusDays(1));
                }
                report.setEarnings(sumEarnings);
                report.setTotalSoldItems(sumSold);
                incomeAndSold.add(report);
            }
        } else {
            for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusYears(1)) {
                IncomeReportDTO report = new IncomeReportDTO(0, 0, Integer.toString(date.getYear()));

                report.setEarnings(orderRepository.sumTotalPriceByIsPaidAndDateBetween(date, date.plusYears(1).minusDays(1)));
                report.setTotalSoldItems(orderedItemRepository.sumQuantityByOrderIsPaidAndOrderDateBetween(date, date.plusYears(1).minusDays(1)));
                incomeAndSold.add(report);
            }
        }

        return incomeAndSold;
    }

    public List<PreparationCostReportDTO> getPreparationsCostReport(int type) {

        Optional<Order> minOrder = orderRepository.findTopByIsPaidTrueOrderByDateAsc();
        Optional<Order> maxOrder = orderRepository.findTopByIsPaidTrueOrderByDateDesc();

        List<PreparationCostReportDTO> prepCosts = new ArrayList<>();

        //No paid orders in system
        if (!minOrder.isPresent() || !maxOrder.isPresent())
            return prepCosts;

        LocalDate from = minOrder.get().getDate();
        LocalDate to = maxOrder.get().getDate();

        if (type == 0) {
            // From start date to end date
            for (LocalDate date = from.withDayOfMonth(1); to.plusDays(1).isAfter(date); date = date.plusMonths(1)) {
                PreparationCostReportDTO report = new PreparationCostReportDTO(0, YearMonth.of(date.getYear(), date.getMonthValue()).toString());

                report.setTotalPreparationCosts(orderedItemRepository
                        .sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(date, date.plusMonths(1).minusDays(1)));
                prepCosts.add(report);
            }
        } else if (type == 1) {
            long q = (from.getMonthValue() / 3) + 1;
            for (LocalDate date = from.with(IsoFields.QUARTER_OF_YEAR, q).with(IsoFields.DAY_OF_QUARTER, 1L); to.plusDays(1).isAfter(date); date = date.plusMonths(3)) {
                PreparationCostReportDTO report = new PreparationCostReportDTO(0, "Q" + date.get(IsoFields.QUARTER_OF_YEAR) + " - " + date.getYear());

                int sum = 0;
                for (int i = 0; i <= 2; i++) {

                    if (date.plusMonths(i + 1).minusDays(1).isAfter(to.withDayOfMonth(1).plusMonths(1).minusDays(1))) break;
                    sum += orderedItemRepository
                            .sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(date.plusMonths(i), date.plusMonths(i + 1).minusDays(1));
                }
                report.setTotalPreparationCosts(sum);
                prepCosts.add(report);
            }
        } else {
            for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusYears(1)) {
                PreparationCostReportDTO report = new PreparationCostReportDTO(0, Integer.toString(date.getYear()));

                report.setTotalPreparationCosts(orderedItemRepository
                        .sumPreparationCostsByOrderedItemStatusNotOrderedAndOrderDateBetween(date, date.plusYears(1).minusDays(1)));

                prepCosts.add(report);
            }
        }
        return prepCosts;
    }

    public List<PaychecksReportDTO> getPaycheckReport(int type) {

        Optional<Paychecks> minPaycheck = paycheckRepository.findTopByOrderByDateFromAsc();

        List<PaychecksReportDTO> paychecks = new ArrayList<>();

        //No paychecks in system
        if (!minPaycheck.isPresent())
            return paychecks;

        LocalDate from = minPaycheck.get().getDateFrom();
        LocalDate to = YearMonth.now().minusMonths(1).atEndOfMonth();

        if (type == 0) {
            for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusMonths(1)) {
                PaychecksReportDTO report = new PaychecksReportDTO(0, YearMonth.of(date.getYear(), date.getMonthValue()).toString());

                report.setTotalPaychecks(paycheckRepository
                        .sumTotalPaychecksAndDateBetween(date, date.plusMonths(1).minusDays(1)));
                paychecks.add(report);
            }
        } else if (type == 1) {
            long q = (from.getMonthValue() / 3);
            for (LocalDate date = from.with(IsoFields.QUARTER_OF_YEAR, q).with(IsoFields.DAY_OF_QUARTER, 1L); to.plusDays(1).isAfter(date); date = date.plusMonths(3)) {
                PaychecksReportDTO report = new PaychecksReportDTO(0, "Q" + date.get(IsoFields.QUARTER_OF_YEAR) + " - " + date.getYear());

                int sum = 0;
                for (int i = 0; i <= 2; i++) {

                    if (date.plusMonths(i + 1).minusDays(1).isAfter(to)) break;
                    sum += paycheckRepository
                            .sumTotalPaychecksAndDateBetween(date.plusMonths(i), date.plusMonths(i + 1).minusDays(1));
                }

                report.setTotalPaychecks(sum);
                paychecks.add(report);
            }
        } else {
            for (LocalDate date = from; to.plusDays(1).isAfter(date); date = date.plusYears(1)) {
                PaychecksReportDTO report = new PaychecksReportDTO(0, Integer.toString(date.getYear()));

                int sum = 0;
                for (int i = 0; i <= 11; i++) {
                    if (date.plusMonths(i + 1).minusDays(1).isAfter(to)) break;
                    sum += paycheckRepository
                            .sumTotalPaychecksAndDateBetween(date.plusMonths(i), date.plusMonths(i + 1).minusDays(1));
                }

                report.setTotalPaychecks(sum);
                paychecks.add(report);
            }
        }

        return paychecks;
    }
}

