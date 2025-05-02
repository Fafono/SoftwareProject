package com.foodfactory.food.controller;

import com.foodfactory.food.exception.CustomerNotFoundException;
import com.foodfactory.food.model.*;
import com.foodfactory.food.services.AuthenticationService;
import com.foodfactory.food.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/customers")
    public String showUsers(Model model) {
        List<Customer> listUsers = service.listAll();
        model.addAttribute("listCustomers", listUsers);
        return "customers";
    }

    @GetMapping("/customers/new")
    public String showNewForm(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setContacts(Collections.singletonList(new Contacts()));
        registerDTO.setAddress(new Address());
        model.addAttribute("customer", registerDTO);
        model.addAttribute("pageTitle", "Add New Customer");
        return "register_form";
    }


    @PostMapping("/customers/save")
    public String saveUser(@Valid RegisterDTO registerDTO,BindingResult result, RedirectAttributes ra) {
        if(result.hasErrors()) {
            return "register_form";
        }
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "confirmPassword.invalid");
        }


        authenticationService.register(registerDTO);
        return "redirect:/menu_interface";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Customer user = service.get(id);
            RegisterDTO registerDTO = getRegisterDTO(user);

            model.addAttribute("customer", registerDTO);
            model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
            return "register_form";
        }
        catch (CustomerNotFoundException e){
            ra.addFlashAttribute("message", "Customer added successfully");
            return "redirect:/customers";
        }
    }

    private static RegisterDTO getRegisterDTO(Customer user) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setFirstName(user.getFirstName());
        registerDTO.setLastName(user.getLastName());
        registerDTO.setEmail(user.getEmail());
        registerDTO.setPassword(user.getPassword());
       registerDTO.setRole(Role.USER);
        registerDTO.setAddress(user.getAddress());
        registerDTO.setContacts(user.getContactsList());
        if(registerDTO.getContacts().isEmpty()) {
            registerDTO.setContacts(Collections.singletonList(new Contacts()));
        }
        return registerDTO;
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);

        }
        catch (CustomerNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/customers";
    }
}
