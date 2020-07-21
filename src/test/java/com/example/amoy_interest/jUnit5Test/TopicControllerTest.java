package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.UserDTO;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.TopicService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

    @Test
    public void testGetTopic() throws Exception{
        when(topicService.getTopicDTOByName("高考加油")).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/topics?topic_name=高考加油"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<TopicDTO> msg = om.readValue(resultContent,new TypeReference<Msg<TopicDTO>>() {});
        verify(topicService,times(1)).getTopicDTOByName("高考加油");
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }
    @Test
    public void testGetHotList() throws Exception{
        MvcResult result = mockMvc.perform(get("/topics/hotList"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<List<String>> msg = om.readValue(resultContent,new TypeReference<Msg<List<String>>>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }
}
