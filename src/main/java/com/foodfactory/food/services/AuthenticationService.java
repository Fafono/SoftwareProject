package com.foodfactory.food.services;

import com.foodfactory.food.model.AuthenticationResponse;
import com.foodfactory.food.model.Customer;
import com.foodfactory.food.model.RegisterDTO;
import com.foodfactory.food.model.Role;
import com.foodfactory.food.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public AuthenticationResponse register(RegisterDTO registerDTO) {

        Customer customer = new Customer();
        customer.setFirstName(registerDTO.getFirstName());
        customer.setLastName(registerDTO.getLastName());
        customer.setEmail(registerDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        customer.setRole(Role.USER);
        customer.setAddress(registerDTO.getAddress());
        customer.setContactsList(registerDTO.getContacts());
        customer.setDelivery(registerDTO.getDelivery());
        customerRepository.save(customer);



        UserDetails userDetails = User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles(String.valueOf(Role.USER))
                .build();

        String token = jwtService.generateToken(customer);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(Customer customer) {
        Customer existing = customerRepository.findByEmail(customer.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(customer.getPassword(), existing.getPassword())) {
            return new AuthenticationResponse("Login successful");
        }
        throw new RuntimeException("Invalid credentials");
    }

}
