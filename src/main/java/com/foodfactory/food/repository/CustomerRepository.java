package com.foodfactory.food.repository;

import com.foodfactory.food.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Long countById(Long id);

    Optional<Customer> findByEmail(String email);
}
