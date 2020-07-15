package com.example.demo.jUnit5Test;

import com.example.demo.dao.UserCountDao;
import com.example.demo.dao.UserAuthDao;
import com.example.demo.dao.UserFollowDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserAuth;
import com.example.demo.entity.UserCount;
import com.example.demo.entity.UserFollow;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
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
        UserAuth insert_userAuth = new UserAuth("admin","123456",0,0);
        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0);
        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
        UserCount userCount = new UserCount(100,0,0,0);
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
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
}
