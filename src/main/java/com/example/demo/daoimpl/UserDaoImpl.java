package com.example.demo.daoimpl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserCount;
import com.example.demo.entity.UserFollow;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserCountRepository;
import com.example.demo.repository.UserFollowRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCountRepository userCountRepository;
    @Autowired
    private UserFollowRepository userFollowRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserById(Integer user_id) {
        return userRepository.findUserById(user_id);
    }

    @Override
    public UserInfoDTO register(RegisterDTO registerDTO) {
//        User
        User user = new User(registerDTO.getUsername(),registerDTO.getPassword(),0,0);
        user = userRepository.saveAndFlush(user);
        Integer user_id = user.getUser_id();
        UserInfo userInfo = new UserInfo(user_id,registerDTO.getEmail(),registerDTO.getSex(),
            registerDTO.getAddress(),100,"这个人很懒，什么都没留下",null
        );
        userInfoRepository.save(userInfo);
        UserCount userCount = new UserCount(user_id,0,0,0);
        userCountRepository.save(userCount);
        return new UserInfoDTO(user,userInfo);
    }
}
