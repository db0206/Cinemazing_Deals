package com.example.Cinemazing_Deals.services.impl;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.repositories.CompanyRepository;
import com.example.Cinemazing_Deals.repositories.CouponRepository;
import com.example.Cinemazing_Deals.repositories.CustomerRepository;
import com.example.Cinemazing_Deals.security.ClientType;
import com.example.Cinemazing_Deals.security.LoginResponse;
import com.example.Cinemazing_Deals.security.TokenInformation;
import com.example.Cinemazing_Deals.security.TokenManager;
import com.example.Cinemazing_Deals.services.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public LoginResponse login(String email, String password) throws CouponSystemException {
        if (!(email.equalsIgnoreCase("admin@admin.com") && password.equals("admin"))) {
            logger.error("login failed because email or password is wrong");
            throw new CouponSystemException("email or password is wrong");
        }
        LocalDateTime expireToken = LocalDateTime.now().plusHours(24);
        TokenInformation tokenInformation = new TokenInformation(-1L, expireToken, ClientType.ADMIN);
        UUID token = tokenManager.addToken(tokenInformation);
        logger.info("Login Successful. Welcome Admin!");
        return new LoginResponse(token, email, "Admin", ClientType.ADMIN, expireToken);
    }

    @Override
    public Company addCompany(UUID token, Company company) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (companyRepository.existsByName(company.getName())) {
            logger.error("Company name already exists");
            throw new CouponSystemException("Company name already exists");
        }

        if (companyRepository.existsByEmail(company.getEmail())) {
            logger.error("Company email already exists");
            throw new CouponSystemException("Company email already exists");
        }
        companyRepository.save(company);
        logger.info("company added successfully: " + company);
        return company;
    }

    @Override
    public void updateCompany(UUID token, Company company) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (!companyRepository.existsById(company.getCompanyId())) {
            logger.error("company does not exist");
            throw new CouponSystemException("Company does not exist");
        }

        Company companyFromDB = companyRepository.getOneCompany(company.getCompanyId());

        if (!companyFromDB.getName().equals(company.getName())) {
            logger.error("company name cannot be changed");
            throw new CouponSystemException("Company name cannot be changed");
        }

        companyFromDB.setEmail(company.getEmail());
        companyFromDB.setPassword(company.getPassword());
        companyRepository.save(companyFromDB);
        logger.info("company updated successfully: " + companyFromDB);
    }

    @Override
    public void deleteCompany(UUID token, Long companyId) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            logger.error("company does not exist");
            throw new CouponSystemException("Company does not exist");
        }

        couponRepository.deletePurchasedCoupons(companyId);
        logger.info("created coupons deleted successfully");
        companyRepository.deleteById(companyId);
        logger.info("company deleted successfully");
    }

    @Override
    public List<Company> getAllCompanies(UUID token) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        List<Company> companies = (List<Company>) companyRepository.findAll();
        logger.info("received all companies:" + companies);
        return companies;
    }

    @Override
    public Company getOneCompany(UUID token, Long companyId) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            logger.error("company does not exist");
            throw new CouponSystemException("Company does not exist");
        }

        Company company = companyRepository.getOneCompany(companyId);
        logger.info("Received company: " + company);
        return company;
    }

    @Override
    public Customer addCustomer(UUID token, Customer customer) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (customerRepository.existsByEmail(customer.getEmail())) {
            logger.error("Customer email already exists");
            throw new CouponSystemException("Customer email already exists");
        }
        customerRepository.save(customer);
        logger.info("customer added successfully: " + customer);
        return customer;
    }

    @Override
    public void updateCustomer(UUID token, Customer customer) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (!customerRepository.existsById(customer.getCustomerId())) {
            logger.error("customer does not exist");
            throw new CouponSystemException("Customer does not exist");
        }

        Customer customerFromDB = customerRepository.getOneCustomer(customer.getCustomerId());

        if (customerRepository.existsByEmailWithDifferentId(customer.getEmail(), customer.getCustomerId())) {
            logger.error("customer email already exists ");
            throw new CouponSystemException("Customer email already exists");
        }

        customerFromDB.setFirstName(customer.getFirstName());
        customerFromDB.setLastName(customer.getLastName());
        customerFromDB.setEmail(customer.getEmail());
        customerFromDB.setPassword(customer.getPassword());
        customerFromDB.setCoupons(customer.getCoupons());
        customerRepository.save(customerFromDB);
        logger.info("customer updated successfully: " + customerFromDB);
    }

    @Override
    public void deleteCustomer(UUID token, Long customerId) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)) {
            logger.error("customer does not exist");
            throw new CouponSystemException("Customer does not exist");
        }
        couponRepository.deletePurchasedCouponsByCustomerId(customerId);
        logger.info("Purchased coupons deleted successfully");
        customerRepository.deleteById(customerId);
        logger.info("customer deleted successfully");
    }

    @Override
    public List<Customer> getAllCustomers(UUID token) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        logger.info("received all customers:" + customers);
        return customers;
    }

    @Override
    public Customer getOneCustomer(UUID token, Long customerId) throws CouponSystemException {
        tokenManager.validateToken(token, ClientType.ADMIN);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)) {
            logger.error("customer does not exist");
            throw new CouponSystemException("Customer does not exist");
        }

        Customer customer = customerRepository.getOneCustomer(customerId);
        logger.info("Received company: " + customer);
        return customer;
    }
}
