package com.foodfactory.food.services;

import com.foodfactory.food.model.User;
import com.foodfactory.food.exception.UserNotFounException;
import com.foodfactory.food.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {

        repo.save(user);
    }

    public User get(Long id) throws UserNotFounException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFounException("Could not find any users ID: " + id);
    }

    public  void delete(Long id) throws UserNotFounException {
        Long count = repo.countById(id);
        if(count == null || count == 0){
            throw new UserNotFounException("Could not find any users with ID " + id);
        }
        repo.deleteById(id);
    }
}
