package com.example.Cinemazing_Deals.services;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Coupon;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.security.LoginResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    LoginResponse login(String email, String password) throws CouponSystemException;

    void purchaseCoupon(UUID token, Long couponId) throws CouponSystemException;

    List<Coupon> getCustomerCoupons(UUID token) throws CouponSystemException;

    List<Coupon> getCustomerCouponsByCategory(UUID token, Category category) throws CouponSystemException;

    List<Coupon> getCustomerCouponsByPrice(UUID token, Double price) throws CouponSystemException;

    Customer getCustomerDetails(UUID token) throws CouponSystemException;

    List<Coupon> getAllCoupons(UUID token) throws CouponSystemException;
}
