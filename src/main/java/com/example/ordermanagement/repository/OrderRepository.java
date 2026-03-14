package com.example.ordermanagement.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.example.ordermanagement.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ================= TODAY ORDERS COUNT =================
    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate = CURRENT_DATE")
    long countTodayOrders();

    // ================= TODAY REVENUE =================
    @Query("""
           SELECT SUM(o.quantity * 40)
           FROM Order o
           WHERE o.orderDate = CURRENT_DATE
           AND o.paymentMode.mode != 'Unpaid'
           """)
    Double todayRevenue();

    // ================= PAID / UNPAID =================
    @Query("""
           SELECT COUNT(o)
           FROM Order o
           WHERE o.paymentMode.mode ='Gpay'
           """)
    long countPaidOrders();

    @Query("""
           SELECT COUNT(o)
           FROM Order o
           WHERE o.paymentMode.mode = 'Unpaid'
           """)
    long countUnpaidOrders();
    
    @Query("""
            SELECT COUNT(o)
            FROM Order o
            WHERE o.paymentMode.mode = 'Cash'
            """)
     long countCashOrders();

    // ================= WEEKLY ORDERS =================
    @Query(value = """
        SELECT DATE(order_date) AS order_date, COUNT(*) AS total
        FROM order_master
        WHERE order_date >= CURDATE() - INTERVAL 6 DAY
        GROUP BY DATE(order_date)
        ORDER BY order_date
        """, nativeQuery = true)
    List<Object[]> getLast7DaysOrders();

    // ================= TODAY ORDERS LIST =================
    @Query("SELECT o FROM Order o WHERE o.orderDate = CURRENT_DATE")
    List<Order> findTodayOrders();

    // ================= NORMAL BUSINESS METHODS =================

    boolean existsByCustomer_CustIdAndOrderDate(String custId, LocalDate date);

    List<Order> findByOrderDate(LocalDate date);

    List<Order> findByOrderDateBetween(LocalDate start, LocalDate end);

    List<Order> findByOrderDateAndStatus_StatusId(LocalDate date, Long statusId);

    List<Order> findByOrderDateAndStatus_StatusIdAndAssignedUser_Username(
            LocalDate date,
            Long statusId,
            String username
    );
    List<Order> findByCustomer_CustIdOrderByOrderDateDesc(String custId);

}
