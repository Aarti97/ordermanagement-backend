package com.example.ordermanagement.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.entity.Status;
import com.example.ordermanagement.repository.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private StatusRepository repository;

    public List<Status> getAll() {
        return repository.findAll();
    }
    public Status getStatusById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("OrderStatus not found"));
    }
}