package com.example.demo.serviceimpl;

import com.example.demo.dao.UserCountDao;
import com.example.demo.dao.UserAuthDao;
import com.example.demo.dao.UserFollowDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserInfoDTO register(RegisterDTO registerDTO) {
        UserAuth userAuth = new UserAuth(registerDTO.getUsername(),registerDTO.getPassword(),0,0);
        userAuth = userAuthDao.insert(userAuth);
        Integer user_id = userAuth.getUser_id();
        User user = new User(user_id,registerDTO.getNickname(),registerDTO.getEmail(),registerDTO.getSex(),
                registerDTO.getAddress(),100,"这个人很懒，什么都没留下",null
        );
        userDao.insert(user);
        UserCount userCount = new UserCount(user_id,0,0,0);
        userCountDao.insert(userCount);
        return new UserInfoDTO(user);
    }

    @Override
    public void follow(Integer user_id, Integer follow_id) {
        UserFollow userFollow = new UserFollow(user_id,follow_id);
        userFollowDao.insert(userFollow);
    }
}
