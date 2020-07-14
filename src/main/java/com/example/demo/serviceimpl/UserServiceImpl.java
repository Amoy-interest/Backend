package com.example.demo.serviceimpl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.Blog;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public UserInfoDTO register(RegisterDTO registerDTO) {
        return userDao.register(registerDTO);
    }

    @Override
    public UserInfoDTO getUserInfoById(Integer id) {
        Blog blog = new Blog();
        blog.getUser().getUsername();
        return null;
    }
}
