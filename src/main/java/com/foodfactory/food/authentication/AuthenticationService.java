package com.foodfactory.food.authentication;

import com.foodfactory.food.customer.Customer;
import com.foodfactory.food.customer.CustomerRepository;
import com.foodfactory.food.customer.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Customer request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRole(request.getRole());
        customerRepository.save(customer);

        String token = jwtService.generateToken(customer);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(Customer request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Optional<Customer> customer = Optional.of(customerRepository.findByEmail(request.getEmail()).orElseThrow());
        String token = jwtService.generateToken(customer.get());
        return new AuthenticationResponse(token);
    }
}
