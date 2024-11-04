package com.example.Cinemazing_Deals.services.impl;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Coupon;
import com.example.Cinemazing_Deals.repositories.CompanyRepository;
import com.example.Cinemazing_Deals.repositories.CouponRepository;
import com.example.Cinemazing_Deals.security.ClientType;
import com.example.Cinemazing_Deals.security.LoginResponse;
import com.example.Cinemazing_Deals.security.TokenInformation;
import com.example.Cinemazing_Deals.security.TokenManager;
import com.example.Cinemazing_Deals.services.CompanyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LogManager.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public LoginResponse login(String email, String password) throws CouponSystemException {

        Company companyFromDb = companyRepository.findByEmailAndPassword(email, password);

        if (companyFromDb == null) {
            logger.error("company login failed because email or password are wrong");
            throw new CouponSystemException("email or password is wrong");
        }

        LocalDateTime expireToken = LocalDateTime.now().plusHours(24);
        TokenInformation tokenInformation = new TokenInformation(companyFromDb.getCompanyId(), expireToken, ClientType.COMPANY);
        UUID token = tokenManager.addToken(tokenInformation);
        logger.info("company login successful. Welcome Company!");
        return new LoginResponse(token, email, companyFromDb.getName(), ClientType.COMPANY, expireToken);
    }

    @Override
    public Coupon addCoupon(UUID token, Coupon coupon) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            logger.error("company doesn't exist");
            throw new CouponSystemException("Company doesn't exist");
        }

        Company companyFromDB = companyRepository.getOneCompany(companyId);

        if (couponRepository.existsByTitleAndCompany(coupon.getTitle(), companyFromDB)) {
            logger.error("coupon title already exists");
            throw new CouponSystemException("Coupon title already exists");
        }

        coupon.setCompany(companyFromDB);

        couponRepository.save(coupon);
        logger.info("coupon successfully added: " + coupon);
        return coupon;
    }

    @Override
    public void updateCoupon(UUID token, Coupon coupon) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!couponRepository.existsById(coupon.getCouponId())) {
            throw new CouponSystemException("Coupon does not exist");
        }

        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException("Company does not exist");
        }

        Coupon couponToUpdate = couponRepository.getOneCoupon(coupon.getCouponId());

        if (couponToUpdate.getCompany().getCompanyId() != companyId) {
            throw new CouponSystemException("Coupon does not belong to this company");
        }

        if (!couponToUpdate.getTitle().equals(coupon.getTitle())) {
            if (couponRepository.existsByTitleAndCompany(coupon.getTitle(), couponToUpdate.getCompany())) {
                throw new CouponSystemException("Coupon title already exists");
            }
        }

        couponToUpdate.setCategory(coupon.getCategory());
        couponToUpdate.setTitle(coupon.getTitle());
        couponToUpdate.setDescription(coupon.getDescription());
        couponToUpdate.setStartDate(coupon.getStartDate());
        couponToUpdate.setEndDate(coupon.getEndDate());
        couponToUpdate.setAmount(coupon.getAmount());
        couponToUpdate.setPrice(coupon.getPrice());
        couponToUpdate.setImage(coupon.getImage());

        couponRepository.save(couponToUpdate);
        logger.info("coupon successfully updated: " + couponToUpdate);
    }

    @Override
    public void deleteCoupon(UUID token, Long couponId) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException("Coupon does not exist");
        }

        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException("Company does not exist");
        }

        Coupon couponToDelete = couponRepository.getOneCoupon(couponId);

        if (couponToDelete.getCompany().getCompanyId() != companyId) {
            throw new CouponSystemException("Coupon does not belong to this company");
        }

        couponRepository.deleteCouponPurchases(couponId);
        logger.info("coupon purchases deleted successfully");
        couponRepository.deleteById(couponId);
        logger.info("coupon successfully deleted");
    }

    @Override
    public List<Coupon> getCompanyCoupons(UUID token) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException("Company does not exist");
        }

        Company company = companyRepository.getOneCompany(companyId);
        List<Coupon> coupons = company.getCoupons();
        logger.info("Company coupons acquired");
        return coupons;
    }

    @Override
    public List<Coupon> getCompanyCouponsByCategory(UUID token, Category category) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException("Company does not exist");
        }

        Company company = companyRepository.getOneCompany(companyId);

        List<Coupon> coupons = couponRepository.getCouponsByCategory(company, category);
        logger.info("Company coupons by category acquired: " + category);
        return coupons;

    }

    @Override
    public List<Coupon> getCompanyCouponsByPrice(UUID token, Double price) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException("Company does not exist");
        }

        Company company = companyRepository.getOneCompany(companyId);

        List<Coupon> coupons = couponRepository.getCouponsByPrice(company, price);
        logger.info("Company coupons by max price acquired: " + price);
        return coupons;
    }

    @Override
    public Company getCompanyDetails(UUID token) throws CouponSystemException {
        Long companyId = tokenManager.validateToken(token, ClientType.COMPANY);
        logger.info("token validated");
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException("Company does not exist");
        }

        Company company = companyRepository.getOneCompany(companyId);
        logger.info("Company details acquired");
        return company;
    }
}
