package com.example.ordermanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_mode")
public class PaymentMode {

    @Id
    private Long id;

    private String mode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
