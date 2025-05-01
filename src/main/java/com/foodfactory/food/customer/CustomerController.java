package com.foodfactory.food.customer;

import com.foodfactory.food.personalInfo.RegisterDTO;
import com.foodfactory.food.personalInfo.Role;
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

import java.util.Date;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/customers")
    public String showUsers(Model model) {
        List<Customer> listUsers = service.listAll();
        model.addAttribute("listCustomers", listUsers);
        return "customers";
    }

    @GetMapping("/customers/new")
    public String showNewForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "Add New Customer");
        return "customer_form";
    }

    @PostMapping("/customers/new")
    public String processNewForm(@Valid RegisterDTO registerDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "customer_form";
        }

        Customer cust = new Customer();
        cust.setFirstName(registerDTO.getFirstName());
        cust.setLastName(registerDTO.getLastName());
        cust.setEmail(registerDTO.getEmail());
        cust.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        cust.setRole(Role.USER);
        cust.setAddress(registerDTO.getAddress());
        cust.setCreationDate(new Date());
        cust.setContactsList(registerDTO.getContacts());

        service.save(cust);
        return "redirect:/customers";
    }

    @PostMapping("/customers/save")
    public String saveUser(Customer user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "Customer added successfully");

        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Customer user = service.get(id);
            model.addAttribute("customer", user);
            model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
            return "customer_form";
        }
        catch (CustomerNotFoundException e){
            ra.addFlashAttribute("message", "Customer added successfully");
            return "redirect:/customers";
        }
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
