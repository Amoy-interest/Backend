package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.RegisterDTO;
import com.example.amoy_interest.dto.UserCheckDTO;
import com.example.amoy_interest.dto.UserInfoDTO;
import com.example.amoy_interest.dto.UserReportDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.amoy_interest.constant.SecurityConstants.EXPIRATION_TIME;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserCountDao userCountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserFollowDao userFollowDao;
    @Autowired
    private UserBanDao userBanDao;
    @Override
    public UserAuth findUserAuthById(Integer id) {
        return userAuthDao.findUserById(id);
    }

    @Override
    public UserAuth findUserAuthByUsername(String username) {
        return userAuthDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserInfoDTO register(RegisterDTO registerDTO) {
        UserAuth userAuth = new UserAuth(registerDTO.getUsername(),registerDTO.getPassword(),0,0,0);
        userAuth = userAuthDao.insert(userAuth);
        Integer user_id = userAuth.getUser_id();
        User user = new User(user_id,registerDTO.getNickname(),registerDTO.getEmail(),registerDTO.getSex(),
                registerDTO.getAddress(),100,"这个人很懒，什么都没留下",null
        );
        user.setUserAuth(userAuth);
        userDao.insert(user);
        UserCount userCount = new UserCount(user_id,0,0,0);
        userCountDao.insert(userCount);
        return new UserInfoDTO(user);
    }

    @Override
    public boolean follow(Integer user_id, Integer follow_id) {
        UserFollow userFollow = new UserFollow(user_id,follow_id);
        userFollowDao.insert(userFollow);
        return true;
    }

    @Override
    public boolean ban(UserCheckDTO userCheckDTO) {
        Date endTime = new Date(System.currentTimeMillis() + userCheckDTO.getTime()*1000);
        Integer user_id = userCheckDTO.getUser_id();
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_ban(1);
        userAuthDao.update(userAuth);
        UserBan userBan = userBanDao.findUserBanById(user_id);
        if(userBan == null) {
            userBan = new UserBan(user_id,endTime,null);
        }else {
            userBan.setBan_time(endTime);
        }
        userBanDao.insert(userBan);
        return true;
    }

    @Override
    public boolean unban(Integer user_id) {
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_ban(0);
        userAuthDao.update(userAuth);
        return true;
    }

    @Override
    public boolean forbid(UserCheckDTO userCheckDTO) {
        Date endTime = new Date(System.currentTimeMillis() + userCheckDTO.getTime()*1000);
        Integer user_id = userCheckDTO.getUser_id();
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_forbidden(1);
        userAuthDao.update(userAuth);
        UserBan userBan = userBanDao.findUserBanById(user_id);
        if(userBan == null) {
            userBan = new UserBan(user_id,null,endTime);
        }else {
            userBan.setForbidden_time(endTime);
        }
        userBanDao.insert(userBan);
        return true;
    }

    @Override
    public boolean permit(Integer user_id) {
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_forbidden(0);
        userAuthDao.update(userAuth);
        return true;
    }

    @Override
    public List<UserReportDTO> getReportedUsers() {
        List<User> userList = userDao.getReportedUsers();
        List<UserReportDTO> userReportDTOList = new ArrayList<>();
        for(User user:userList) {
            UserReportDTO userReportDTO = new UserReportDTO(user);
            userReportDTOList.add(userReportDTO);
        }
        return userReportDTOList;
    }
}