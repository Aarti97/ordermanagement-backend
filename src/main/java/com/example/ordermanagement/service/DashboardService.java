package com.example.ordermanagement.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.ordermanagement.dto.DashboardResponse;
import com.example.ordermanagement.dto.WeeklyOrderDTO;
import com.example.ordermanagement.repository.CustomerRepository;
import com.example.ordermanagement.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public DashboardResponse getDashboardData() {

        DashboardResponse res = new DashboardResponse();

        res.setTotalCustomers(customerRepository.count());
        res.setTotalOrders(orderRepository.count());
        res.setTodayOrders(orderRepository.countTodayOrders());

        Double revenue = orderRepository.todayRevenue();
        res.setTodayRevenue(revenue != null ? revenue : 0);

        res.setPaidOrders(orderRepository.countPaidOrders());
        res.setUnpaidOrders(orderRepository.countUnpaidOrders());
        res.setUnpaidOrders(orderRepository.countCashOrders());
        res.setWeeklyOrders(getWeeklyOrders());

        return res;
    }

    private List<WeeklyOrderDTO> getWeeklyOrders() {

        List<Object[]> rows = orderRepository.getLast7DaysOrders();

        Map<String, Integer> dbData = new HashMap<>();

        for (Object[] row : rows) {
            dbData.put(row[0].toString(), ((Number) row[1]).intValue());
        }

        List<WeeklyOrderDTO> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {

            LocalDate date = LocalDate.now().minusDays(i);
            String formatted = date.toString();

            result.add(new WeeklyOrderDTO(
                    formatted,
                    dbData.getOrDefault(formatted, 0)
            ));
        }

        return result;
    }
}
