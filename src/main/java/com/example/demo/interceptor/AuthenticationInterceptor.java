package com.example.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.annotation.PassToken;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Date;

import static com.example.demo.constant.SecurityConstants.SECRET;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                DecodedJWT jwt = null;
                try {
                    // 使用了HMAC256加密算法。
                    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                            .withIssuer("auth0")
                            .build(); //Reusable verifier instance
                    jwt = verifier.verify(token);
                } catch (JWTVerificationException exception){
                    //Invalid signature/claims
                    throw new RuntimeException("Token失效，请重新登陆");
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException("401");
                }
//                Date expiration_time;
//                try {
//                    expiration_time = jwt.getExpiresAt();
//                } catch (JWTDecodeException j) {
////                    throw new RuntimeException("401");
//                    throw new RuntimeException("已过期");
//                }
//
//                if(expiration_time.before(new Date(System.currentTimeMillis()))) {
//                    throw new RuntimeException("token已过期，请重新登录");
//                }
                // 获取 token 中的 user id

//                String userId;
//                try {
//                    userId = JWT.decode(token).getAudience().get(0);
//                } catch (JWTDecodeException j) {
//                    throw new RuntimeException("401");
//                }
////                System.out.println(userId);
//                User user = userService.findUserById(Integer.parseInt(userId));
////                System.out.println(user);
//                if (user == null) {
//                    throw new RuntimeException("用户不存在，请重新登录");
//                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
