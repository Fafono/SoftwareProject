package com.foodfactory.food;

import com.foodfactory.food.model.Address;
import com.foodfactory.food.model.Customer;
import com.foodfactory.food.model.Role;
import com.foodfactory.food.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
    @Autowired
    private CustomerRepository repo;

    @Test
    public void testAddNew() {
        Customer user = new Customer();
        user.setFirstName("John");
        user.setLastName("Kawni");
        user.setEmail("a45h43g@gmail.com");
        user.setPassword("12355555");
        user.setRole(Role.USER);

        Address address = new Address();
        address.setStreet("Kani45hg");
        address.setCity("Kani");
        address.setProvince("Kani45hg");
        address.setZip("1436");
        user.setAddress(address);

        Customer savedUser = repo.save(user);

        assertNotNull(savedUser);
        assertTrue(savedUser.getId() > 0);
    }

    @Test
    public void testListAll() {
        Iterable<Customer> users = repo.findAll();
        assertTrue(users.iterator().hasNext());

        for (Customer user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Long userId = 4L;

        Optional<Customer> optionalUser = repo.findById(userId);
        Customer user = optionalUser.get();
        user.setContactsList(user.getContactsList());
        repo.save(user);

        Customer updatedUser = repo.findById(userId).get();
        Assertions.assertEquals("12345667583", updatedUser.getPassword());
    }

    @Test
    public void testGet() {
        Long userId = 4L;
        Optional<Customer> optionalUser = repo.findById(userId);
        assertTrue(optionalUser.isPresent());
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Long userId = 4L;
        repo.deleteById(userId);
        Optional<Customer> optionalUser = repo.findById(userId);
        Assertions.assertFalse(optionalUser.isPresent());

    }
}
