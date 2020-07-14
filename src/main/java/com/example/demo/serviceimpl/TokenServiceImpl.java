package com.example.demo.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.constant.SecurityConstants.EXPIRATION_TIME;
import static com.example.demo.constant.SecurityConstants.SECRET;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(User user) {
        String token="";
        token= JWT.create()
                .withIssuer("auth0")
                .withClaim("user_id",user.getUser_id())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("user_type",user.getUser_type())
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

}
