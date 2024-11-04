package com.example.Cinemazing_Deals.services;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Coupon;
import com.example.Cinemazing_Deals.security.LoginResponse;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    LoginResponse login(String email, String password) throws CouponSystemException;

    Coupon addCoupon(UUID token, Coupon coupon) throws CouponSystemException;

    void updateCoupon(UUID token, Coupon coupon) throws CouponSystemException;

    void deleteCoupon(UUID token, Long couponId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(UUID token) throws CouponSystemException;

    List<Coupon> getCompanyCouponsByCategory(UUID token, Category category) throws CouponSystemException;

    List<Coupon> getCompanyCouponsByPrice(UUID token, Double price) throws CouponSystemException;

    Company getCompanyDetails(UUID token) throws CouponSystemException;
}
