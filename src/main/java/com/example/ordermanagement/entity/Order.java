package com.example.ordermanagement.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(
        name = "order_master",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cust_id", "order_date"})
        }
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;


    @ManyToOne
    @JoinColumn(name = "payment_mode_id", nullable = false)
    private PaymentMode paymentMode;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}



	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

 
    
}
