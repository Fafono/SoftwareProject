package com.foodfactory.food.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public Long countById(Long id);
}
