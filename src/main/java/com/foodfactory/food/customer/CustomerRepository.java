package com.foodfactory.food.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Long countById(Long id);

    Optional<Customer> findByEmail(String email);
}
