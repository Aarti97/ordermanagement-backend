package com.example.ordermanagement.dto;

import lombok.Data;


public class AssignOrderRequest {
    private Long assignedUserId;
    private Long statusId;
	public Long getAssignedUserId() {
		return assignedUserId;
	}
	public void setAssignedUserId(Long assignedUserId) {
		this.assignedUserId = assignedUserId;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
    
    
    
}