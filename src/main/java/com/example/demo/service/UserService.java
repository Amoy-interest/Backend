package com.example.demo.service;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;

public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    User register(RegisterDTO registerDTO);
}
