package com.example.demo.service;

import com.example.demo.entity.UserAuth;

public interface TokenService {
    String getToken(UserAuth userAuth);
}
