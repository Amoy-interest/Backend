package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.RegisterDTO;
import com.example.amoy_interest.dto.UserCheckDTO;
import com.example.amoy_interest.dto.UserInfoDTO;
import com.example.amoy_interest.entity.UserAuth;
import io.swagger.models.auth.In;

public interface UserService {
    UserAuth findUserAuthById(Integer id);
    UserAuth findUserAuthByUsername(String username);
    UserInfoDTO register(RegisterDTO registerDTO);
    boolean follow(Integer user_id,Integer follow_id);
    boolean ban(UserCheckDTO userCheckDTO);
    boolean unban(Integer user_id);
    boolean forbid(UserCheckDTO userCheckDTO);
    boolean permit(Integer user_id);
}
