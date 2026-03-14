package com.example.ordermanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ordermanagement.dto.CompleteOrderRequest;
import com.example.ordermanagement.dto.OrderRequest;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.service.CustomerService;
import com.example.ordermanagement.service.OrderService;
import com.example.ordermanagement.service.PaymentModeService;
import com.example.ordermanagement.service.StatusService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final PaymentModeService paymentModeService;
    private final StatusService statusService;

    public OrderController(OrderService orderService,
                           CustomerService customerService,
                           PaymentModeService paymentModeService,
                           StatusService statusService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.paymentModeService = paymentModeService;
        this.statusService = statusService;
    }

    // ✅ ALL ORDERS
    @GetMapping
    public List<Order> all() {
        return orderService.all();
    }

    // ✅ TODAY ORDERS
    @GetMapping("/today")
    public List<Order> getTodayOrders() {
        return orderService.getTodayOrders();
    }

    // ✅ WEEKLY ORDERS (FOR DASHBOARD GRAPH)
    @GetMapping("/weekly")
    public List<Order> getWeeklyOrders() {
        return orderService.getWeeklyOrders();
    }

    // ✅ CREATE ORDER
    @PostMapping
    public Order create(@RequestBody OrderRequest req) {

        Order order = new Order();
        order.setQuantity(req.getQuantity());
        order.setOrderDate(LocalDate.now());

        order.setCustomer(customerService.getByCustId(req.getCustomerId()));
        order.setStatus(statusService.getStatusById(1L)); // PENDING
        order.setPaymentMode(paymentModeService.getById(1L)); // UNPAID

        return orderService.save(order);
    }

    // ✅ CHANGE ORDER STATUS
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody(required = false) CompleteOrderRequest req) {

        Long paymentModeId = req != null ? req.getPaymentModeId() : null;

        Order updatedOrder =
                orderService.changeOrderStatus(orderId, paymentModeId);

        return ResponseEntity.ok(updatedOrder);
    }

    // ✅ FILTER BY STATUS
    @GetMapping("/status/{statusId}")
    public List<Order> getByStatus(@PathVariable Long statusId) {
        return orderService.getOrdersByStatus(statusId);
    }
    
    @GetMapping("/customer/{custId}")
    public ResponseEntity<List<Order>> getCustomerOrders(
            @PathVariable String custId) {

        return ResponseEntity.ok(
                orderService.getCustomerOrderHistory(custId)
        );
    }

}
