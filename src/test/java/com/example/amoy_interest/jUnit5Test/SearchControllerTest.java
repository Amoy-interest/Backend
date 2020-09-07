package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.amoy_interest.config.shiro.jwt.JwtToken;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.RecommendService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.utils.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static com.example.amoy_interest.constant.Constant.TEST_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: Mok
 * @Date: 2020/9/7 21:00
 */
@SpringBootTest
public class SearchControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private SecurityManager securityManager;
    private final static String token1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMSIsImN1cnJlbnRUaW1lTWlsbGlzIjoiMTU5ODQwODAyMTU0OSIsImV4cCI6MTkxMzc2ODAyMSwidXNlcm5hbWUiOiLpsoHov4UifQ.FSxvme-or5PLR23LYNfgcD4k6P7p_uqVbYegdJVA3HE";

    @MockBean
    private UserService userService;
    @MockBean
    private TopicService topicService;
    private ObjectMapper om = new ObjectMapper();
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(context.getServletContext());
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(context.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Subject subject = new WebSubject
                .Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        JwtToken token = new JwtToken(token1);
        subject.login(token);
        ThreadContext.bind(subject);
    }

    @Test
    public void testPreSearch() throws Exception {
        List<UserDTO> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserDTO> userDTOPage = new PageImpl<>(list, pageable, 0);
        when(userService.searchUsersPage(any(),any(),any())).thenReturn(userDTOPage);
        mockMvc.perform(get("/search/pre?keyword=你好")).andExpect(status().isOk());
    }

    @Test
    public void testTopicPreSearch() throws Exception {
        List<String> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        Page<String> page = new PageImpl<>(list, pageable, 0);
        when(topicService.searchTopicsPage(any(),any(),any())).thenReturn(page);
        mockMvc.perform(get("/search/pre/topic?keyword=你好")).andExpect(status().isOk());
    }

}
