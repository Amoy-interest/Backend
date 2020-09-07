package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.daoimpl.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.*;
import com.example.amoy_interest.utils.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
public class SensitivewordServiceTest {
    @InjectMocks
    private SensitiveWordServiceImpl sensitiveWordService;

    @Mock
    private SensitiveWordDaoImpl sensitiveWordDao;

    @Test
    public void testGetAllSensWords() {
        when(sensitiveWordDao.findAllSensitiveWords()).thenReturn(null);
        sensitiveWordService.getAllSensitiveWords();
    }

    @Test
    public void testAddSensWord() {
        SensitiveWord sensitiveWord = new SensitiveWord("测试");
        sensitiveWordService.addSensitiveWord(sensitiveWord);
    }

    @Test
    public void testUpdateSensWord() {
        sensitiveWordService.updateSensitiveWord("测试1", "测试2");
    }

    @Test
    public void testDeleteByKeyword() {
        sensitiveWordService.deleteByKeyword("测试1");
    }

    @Test
    public void testFindSensWordByKw() {
        when(sensitiveWordDao.findSensitiveWordByKeyword(any())).thenReturn(null);
        sensitiveWordService.findSensitiveWordByKeyword("测试");
    }

    @Test
    public void testFindSensWordPageByKw() {
        when(sensitiveWordDao.findPageByKeyword(any(), any())).thenReturn(null);
        sensitiveWordService.findSensitiveWordsPageByKeyword("测试", 0, 5);
    }

    @Test
    public void testGetSensWordPage() {
        when(sensitiveWordDao.findPage(any())).thenReturn(null);
        sensitiveWordService.getSensitiveWordsPage(0, 5);
    }

}
