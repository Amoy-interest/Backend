package com.example.amoy_interest.msgutils;


public class MsgUtil {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int LOGIN_USER_ERROR = -100;
    public static final int NOT_LOGGED_IN_ERROR = -101;
    public static final int USER_BAN = -102;
    public static final int USER_NOT_EXIST = -103;
    public static final int USER_BAN_SUCCESS = 1;
    public static final int IS_ADMIN = -104;


    public static final String SUCCESS_MSG = "成功！";
    public static final String LOGIN_SUCCESS_MSG = "登录成功！";
    public static final String LOGOUT_SUCCESS_MSG = "登出成功！";
    public static final String LOGOUT_ERR_MSG = "登出异常！";
    public static final String REGISTER_SUCCESS_MSG = "注册成功！";
    public static final String SIGNIN_ERR_MSG = "注册异常！";
    public static final String ERROR_MSG = "错误！";
    public static final String LOGIN_USER_ERROR_MSG = "用户名或密码错误，请重新输入！";
    public static final String NOT_LOGGED_IN_ERROR_MSG = "登录失效，请重新登录！";
    public static final String USER_BAN_MSG = "你已经被禁用，请与管理员联系解除! ";
    public static final String USER_NOT_EXIST_MSG = "该用户不存在";
    public static final String IS_ADMIN_MSG = "该用户号为管理员,禁用失败";
    public static final String IS_ADMIN2_MSG = "该用户号为管理员,解禁失败";
    public static final String USER_EXIST_MSG = "该用户名已被使用";

    public static final String ADD_BLOG_SUCCESS_MSG = "添加博文成功";
    public static final String PUT_BLOG_SUCCESS_MSG = "更新博文成功";
    public static final String DELETE_BLOG_SUCCESS_MSG = "删除博文成功";
    public static final String GET_BLOG_SUCCESS_MSG = "获取博文成功";

    public static final String VOTE_SUCCESS_MSG = "点赞成功";
    public static final String COMMENT_SUCCESS_MSG = "评论成功";
    public static final String FORWARD_SUCCESS_MSG = "转发成功";

    public static final String SEARCH_SUCCESS_MSG = "搜索成功";

//    public static Msg makeMsg(MsgCode code, JSONObject data){
//        return new Msg(code, data);
//    }

//    public static Msg makeMsg(MsgCode code, String msg, JSONObject data){
//        return new Msg(code, msg, data);
//    }

    public static Msg makeMsg(MsgCode code){
        return new Msg(code);
    }

    public static Msg makeMsg(MsgCode code, String msg){
        return new Msg(code, msg);
    }

//    public static Msg makeMsg(int status, String msg, JSONObject data){
//        return new Msg(status, msg, data);
//    }

    public static Msg makeMsg(int status, String msg){
        return new Msg(status, msg);
    }
}
