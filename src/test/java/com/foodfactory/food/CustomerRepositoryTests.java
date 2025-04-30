package com.foodfactory.food;

import com.foodfactory.food.customer.Customer;
import com.foodfactory.food.customer.CustomerRepository;
import com.foodfactory.food.personalInfo.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

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
        user.setLastName("Kani");
        user.setEmail("Kani45hg@gmail.com");
        user.setPassword("1436");
        user.setId(4);
        Address address = new Address();
        address.setId(4);
        address.setStreet("Kani45hg");
        address.setCity("Kani");
        address.setProvince("Kani45hg");
        address.setZip("1436");
        user.setAddress(address);

        Customer savedUser = repo.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);
    }

    @Test
    public void testListAll() {
        Iterable<Customer> users = repo.findAll();
        Assertions.assertTrue(users.iterator().hasNext());

        for (Customer user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Long userId = 1L;
        Optional<Customer> optionalUser = repo.findById(userId);
        Customer user = optionalUser.get();
        user.setPassword("123456");
        repo.save(user);

        Customer updatedUser = repo.findById(userId).get();
        Assertions.assertEquals("123456", updatedUser.getPassword());
    }

    @Test
    public void testGet() {
        Long userId = 1L;
        Optional<Customer> optionalUser = repo.findById(userId);
        Assertions.assertTrue(optionalUser.isPresent());
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Long userId = 1L;
        repo.deleteById(userId);
        Optional<Customer> optionalUser = repo.findById(userId);
        Assertions.assertFalse(optionalUser.isPresent());

    }
}
