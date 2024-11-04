package com.example.Cinemazing_Deals.controllers;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/company")
    public Company addCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSystemException {
        return adminService.addCompany(token, company);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/company")
    public void updateCompany(@RequestHeader("Authorization") UUID token,  @RequestBody Company company) throws CouponSystemException {
        adminService.updateCompany(token, company);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/company/{companyId}")
    public void deleteCompany (@RequestHeader("Authorization") UUID token,  @PathVariable("companyId") Long companyId) throws CouponSystemException {
        adminService.deleteCompany(token, companyId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/companies")
    public List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CouponSystemException{
        return adminService.getAllCompanies(token);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/company/{companyId}")
    public Company getOneCompany (@RequestHeader("Authorization") UUID token,  @PathVariable("companyId") Long companyId) throws CouponSystemException {
        return adminService.getOneCompany(token, companyId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/customer")
    public Customer addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSystemException {
        return adminService.addCustomer(token, customer);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/customer")
    public void updateCustomer(@RequestHeader("Authorization") UUID token,  @RequestBody Customer customer) throws CouponSystemException {
        adminService.updateCustomer(token, customer);
    }


    //some sort of error, need to check
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer (@RequestHeader("Authorization") UUID token,  @PathVariable("customerId") Long customerId) throws CouponSystemException {
        adminService.deleteCustomer(token, customerId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CouponSystemException{
        return adminService.getAllCustomers(token);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/customer/{customerId}")
    public Customer getOneCustomer (@RequestHeader("Authorization") UUID token,  @PathVariable("customerId") Long customerId) throws CouponSystemException {
        return adminService.getOneCustomer(token, customerId);
    }
}
