package com.example.ordermanagement.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
@Entity
@Table(
    name = "customer_master",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cust_id"})
    }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String society;

    @Column(name = "building_no")
    private String building;

    @Column(name = "room_no")
    private String roomNo;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "cust_id", unique = true)
    private String custId;

    private String status;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    /* ================= AUTO VALUES ================= */

    @PrePersist
    public void prePersist() {

        // if NOT passed from Postman → auto set
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }

        if (this.status == null) {
            this.status = "active";
        }

        if (this.building != null && this.roomNo != null) {
            this.custId = this.building + "_" + this.roomNo;
        }
    }

    /* ================= GETTERS & SETTERS ================= */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCustId() {
        return custId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
