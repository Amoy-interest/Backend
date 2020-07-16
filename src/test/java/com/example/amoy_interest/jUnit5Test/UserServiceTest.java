package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.dao.UserCountDao;
import com.example.amoy_interest.dao.UserAuthDao;
import com.example.amoy_interest.dao.UserFollowDao;
import com.example.amoy_interest.dao.UserDao;
import com.example.amoy_interest.dto.RegisterDTO;
import com.example.amoy_interest.dto.UserInfoDTO;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.entity.UserCount;
import com.example.amoy_interest.entity.UserFollow;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static com.example.amoy_interest.constant.SecurityConstants.EXPIRATION_TIME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserAuthDao userAuthDao;
    @MockBean
    private UserCountDao userCountDao;
    @MockBean
    private UserDao userDao;
    @MockBean
    private UserFollowDao userFollowDao;
    @Test
    public void TestRegister() {
        RegisterDTO registerDTO = new RegisterDTO("admin","mok","123456",0,"上海市闵行区","mokkkkk@sjtu.edu.cn");
        UserAuth insert_userAuth = new UserAuth("admin","123456",0,0,0);
        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0);
        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
        user.setUserAuth(userAuth);
        UserCount userCount = new UserCount(100,0,0,0);
        UserInfoDTO userInfoDTO = new UserInfoDTO("mok",0,"上海市闵行区","这个人很懒，什么都没留下",null,0);
        when(userAuthDao.insert(insert_userAuth)).thenReturn(userAuth);

        assertEquals(userInfoDTO,userService.register(registerDTO));
        verify(userAuthDao,times(1)).insert(insert_userAuth);
        verify(userCountDao,times(1)).insert(userCount);
        verify(userDao,times(1)).insert(user);
    }
    @Test
    public void TestFollow() {
        UserFollow userFollow = new UserFollow(1,4);
        userService.follow(1,4);
        verify(userFollowDao,times(1)).insert(userFollow);
    }
    @Test
    public void testfindUserAuthById() {
        Integer id = 100;
        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0);
        when(userAuthDao.findUserById(id)).thenReturn(userAuth);
        assertEquals(userAuth,userService.findUserAuthById(id));
        verify(userAuthDao,times(1)).findUserById(id);
    }

    @Test
    public void testfindUserAuthByUsername() {
        String username = "admin";
        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0);
        when(userAuthDao.findUserByUsername(username)).thenReturn(userAuth);
        assertEquals(userAuth,userService.findUserAuthByUsername(username));
        verify(userAuthDao,times(1)).findUserByUsername(username);
    }

}
