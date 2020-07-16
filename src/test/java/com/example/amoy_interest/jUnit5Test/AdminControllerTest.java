package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private UserService userService;
    @MockBean
    private BlogService blogService;
    @MockBean
    private TopicService topicService;

    private ObjectMapper om = new ObjectMapper();
    @Before
    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(context).build();}

    @AfterEach
    void tearDown() {

    }

    @Test
    public void testBan() {

    }
    @Test
    public void testUnban() {

    }
    @Test
    public void testForbid() {

    }
    @Test
    public void testPermit() {

    }
}
