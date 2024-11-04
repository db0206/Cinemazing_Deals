package com.example.Cinemazing_Deals.services.impl;

import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.services.AdminService;

import java.util.List;
import java.util.UUID;

public class AdminServiceImpl implements AdminService {
    @Override
    public Company addCompany(UUID token, Company company) {
        return null;
    }

    @Override
    public void updateCompany(UUID token, Company company) {

    }

    @Override
    public void deleteCompany(UUID token, Long companyId) {

    }

    @Override
    public List<Company> getAllCompanies(UUID token) {
        return null;
    }

    @Override
    public Company getOneCompany(UUID token, Long companyId) {
        return null;
    }

    @Override
    public Customer addCustomer(UUID token, Customer customer) {
        return null;
    }

    @Override
    public void updateCustomer(UUID token, Customer customer) {

    }

    @Override
    public void deleteCustomer(UUID token, Long customerId) {

    }

    @Override
    public List<Customer> getAllCustomers(UUID token) {
        return null;
    }

    @Override
    public Customer getOneCustomer(UUID token, Long customerId) {
        return null;
    }
}
