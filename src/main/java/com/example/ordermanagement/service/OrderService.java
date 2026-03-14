package com.example.ordermanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.repository.OrderRepository;
import com.example.ordermanagement.repository.StatusRepository;
import com.example.ordermanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private static final Long PENDING = 1L;
    private static final Long PROCESS = 2L;
    private static final Long COMPLETE = 3L;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentModeService paymentModeService;

    /* ================= GET ALL ================= */
    public List<Order> all() {
        return orderRepository.findAll();
    }

    /* ================= GET TODAY ORDERS ================= */
    public List<Order> getTodayOrders() {
        return orderRepository.findTodayOrders();
    }

    /* ================= GET ORDER BY ID ================= */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /* ================= SAVE ORDER WITH DUPLICATE CHECK ================= */
    public Order save(Order order) {

        Customer customer = order.getCustomer();

        if (customer == null) {
            throw new RuntimeException("Customer required");
        }

        String custId = customer.getCustId();
        LocalDate today = LocalDate.now();

        boolean exists =
                orderRepository.existsByCustomer_CustIdAndOrderDate(
                        custId,
                        today
                );

        if (exists) {
            throw new RuntimeException(
                    "Customer already placed order today"
            );
        }

        order.setOrderDate(today);

        return orderRepository.save(order);
    }

    /* ================= DELETE ================= */
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    /* ================= CHANGE STATUS ================= */
    @Transactional
    public Order changeOrderStatus(Long orderId,
                                   Long paymentModeId) {

        Order order = getOrderById(orderId);

        Long currentStatus =
                order.getStatus().getStatusId();

        /* ================= PENDING → PROCESS ================= */
        if (currentStatus.equals(PENDING)) {

            order.setStatus(
                    statusRepository.getReferenceById(PROCESS)
            );

            String username =
                    SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getName();

            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            order.setAssignedUser(user);
        }

        /* ================= PROCESS → COMPLETE ================= */
        else if (currentStatus.equals(PROCESS)) {

            if (paymentModeId == null) {
                throw new IllegalArgumentException(
                        "paymentModeId required"
                );
            }

            order.setStatus(
                    statusRepository.getReferenceById(COMPLETE)
            );

            order.setPaymentMode(
                    paymentModeService.getById(paymentModeId)
            );
        }

        /* ================= COMPLETE → UPDATE PAYMENT ONLY ================= */
        else if (currentStatus.equals(COMPLETE)) {

            if (paymentModeId == null) {
                throw new IllegalArgumentException(
                        "paymentModeId required"
                );
            }

            // ONLY update payment
            order.setPaymentMode(
                    paymentModeService.getById(paymentModeId)
            );
        }

        return orderRepository.save(order);
    }

    /* ================= GET BY STATUS ================= */
    public List<Order> getOrdersByStatus(Long statusId) {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        LocalDate today = LocalDate.now();

        // PENDING → ALL USERS
        if (statusId.equals(PENDING)) {
            return orderRepository
                    .findByOrderDateAndStatus_StatusId(
                            today,
                            PENDING
                    );
        }

        // PROCESS / COMPLETE → ASSIGNED USER
        return orderRepository
                .findByOrderDateAndStatus_StatusIdAndAssignedUser_Username(
                        today,
                        statusId,
                        username
                );
    }
    public List<Order> getWeeklyOrders() {

        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(6);

        return orderRepository.findByOrderDateBetween(lastWeek, today);
    }

//    public void sendUnpaidWhatsapp(Long orderId) {
//
//        Order order = getOrderById(orderId);
//
//        if (order.getPaymentMode() != null) {
//            throw new RuntimeException("Order already paid");
//        }
//
//        String mobile =
//                order.getCustomer().getPhoneNo();
//
//        String scannerUrl =
//                "https://png.pngtree.com/png-clipart/20190630/original/pngtree-img-file-document-icon-png-image_4165155.jpg";
//
//        whatsAppService.sendUnpaidOrderMessage(
//                mobile,
//                String.valueOf(order.getOrderId()),
//                10,
//                scannerUrl
//        );
//    }
    public List<Order> getCustomerOrderHistory(String custId) {
        return orderRepository
                .findByCustomer_CustIdOrderByOrderDateDesc(custId);
    }

}
