package com.example.ordermanagement.dto;

public class OrderRequest {

    private Integer quantity;
    private String customerId;       // e.g., "L32_406"
    private Integer paymentModeId;   // must be 1, 2, or 3
  //  private Integer paymentStatusId; // must be 1 or 2

    // Getters & Setters
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public Integer getPaymentModeId() { return paymentModeId; }
    public void setPaymentModeId(Integer paymentModeId) { this.paymentModeId = paymentModeId; }

//    public Integer getPaymentStatusId() { return paymentStatusId; }
//    public void setPaymentStatusId(Integer paymentStatusId) { this.paymentStatusId = paymentStatusId; }
//}
}
