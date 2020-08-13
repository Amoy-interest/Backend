package com.example.amoy_interest.utils;

/**
 * @Author: Mok
 * @Date: 2020/7/29 15:37
 */
public class RedisKeyUtils {
    //保存用户点赞博文数据的key
    public static final String MAP_KEY_USER_VOTE_BLOG = "MAP_USER_VOTE_BLOG";
    //保存博文被点赞数量的key
    public static final String MAP_KEY_BLOG_VOTE_COUNT = "MAP_BLOG_VOTE_COUNT";

    /**
     * 拼接被点赞的博文id和点赞的人的id作为key。格式 222222::333333
     * @param blog_id
     * @param user_id
     * @return
     */
    public static String getVoteKey(Integer blog_id,Integer user_id){
        StringBuilder builder = new StringBuilder();
        builder.append(blog_id);
        builder.append("::");
        builder.append(user_id);
        return builder.toString();
    }
}
