package com.example.Cinemazing_Deals.security;

import com.example.Cinemazing_Deals.exceptions.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenManager {

    @Autowired
    private Map<UUID, TokenInformation> tokens;

    public UUID addToken(TokenInformation tokenInformation) throws CouponSystemException {
        if (tokenInformation == null) {
            throw new CouponSystemException("Token information is bad");
        }

        removeToken(tokenInformation);

        UUID tokenToAdd = UUID.randomUUID();

        while (tokens.containsKey(tokenToAdd)) {
            tokenToAdd = UUID.randomUUID();
        }

        tokens.put(tokenToAdd, tokenInformation);

        return tokenToAdd;
    }

    private void removeToken(TokenInformation tokenInformation) {
        tokens.entrySet().removeIf((token) -> token.getValue().getId() == tokenInformation.getId());
    }

    public Long validateToken(UUID token, ClientType clientType) throws CouponSystemException {
        if (!tokens.containsKey(token)) {
            throw new CouponSystemException("token not valid");
        }

        if (tokens.get(token).getClientType() != clientType) {
            throw new CouponSystemException("unauthorized action");
        }

        tokens.get(token).setExpirationTime(LocalDateTime.now().plusHours(24));

        return tokens.get(token).getId();
    }

    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 1)
    private void deleteExpiredToken() {
        tokens.entrySet().removeIf((token) -> token.getValue().getExpirationTime().isBefore(LocalDateTime.now()));
    }
}
