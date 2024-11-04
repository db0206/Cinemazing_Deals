package com.example.Cinemazing_Deals.services;

import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Customer;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    Company addCompany(UUID token, Company company);

    void updateCompany(UUID token, Company company);

    void deleteCompany(UUID token, Long companyId);

    List<Company> getAllCompanies(UUID token);

    Company getOneCompany(UUID token, Long companyId);

    Customer addCustomer(UUID token, Customer customer);

    void updateCustomer(UUID token, Customer customer);

    void deleteCustomer(UUID token, Long customerId);

    List<Customer> getAllCustomers(UUID token);

    Customer getOneCustomer(UUID token, Long customerId);
}
