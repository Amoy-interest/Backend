package com.example.amoy_interest.utils;

import com.example.amoy_interest.constant.Constant;
import com.example.amoy_interest.dao.UserAuthDao;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.exception.CustomException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserUtil {

    @Autowired
    private UserAuthDao userAuthDao;
    /**
     * 获取当前登录用户
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public UserAuth getUser() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得username
        String username = JwtUtil.getClaim(token, Constant.USERNAME);
        // 用户是否存在
        UserAuth userAuth = userAuthDao.findUserByUsername(username);
        if (userAuth == null) {
            throw new CustomException("该用户名不存在(The username does not exist.)");
        }
        return userAuth;
    }

    /**
     * 获取当前登录用户Id
     *
     * @param
     * @return Integer
     * @author Mok
     */
    public Integer getUserId() {
        //不去数据库拿，减少访问数据库次数
        if(SecurityUtils.getSubject().getPrincipal() == null) {
            return null;
        }
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得username
        return Integer.parseInt(JwtUtil.getClaim(token, Constant.USER_ID));
    }

    /**
     * 获取当前登录用户Token
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */

    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户的用户名
     *
     * @param
     * @return
     * @author mok
     * @date 2020-7-28
     */
    public String getUsername() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得username
        return JwtUtil.getClaim(token, Constant.USERNAME);
    }
}
