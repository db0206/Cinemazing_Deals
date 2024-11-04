package com.example.Cinemazing_Deals.services.impl;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Coupon;
import com.example.Cinemazing_Deals.model.Customer;
import com.example.Cinemazing_Deals.repositories.CouponRepository;
import com.example.Cinemazing_Deals.repositories.CustomerRepository;
import com.example.Cinemazing_Deals.security.ClientType;
import com.example.Cinemazing_Deals.security.LoginResponse;
import com.example.Cinemazing_Deals.security.TokenInformation;
import com.example.Cinemazing_Deals.security.TokenManager;
import com.example.Cinemazing_Deals.services.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public LoginResponse login(String email, String password) throws CouponSystemException {

        Customer customerFromDb = customerRepository.findByEmailAndPassword(email, password);

        if (customerFromDb == null){
            logger.error("customer login failed because email or password are wrong");
            throw new CouponSystemException("email or password is wrong");
        }
        LocalDateTime expireToken = LocalDateTime.now().plusHours(24);
        TokenInformation tokenInformation = new TokenInformation(customerFromDb.getCustomerId(), expireToken, ClientType.CUSTOMER);
        UUID token = tokenManager.addToken(tokenInformation);
        logger.info("customer login successful. Welcome Customer!");
        return new LoginResponse(token, email, customerFromDb.getFirstName(), ClientType.CUSTOMER, expireToken);
    }

    @Override
    public void purchaseCoupon(UUID token, Long couponId) throws CouponSystemException {
        Long customerId = tokenManager.validateToken(token, ClientType.CUSTOMER);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)){
            throw new CouponSystemException("Customer does not exist");
        }

        if (!couponRepository.existsById(couponId)){
            throw new CouponSystemException("Coupon does not exist");
        }

        if (couponRepository.alreadyPurchased(customerId, couponId)==1){
            throw new CouponSystemException("This coupon has already been purchased by this customer");
        }

        Coupon coupon = couponRepository.getOneCoupon(couponId);

        if (coupon.getAmount() == 0){
            throw new CouponSystemException("Coupon is out of stock");
        }

        if (LocalDate.now().isAfter(coupon.getEndDate().toLocalDate())){
            throw new CouponSystemException("Coupon has expired");
        }

        coupon.setAmount(coupon.getAmount()-1);
        couponRepository.save(coupon);
        couponRepository.purchaseCoupon(customerId, couponId);
        logger.info("Coupon purchased successfully");
    }

    @Override
    public List<Coupon> getCustomerCoupons(UUID token) throws CouponSystemException {
        Long customerId = tokenManager.validateToken(token, ClientType.CUSTOMER);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)){
            throw new CouponSystemException("Customer does not exist");
        }
        List<Coupon> coupons = couponRepository.getCustomerCoupons(customerId);
        logger.info("Customer coupons acquired");
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsByCategory(UUID token, Category category) throws CouponSystemException {
        Long customerId = tokenManager.validateToken(token, ClientType.CUSTOMER);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)){
            throw new CouponSystemException("Customer does not exist");
        }
        List<Coupon> coupons = couponRepository.getCustomerCouponsByCategory(customerId, category.getName());
        logger.info("Customer coupons by category acquired: " + category);
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsByPrice(UUID token, Double price) throws CouponSystemException {
        Long customerId = tokenManager.validateToken(token, ClientType.CUSTOMER);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)){
            throw new CouponSystemException("Customer does not exist");
        }
        List<Coupon> coupons = couponRepository.getCustomerCouponsByPrice(customerId, price);
        logger.info("Customer coupons by max price acquired: " + price);
        return coupons;
    }

    @Override
    public Customer getCustomerDetails(UUID token) throws CouponSystemException {
        Long customerId = tokenManager.validateToken(token, ClientType.CUSTOMER);
        logger.info("token validated");
        if (!customerRepository.existsById(customerId)){
            throw new CouponSystemException("Customer does not exist");
        }

        Customer customer = customerRepository.getOneCustomer(customerId);
        logger.info("Customer details acquired");
        return customer;
    }

    @Override
    public List<Coupon> getAllCoupons(UUID token) throws CouponSystemException {
        Long customerId = tokenManager.validateToken(token, ClientType.CUSTOMER);
        logger.info("token validated");
        logger.info("loading tokens");
        return (List<Coupon>) couponRepository.findAll();
    }
}
