package com.foodfactory.food.services;

import com.foodfactory.food.exception.MenuNotFoundException;
import com.foodfactory.food.repository.MenuRepository;
import com.foodfactory.food.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository repo;

    public List<Menu> listAll() {
        return (List<Menu>) repo.findAll();
    }

    public void save(Menu user) {
        repo.save(user);
    }

    public Menu get(Long id) throws MenuNotFoundException {
        Optional<Menu> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new MenuNotFoundException("Could not find any customer ID: " + id);
    }

    public  void delete(Long id) throws MenuNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count == 0){
            throw new MenuNotFoundException("Could not find any customers with ID " + id);
        }
        repo.deleteById(id);
    }
}
