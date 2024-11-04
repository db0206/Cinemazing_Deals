package com.example.Cinemazing_Deals.repositories;

import com.example.Cinemazing_Deals.model.Category;
import com.example.Cinemazing_Deals.model.Company;
import com.example.Cinemazing_Deals.model.Coupon;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
    boolean existsByTitleAndCompany(String title, Company company);

    @Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
    Coupon getOneCoupon(Long couponId);

    @Query("SELECT c FROM Coupon c WHERE c.company = :company AND c.category = :category")
    List<Coupon> getCouponsByCategory(Company company, Category category);

    @Query("SELECT c FROM Coupon c WHERE c.company = :company AND c.price <= :price")
    List<Coupon> getCouponsByPrice(Company company, Double price);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customers_coupons (customer_customer_id, coupons_coupon_id) VALUES (:customerId, :couponId)", nativeQuery = true)
    void purchaseCoupon(@Param("customerId") Long customerId, @Param("couponId") Long couponId);

    @Query(value = "SELECT COUNT(*) > 0 FROM customers_coupons WHERE customer_customer_id = :customerId AND coupons_coupon_id = :couponId", nativeQuery = true)
    int alreadyPurchased(@Param("customerId") Long customerId, @Param("couponId") Long couponId);

    @Query(value = "SELECT * FROM coupons inner JOIN customers_coupons ON customers_coupons.coupons_coupon_id = coupons.coupon_id WHERE customer_customer_id = :customerId", nativeQuery = true)
    List<Coupon> getCustomerCoupons(@Param("customerId") Long customerId);

    @Query(value = "SELECT * FROM coupons inner JOIN customers_coupons ON coupons.coupon_id = customers_coupons.coupons_coupon_id WHERE customer_customer_id = ? AND category = ?", nativeQuery = true)
    List<Coupon> getCustomerCouponsByCategory(Long customerId, String category);

    @Query(value = "SELECT * FROM coupons inner JOIN customers_coupons ON customers_coupons.coupons_coupon_id = coupons.coupon_id WHERE customer_customer_id = :customerId AND price <= :price", nativeQuery = true)
    List<Coupon> getCustomerCouponsByPrice(@Param("customerId") Long customerId, @Param("price") Double price);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE end_date < CURRENT_DATE", nativeQuery = true)
    void deleteExpiredCoupons();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE coupons_coupon_id IN (SELECT coupon_id FROM coupons WHERE end_date < CURRENT_DATE) ", nativeQuery = true)
    void deleteExpiredPurchasedCoupons();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE coupons_coupon_id IN (SELECT coupon_id FROM coupons WHERE company_id = :companyId)", nativeQuery = true)
    void deletePurchasedCoupons(@Param("companyId") Long companyId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE customer_customer_id = :customerId", nativeQuery = true)
    void deletePurchasedCouponsByCustomerId(@Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE coupons_coupon_id = :couponId", nativeQuery = true)
    void deleteCouponPurchases(@Param("couponId") Long couponId);
}
