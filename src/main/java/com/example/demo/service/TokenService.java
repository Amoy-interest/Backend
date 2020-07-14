package com.example.demo.service;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;

public interface TokenService {
    String getToken(User user);
}
