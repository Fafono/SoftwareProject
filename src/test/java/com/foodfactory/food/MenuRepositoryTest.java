package com.foodfactory.food;

import com.foodfactory.food.menu.Menu;
import com.foodfactory.food.menu.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MenuRepositoryTest {
    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testAddMenu() {
        Menu menu = new Menu();
        menu.setItem("Apple");
        menu.setPrice(Double.valueOf("5"));

        Menu menu2 = menuRepository.save(menu);

        Assertions.assertNotNull(menu2);
        Assertions.assertTrue(menu2.getId() > 0);
    }

    @Test
    public void findAll() {
        Iterable<Menu> menus = menuRepository.findAll();
        Assertions.assertTrue(menus.iterator().hasNext());
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }

    @Test
    public void testUpdateMenu() {
        Long id = 1L;
        Optional<Menu> menu = menuRepository.findById(id);
        Menu menu1 = menu.get();
        menu1.setItem("Apple");
        menu1.setPrice(Double.valueOf("5"));
        Menu menu2 = menuRepository.save(menu1);
        Assertions.assertNotNull(menu2);
        Assertions.assertTrue(menu2.getId() > 0);

    }

    @Test
    public void testDeleteMenu() {
        Long id = 1L;
        menuRepository.deleteById(id);
        Optional<Menu> menu = menuRepository.findById(id);
        Assertions.assertFalse(menu.isPresent());
    }
}
