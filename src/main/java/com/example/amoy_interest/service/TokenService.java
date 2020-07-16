package com.example.amoy_interest.service;

import com.example.amoy_interest.entity.UserAuth;

public interface TokenService {
    String getToken(UserAuth userAuth);
}
