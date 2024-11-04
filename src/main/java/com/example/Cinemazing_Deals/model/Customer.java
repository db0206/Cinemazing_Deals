package com.example.Cinemazing_Deals.model;

import jakarta.persistence.*;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    @Singular
    private List<Coupon> coupons;

    public Customer() {
        this.coupons = new ArrayList<>();
    }

    public Customer(Long customerId, String firstName, String lastName, String email, String password) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    public Customer(Long customerId, String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
