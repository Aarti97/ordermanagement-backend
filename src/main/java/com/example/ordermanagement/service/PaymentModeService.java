package com.example.ordermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.entity.PaymentMode;
import com.example.ordermanagement.repository.PaymentModeRepository;


@Service
public class PaymentModeService {

	 @Autowired
	    private PaymentModeRepository repo;

	    public PaymentMode getById(Long id) {
	        return repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("PaymentMode not found"));
	    }

	    public List<PaymentMode> getAll() {
	        return repo.findAll();
	    }
}