package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.UserCountDao;
import com.example.amoy_interest.dao.UserAuthDao;
import com.example.amoy_interest.dao.UserFollowDao;
import com.example.amoy_interest.dao.UserDao;
import com.example.amoy_interest.dto.RegisterDTO;
import com.example.amoy_interest.dto.UserCheckDTO;
import com.example.amoy_interest.dto.UserInfoDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

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
        UserAuth userAuth = new UserAuth(registerDTO.getUsername(),registerDTO.getPassword(),0,0);
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
        //时间怎么设置？在哪存？
        UserAuth userAuth = userAuthDao.findUserById(userCheckDTO.getUser_id());
//        userAuth.s
        return false;
    }

    @Override
    public boolean unban(Integer user_id) {
        return false;
    }

    @Override
    public boolean forbid(UserCheckDTO userCheckDTO) {
        return false;
    }

    @Override
    public boolean permit(Integer user_id) {
        return false;
    }

}
