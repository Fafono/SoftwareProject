package com.foodfactory.food.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;


    public List<Customer> listAll() {
        return (List<Customer>) repo.findAll();
    }

    public void save(Customer user) {
        repo.save(user);
    }

    public Customer get(Long id) throws CustomerNotFoundException {
        Optional<Customer> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CustomerNotFoundException("Could not find any customer ID: " + id);
    }

    public  void delete(Long id) throws CustomerNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count == 0){
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }
        repo.deleteById(id);
    }
}
