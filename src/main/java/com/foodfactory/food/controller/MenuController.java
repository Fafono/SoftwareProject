package com.foodfactory.food.controller;

import com.foodfactory.food.model.Cart;
import com.foodfactory.food.model.CartItem;
import com.foodfactory.food.model.Menu;
import com.foodfactory.food.repository.MenuRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    private MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/menu_interface")
    public String showMenuInterface(Model model, HttpSession session) {
        List<Menu> menuItems = menuRepository.findAll();
        Set<String> categories = menuItems.stream()
                .map(Menu::getCategory)
                .filter(category -> category != null && !category.isEmpty())
                .collect(Collectors.toSet());
        categories.add("All");

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("menuItems", menuItems);
        model.addAttribute("categories", categories);
        model.addAttribute("cart", cart);
        return "menu_interface";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("id") Long id, @RequestParam("quantity") Integer quantity, HttpSession session) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found: " + id));
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        CartItem cartItem = new CartItem(menu.getQuantity(),menu.getDescription(), menu.getPrice(), menu.getCategory(), menu.getItem(), menu.getId());
        cart.addItem(cartItem);
        return "redirect:/menu_interface";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("id") Long id, @RequestParam("quantity") Integer quantity, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.updateQuantity(id, quantity);
        }
        return "redirect:/menu_interface";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("id") Long id, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeItem(id);
        }
        return "redirect:/menu_interface";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.clearCart();
        }
        return "redirect:/menu_interface";
    }

    @GetMapping("/cart/count")
    @ResponseBody
    public String getCartCount(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        return cart != null ? String.valueOf(cart.getCartItems().size()) : "0";
    }


}
