package com.foodfactory.food.controller;

import com.foodfactory.food.model.Cart;
import com.foodfactory.food.model.CartItem;
import com.foodfactory.food.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService service;

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@RequestBody CartItem item, HttpSession session) {
        service.addToCart(item, session);
        Cart cart = (Cart) session.getAttribute("cart");
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id,@RequestBody CartItem item, HttpSession session) {
        service.updateCart(id, item.getQuantity());
        Cart cart = (Cart) session.getAttribute("cart");
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Cart> removeCart(@PathVariable Long id, HttpSession session) {
        service.removeFromCart(id);
        Cart cart = (Cart) session.getAttribute("cart");
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return ResponseEntity.ok(cart);
    }
}
