package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.service.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TopicService topicService;

    private ObjectMapper om = new ObjectMapper();
    @Before
    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(context).build();}

    @AfterEach
    void tearDown() {

    }

}
