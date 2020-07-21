package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.RegisterDTO;
import com.example.amoy_interest.dto.UserCheckDTO;
import com.example.amoy_interest.dto.UserInfoDTO;
import com.example.amoy_interest.dto.UserReportDTO;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.entity.UserFollow;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserAuth findUserAuthById(Integer id);
    UserAuth findUserAuthByUsername(String username);
    UserInfoDTO register(RegisterDTO registerDTO);
    boolean follow(Integer user_id,Integer follow_id);
    boolean ban(UserCheckDTO userCheckDTO);
    boolean unban(Integer user_id);
    boolean forbid(UserCheckDTO userCheckDTO);
    boolean permit(Integer user_id);
    List<UserReportDTO> getReportedUsers();
    User findUserById(Integer user_id);
    //测试
    Page<UserFollow> getAllUserFollow(Integer pageNum, Integer pageSize);
}
