package com.example.Cinemazing_Deals.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UUID token;
    private String email;
    private String name;
    private ClientType clientType;
    private LocalDateTime expirationTime;
}
