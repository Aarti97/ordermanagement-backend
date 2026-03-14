package com.example.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordermanagement.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}