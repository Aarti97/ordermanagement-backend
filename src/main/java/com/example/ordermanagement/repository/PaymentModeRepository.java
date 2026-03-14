package com.example.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordermanagement.entity.PaymentMode;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {
	
}

