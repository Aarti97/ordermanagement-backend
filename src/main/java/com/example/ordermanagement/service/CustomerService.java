package com.example.ordermanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.repository.CustomerRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    /* ===== SAVE ===== */
 
    public Customer save(Customer c) {

        if(repo.findByCustId(c.getCustId()).isPresent()){
            throw new RuntimeException("Customer ID already exists");
        }

        return repo.save(c);
    }

    /* ===== ALL ===== */
    public List<Customer> all() {
        return repo.findAll();
    }

    public Customer getByCustId(String custId) {
        return repo.findByCustId(custId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found with custId: " + custId));
    }

    
    public List<Customer> search(String keyword) {
        return repo
            .findBySocietyContainingIgnoreCaseOrPhoneNoContaining(
                keyword, keyword);
    }
    
    public void deleteByCustId(String custId) {

        Customer customer = repo.findByCustId(custId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        repo.delete(customer);
    }
    
    public Customer updateByCustId(String custId, Customer updated) {

        Customer existing = repo.findByCustId(custId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Update fields (DO NOT change custId)
       
        existing.setPhoneNo(updated.getPhoneNo());
        existing.setStatus(updated.getStatus());

        return repo.save(existing);
    }
    
}
