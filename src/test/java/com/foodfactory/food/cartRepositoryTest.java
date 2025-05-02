package com.foodfactory.food;

import com.foodfactory.food.model.Cart;
import com.foodfactory.food.model.CartItem;
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
        cartItem1 = new CartItem(25,"fast food",25.50,"kota","hdh",1L);
        cartItem2 = new CartItem(24,"Burger",55.50,"slide","burger king",2L);
        cartItem2 = new CartItem(84,"Burger",155.50,"slide","burger king",3L);
    }

    @Test
    void testAddCartItem() {
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        Assertions.assertTrue(true);
        Assertions.assertTrue(cart.getCartItems().get(0).getId() > 0);
    }

    @Test
    void testAddCartDuplicateItem() {
        cart.addItem(cartItem1);
        CartItem duplicateItem = new CartItem(25,"fast food",25.50,"kota","hdh",1L);
        cart.addItem(duplicateItem);
        List<CartItem> cartItems = cart.getCartItems();
    }

    @Test
    void testRemoveCartItem() {
        cart.removeItem(1L);
        List<CartItem> cartItems = cart.getCartItems();
    }

    @Test
    void testClearCart() {
        cart.clearCart();
        Assertions.assertTrue(cart.getCartItems().isEmpty());
    }
}
