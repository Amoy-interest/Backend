package com.example.amoy_interest.constant;

/**
 * 常量
 * @author dolyw.com
 * @date 2018/9/3 16:03
 */
public class Constant {

    private Constant() {}

    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-username:
     */
    public static final String USERNAME = "username";
    /**
     * JWT-user_id;
     */
    public static final String USER_ID = "user_id";
    /**
     * JWT-currentTimeMillis:
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

    public static final String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMSIsImN1cnJlbnRUaW1lTWlsbGlzIjoiMTU5ODQwODAyMTU0OSIsImV4cCI6MTkxMzc2ODAyMSwidXNlcm5hbWUiOiLpsoHov4UifQ.FSxvme-or5PLR23LYNfgcD4k6P7p_uqVbYegdJVA3HE";
}
