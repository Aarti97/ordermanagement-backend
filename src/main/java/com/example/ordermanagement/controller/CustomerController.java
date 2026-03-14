package com.example.ordermanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ordermanagement.dto.ApiResponse;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /* ===== CREATE ===== */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Customer customer) {

        Customer saved = service.save(customer);

        return ResponseEntity.ok(
            Map.of("message", "Customer added successfully", "data", saved)
        );
    }



    /* ===== ALL ===== */
    @GetMapping
    public List<Customer> all() {
        return service.all();
    }

    /* ===== SEARCH ===== */
    @GetMapping("/search")
    public List<Customer> search(@RequestParam String q) {
        return service.search(q);
    }

    /* ===== GET BY custId ===== */
    @GetMapping("/{custId}")
    public Customer byCustId(@PathVariable String custId) {
        return service.getByCustId(custId);
    }
    
    /* ===== DELETE ===== */
    @DeleteMapping("/{custId}")
    public ResponseEntity<?> delete(@PathVariable String custId) {

        service.deleteByCustId(custId);

        return ResponseEntity.ok(
            Map.of("message", "Customer deleted successfully")
        );
    }
    
    @PutMapping("/{custId}")
    public ResponseEntity<?> update(
            @PathVariable String custId,
            @RequestBody Customer customer) {

        Customer updated = service.updateByCustId(custId, customer);

        return ResponseEntity.ok(
            Map.of("message", "Customer updated successfully", "data", updated)
        );
    }
}
