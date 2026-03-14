package com.example.ordermanagement.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {

    @Id
    private Long statusId;

    private String statusName;

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

   
	
}
