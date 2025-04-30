package com.foodfactory.food.cart;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {
   private static final String CART_SESSION_KEY = "cart";
    private HttpSession session;

    private Cart getCart(HttpSession session) {
       Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
       if (cart == null) {
           cart = new Cart();
           session.setAttribute(CART_SESSION_KEY, cart);
       }
       return cart;
   }

   public void addToCart(CartItem item, HttpSession session) {
       Cart cart = getCart(session);
       cart.addItem(item);
   }

   public void updateCart(Long id, Integer quantity) {
       Cart cart = getCart(session);
       cart.updateQuantity(id, quantity);
   }

   public void removeFromCart(Long id) {
       Cart cart = getCart(session);
       cart.removeItem(id);
   }

   public void cleanCart(HttpSession session) {
       Cart cart = getCart(session);
       cart.clearCart();
   }
}
