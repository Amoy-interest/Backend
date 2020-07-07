package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/user")
@Api(tags="用户模块")
@RestController
public class UserController {
    @ApiOperation(value="login", notes="登录")
    @ResponseBody
    @RequestMapping(value={"/login"}, method= RequestMethod.POST)
    public String Login() {
        return "success";
    }
}
