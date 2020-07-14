package com.example.demo.dao;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;

public interface UserDao {
    User findUserByUsername(String username);
    User findUserById(Integer user_id);
    UserInfoDTO register(RegisterDTO registerDTO);
}
