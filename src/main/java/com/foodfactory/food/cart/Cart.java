package com.foodfactory.food.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();

    public void addItem(CartItem cartItem) {
        for (CartItem cartItem1 : cartItems) {
            if (cartItem1.getId() == cartItem.getId()) {
                cartItem1.setQuantity(cartItem1.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        cartItems.add(cartItem);
    }

    public void removeItem(Long cartItem) {
        cartItems.removeIf(cartItem1 -> cartItem1.getId() == cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotal() {
        return cartItems.stream().mapToDouble(CartItem::getSubTotal).sum();
    }

    public void updateQuantity(Long id, int quantity) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getId() == id) {
                cartItem.setQuantity(quantity);
                return;
            }
        }
    }

    public void clearCart() {
        cartItems.clear();
    }
}
