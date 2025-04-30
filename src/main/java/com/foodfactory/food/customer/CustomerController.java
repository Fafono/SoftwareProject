package com.foodfactory.food.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/customers")
    public String showUsers(Model model) {
        List<Customer> listUsers = service.listAll();
        model.addAttribute("listCustomers", listUsers);
        return "customers";
    }

    @GetMapping("/customers/new")
    public String showNewform(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "Add New Customer");
        return "customer_form";
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
