package com.example.demo.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.UserAuth;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.constant.SecurityConstants.EXPIRATION_TIME;
import static com.example.demo.constant.SecurityConstants.SECRET;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(UserAuth userAuth) {
        if(userAuth == null) {
            throw new RuntimeException("user is null");
        }
        Integer user_id = userAuth.getUser_id();
        Integer user_type = userAuth.getUser_type();
        if(user_id == null || user_type == null) {
            throw new RuntimeException("user_id or user_type is null");
        }
        String token="";
        token= JWT.create()
                .withIssuer("auth0")
                .withClaim("user_id", userAuth.getUser_id())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("user_type", userAuth.getUser_type())
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

}
