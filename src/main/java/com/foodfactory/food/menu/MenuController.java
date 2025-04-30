package com.foodfactory.food.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private MenuService service;

    @GetMapping("/menu")
    public String showUsers(Model model) {
        List<Menu> listUsers = service.listAll();
        model.addAttribute("listItems", listUsers);
        return "menu";
    }

    @GetMapping("/customer_menu")
    public String showMenuInterface(Model model) {
        List<Menu> listUsers = service.listAll();
        model.addAttribute("listItems", listUsers);
        return "customer_menu";
    }

    @GetMapping("/menu/new")
    public String showNewForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("pageTitle", "Add New Item");
        return "menu_form";
    }

    @PostMapping("/menu/save")
    public String saveUser(Menu user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "Item added successfully");

        return "redirect:/menu";
    }

    @GetMapping("/menu/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Menu user = service.get(id);
            model.addAttribute("item", user);
            model.addAttribute("pageTitle", "Edit Item (ID: " + id + ")");
            return "menu_form";
        }
        catch (MenuNotFoundException e){
            ra.addFlashAttribute("message", "Item added successfully");
            return "redirect:/menu";
        }
    }

    @GetMapping("/menu/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);

        }
        catch (MenuNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/menu";
    }


}
