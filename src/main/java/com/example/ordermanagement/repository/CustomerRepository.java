package com.example.ordermanagement.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ordermanagement.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	List<Customer> findByCustIdContainingIgnoreCase(String custId);
    Optional<Customer> findByCustId(String custId);
    
    boolean existsByCustId(Long custId);
    long count();
	 
	 List<Customer> findBySocietyContainingIgnoreCaseOrPhoneNoContaining(
	            String custId, String phoneNo);
}
