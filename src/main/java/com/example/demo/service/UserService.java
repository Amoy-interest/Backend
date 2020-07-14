package com.example.demo.service;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;

public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    UserInfoDTO register(RegisterDTO registerDTO);
}
