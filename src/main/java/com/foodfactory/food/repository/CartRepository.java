package com.foodfactory.food.repository;

import com.foodfactory.food.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    public Long countById(Long id);
}
