package com.example.Cinemazing_Deals.controllers;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import com.example.Cinemazing_Deals.security.LoginManager;
import com.example.Cinemazing_Deals.security.LoginRequest;
import com.example.Cinemazing_Deals.security.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginManager loginManager;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public LoginResponse login (@RequestBody LoginRequest loginRequest) throws CouponSystemException {
        return loginManager.manageLogin(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getClientType());
    }
}
