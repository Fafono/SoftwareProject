package com.foodfactory.food.repository;

import com.foodfactory.food.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public Long countById(Long id);
}
