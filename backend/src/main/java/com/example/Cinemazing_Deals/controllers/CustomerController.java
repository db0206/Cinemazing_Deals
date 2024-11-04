package com.example.Cinemazing_Deals.controllers;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Coupon;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/purchase-coupon/{couponId}")
    public void purchaseCoupon(@RequestHeader("Authorization") UUID token, @PathVariable("couponId") Long couponId) throws CouponSystemException {
        customerService.purchaseCoupon(token, couponId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/coupons")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return customerService.getCustomerCoupons(token);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/coupons-category")
    public List<Coupon> getCustomerCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSystemException {
        return customerService.getCustomerCouponsByCategory(token, category);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/coupons-price")
    public List<Coupon> getCustomerCouponsByPrice(@RequestHeader("Authorization") UUID token, @RequestParam Double price) throws CouponSystemException {
        return customerService.getCustomerCouponsByPrice(token, price);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/customer-details")
    public Customer getCustomerDetails(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return customerService.getCustomerDetails(token);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all-coupons")
    public List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return customerService.getAllCoupons(token);
    }
}
