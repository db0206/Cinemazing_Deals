package com.example.Cinemazing_Deals.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInformation {
    private Long id;
    private LocalDateTime expirationTime;
    private ClientType clientType;

}
