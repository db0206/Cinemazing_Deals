package com.example.Cinemazing_Deals.job;

import com.example.Cinemazing_Deals.repositories.CouponRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CouponExpirationJob {

    @Autowired
    private CouponRepository couponRepository;

    private static final Logger logger = LogManager.getLogger(CouponExpirationJob.class);

    @Scheduled(fixedRate = 1000*60*60*24, initialDelay = 1000*20)
    public void removeExpiredCoupons() {
        couponRepository.deleteExpiredPurchasedCoupons();
        couponRepository.deleteExpiredCoupons();
        logger.info("Expired Coupons Deleted");
    }
}
