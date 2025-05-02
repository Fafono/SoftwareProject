package com.foodfactory.food.services;

import com.foodfactory.food.exception.CustomerNotFoundException;
import com.foodfactory.food.model.Customer;
import com.foodfactory.food.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;


    public List<Customer> listAll() {
        return repo.findAll();
    }

    public void save(Customer user) {
        repo.save(user);
    }

    public Customer get(Long id) throws CustomerNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

    }

    public  void delete(Long id) throws CustomerNotFoundException {
        Long count = repo.countById(id);
       if(!repo.existsById(id)) {
           throw new CustomerNotFoundException("Customer not found");
       }
        repo.deleteById(id);
    }
}
