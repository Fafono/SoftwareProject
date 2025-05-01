package com.foodfactory.food;

import com.foodfactory.food.cart.Cart;
import com.foodfactory.food.cart.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class cartRepositoryTest {
    private Cart cart;
    private CartItem cartItem1;
    private CartItem cartItem2;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cartItem1 = new CartItem(1L, "Pizza", "hot", 25.50,2);
        cartItem2 = new CartItem(2L,"Burger","slide",25.26,2);
    }

    @Test
    void testAddCartItem() {
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        Assertions.assertTrue(cartItems.size() > 1);
        Assertions.assertTrue(cart.getCartItems().get(0).getId() > 0);
    }

    @Test
    void testAddCartDuplicateItem() {
        cart.addItem(cartItem1);
        CartItem duplicateItem = new CartItem(1L, "Pizza", "hot", 25.50,2);
        cart.addItem(duplicateItem);
        List<CartItem> cartItems = cart.getCartItems();
    }

    @Test
    void testRemoveCartItem() {
        cart.removeItem(1l);
        List<CartItem> cartItems = cart.getCartItems();
    }

    @Test
    void testClearCart() {
        cart.clearCart();
        Assertions.assertTrue(cart.getCartItems().isEmpty());
    }
}
