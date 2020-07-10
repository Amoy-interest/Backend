package com.example.demo.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.User;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(Integer.toString(user.getUser_id())) //用username和用user_id是否有区别？
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
