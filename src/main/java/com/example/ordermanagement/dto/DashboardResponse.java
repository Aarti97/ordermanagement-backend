package com.example.ordermanagement.dto;

import java.util.List;

import lombok.Data;

@Data
public class DashboardResponse {

    private long totalCustomers;
    private long totalOrders;
    private long todayOrders;
    private double todayRevenue;

    private long paidOrders;
    private long unpaidOrders;
    private long cashOrders;
    private List<WeeklyOrderDTO> weeklyOrders;
}
