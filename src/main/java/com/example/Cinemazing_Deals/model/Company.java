package com.example.Cinemazing_Deals.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Singular;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String password;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @Singular
    private List<Coupon> coupons;

    public Company(){

    }

    public Company(Long companyId, String name, String email, String password) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    public Company(Long companyId, String name, String email, String password, List<Coupon> coupons) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    public Company(String name, String email, String password, List<Coupon> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Company{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
