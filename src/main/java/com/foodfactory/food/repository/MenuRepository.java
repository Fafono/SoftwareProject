package com.foodfactory.food.repository;

import com.foodfactory.food.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Long countById(Long id);
}
