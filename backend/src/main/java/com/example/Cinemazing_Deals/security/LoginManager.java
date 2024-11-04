package com.example.Cinemazing_Deals.security;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.services.AdminService;
import com.example.Cinemazing_Deals.services.CompanyService;
import com.example.Cinemazing_Deals.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginManager {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CustomerService customerService;


    public LoginResponse manageLogin (String email, String password, ClientType clientType) throws CouponSystemException {

        LoginResponse loginResponse = null;

        switch (clientType){
            case ADMIN:
                loginResponse = adminService.login(email, password);
                break;
            case COMPANY:
                loginResponse = companyService.login(email, password);
                break;
            case CUSTOMER:
                loginResponse = customerService.login(email, password);
                break;
            default:
                throw new CouponSystemException("wrong client type");
        }

        return loginResponse;

    }
}
