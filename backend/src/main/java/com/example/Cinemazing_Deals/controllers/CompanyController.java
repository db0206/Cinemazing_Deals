package com.example.Cinemazing_Deals.controllers;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Coupon;
import com.example.Cinemazing_Deals.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/coupon")
    public Coupon addCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) throws CouponSystemException {
        return companyService.addCoupon(token, coupon);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/coupon")
    public void updateCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) throws CouponSystemException {
        companyService.updateCoupon(token, coupon);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/coupon/{couponId}")
    public void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable("couponId") Long couponId) throws CouponSystemException {
        companyService.deleteCoupon(token, couponId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/coupons")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return companyService.getCompanyCoupons(token);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/coupons-category")
    public List<Coupon> getCompanyCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSystemException {
        return companyService.getCompanyCouponsByCategory(token, category);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/coupons-price")
    public List<Coupon> getCompanyCouponsByPrice(@RequestHeader("Authorization") UUID token, @RequestParam Double price) throws CouponSystemException {
        return companyService.getCompanyCouponsByPrice(token, price);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/company-details")
    public Company getCompanyDetails(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return companyService.getCompanyDetails(token);
    }
}
