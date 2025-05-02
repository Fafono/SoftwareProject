package com.foodfactory.food.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Street is required")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    @Column(nullable = false)
    private String street;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 255, message = "City must be between 2 and 255 characters")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Province is required")
    @Size(min = 2, max = 255, message = "Province must be between 2 and 255 characters")
    @Column(nullable = false)
    private String province;

    @NotBlank(message = "Zip code is required")
    @Size(min = 2, max = 255, message = "Zip code must be between 2 and 255 characters")
    @Column(nullable = false)
    private String zip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
