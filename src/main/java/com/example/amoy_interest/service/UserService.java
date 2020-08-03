package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.entity.UserFollow;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User findUserById(Integer user_id);

    boolean modifyUser(UserModifyParam userModifyParam);

    UserAuth findUserAuthById(Integer id);

    UserAuth findUserAuthByUsername(String username);

    UserInfoDTO register(RegisterDTO registerDTO);

    UserInfoDTO getUserInfo(Integer user_id1, Integer user_id2);

    UserDTO getUserDTO(Integer user_id1, Integer user_id2);

    boolean follow(Integer user_id, Integer follow_id);

    boolean unfollow(Integer user_id, Integer unfollow_id);

    boolean ban(UserCheckDTO userCheckDTO);

    boolean unban(Integer user_id);

    boolean forbid(UserCheckDTO userCheckDTO);

    boolean permit(Integer user_id);

    List<UserReportDTO> getReportedUsers();

    Page<UserReportDTO> getReportedUsersPage(Integer pageNum, Integer pageSize, Integer orderType);

    Page<UserReportDTO> searchReportedUsersPage(String keyword, Integer pageNum, Integer pageSize, Integer orderType);

    Page<UserDTO> searchUsersPage(String keyword, Integer pageNum, Integer pageSize);

    Page<UserInfoDTO> getUserFollowPage(Integer my_user_id, Integer user_id, Integer pageNum, Integer pageSize);

    Page<UserInfoDTO> getUserFanPage(Integer my_user_id, Integer user_id, Integer pageNum, Integer pageSize);

    Page<UserBanResult> getUserBanPage(Integer pageNum, Integer pageSize);

    Page<UserForbiddenResult> getUserForbidPage(Integer pageNum, Integer pageSize);

    Page<UserBanResult> searchUserBanPage(String keyword, Integer pageNum, Integer pageSize);

    Page<UserForbiddenResult> searchUserForbidPage(String keyword, Integer pageNum, Integer pageSize);


}
