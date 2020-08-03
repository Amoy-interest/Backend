package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.BlogAddDTO;
import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.UserDTO;
import com.example.amoy_interest.dto.UserInfoDTO;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.utils.CommonPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: Mok
 * @Date: 2020/7/30 9:30
 */
@RequestMapping("/search")
@Api(tags = "搜索模块")
@RestController
public class SearchController {
    @Autowired
    private UserService userService;

    @RequiresAuthentication
    @ApiOperation(value = "预搜索")
    @RequestMapping(value = "/pre", method = RequestMethod.GET)
    public Msg<CommonPage<UserDTO>> PreSearch(@RequestParam(required = true)
                                              @NotNull(message = "关键词不能为空")
                                              @NotEmpty(message = "关键词不能为空字符串")
                                              @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                              @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SEARCH_SUCCESS_MSG, CommonPage.restPage(userService.searchUsersPage(keyword, pageNum, pageSize)));
    }
}
