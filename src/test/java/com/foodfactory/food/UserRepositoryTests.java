package com.foodfactory.food;

import com.foodfactory.food.user.User;
import com.foodfactory.food.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setFirstName("Johns");
        user.setLastName("Manday");
        user.setEmail("johnmandays@gmail.com");
        user.setPhone("1234567890");
        user.setPassword("1436");
        user.setSalary(55000.0);
        user.setGender("M");
        user.setDeptNo(20L);
        user.setDepartment("Food");
        user.setJob("IT");
        user.setHireDate(new Date());
        User savedUser = repo.save(user);


        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);
    }


    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertTrue(users.iterator().hasNext());

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Long userId = 10L;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("123456");
        user.setHireDate(new SimpleDateFormat("10-02-2010").get2DigitYearStart());
        user.setSalary(55000.0);
        user.setDeptNo(10L);
        user.setJob("IT");


        User updatedUser = repo.findById(userId).get();
        Assertions.assertEquals("123456", updatedUser.getPassword());
    }

    @Test
    public void testGet() {
        Long userId = 10L;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertTrue(optionalUser.isPresent());
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Long userId = 1L;
        repo.deleteById(userId);
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertFalse(optionalUser.isPresent());

    }
}
