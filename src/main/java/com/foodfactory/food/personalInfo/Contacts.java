package com.foodfactory.food.personalInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Contacts {
    @Id
    private int id;
    private Integer number;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
