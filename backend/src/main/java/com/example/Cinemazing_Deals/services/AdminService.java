package com.example.Cinemazing_Deals.services;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.security.LoginResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    LoginResponse login(String email, String password) throws CouponSystemException;

    Company addCompany(UUID token, Company company) throws CouponSystemException;

    void updateCompany(UUID token, Company company) throws CouponSystemException;

    void deleteCompany(UUID token, Long companyId) throws CouponSystemException;

    List<Company> getAllCompanies(UUID token) throws CouponSystemException;

    Company getOneCompany(UUID token, Long companyId) throws CouponSystemException;

    Customer addCustomer(UUID token, Customer customer) throws CouponSystemException;

    void updateCustomer(UUID token, Customer customer) throws CouponSystemException;

    void deleteCustomer(UUID token, Long customerId) throws CouponSystemException;

    List<Customer> getAllCustomers(UUID token) throws CouponSystemException;

    Customer getOneCustomer(UUID token, Long customerId) throws CouponSystemException;


}
