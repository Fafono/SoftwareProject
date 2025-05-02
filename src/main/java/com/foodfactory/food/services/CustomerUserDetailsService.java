package com.foodfactory.food.services;

import com.foodfactory.food.model.Customer;
import com.foodfactory.food.model.Role;
import com.foodfactory.food.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles(String.valueOf(Role.USER))
                .build();
    }
}
