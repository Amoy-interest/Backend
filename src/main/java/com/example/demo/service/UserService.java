package com.example.demo.service;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserAuth;

public interface UserService {
    UserAuth findUserAuthById(Integer id);
    UserAuth findUserAuthByUsername(String username);
    UserInfoDTO register(RegisterDTO registerDTO);
    void follow(Integer user_id,Integer follow_id);
}
