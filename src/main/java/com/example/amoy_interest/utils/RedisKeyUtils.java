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
    //保存用户关注数量的key
    public static final String MAP_KEY_USER_FOLLOW_COUNT = "MAP_USER_FOLLOW_COUNT";
    //保存用户粉丝数量的key
    public static final String MAP_KEY_USER_FAN_COUNT = "MAP_USER_FAN_COUNT";
    //保存用户博文数量的key
    public static final String MAP_KEY_USER_BLOG_COUNT = "MAP_USER_BLOG_COUNT";
    //保存博文评论数的key
    public static final String MAP_KEY_BLOG_COMMENT_COUNT = "MAP_BLOG_COMMENT_COUNT";
    //保存博文举报数的key
    public static final String MAP_KEY_BLOG_REPORT_COUNT = "MAP_BLOG_REPORT_COUNT";
    //保存博文被举报内容的key
    public static final String MAP_KEY_BLOG_REPORT = "MAP_BLOG_REPORT";
    //保存用户被举报数的key
    public static final String MAP_KEY_USER_REPORT_COUNT = "MAP_USER_REPORT_COUNT";
    //保存用户被举报内容的key
    public static final String MAP_KEY_USER_REPORT = "MAP_USER_REPORT";
    //保存博文被转发数量的key
    public static final String MAP_KEY_BLOG_FORWARD_COUNT = "MAP_BLOG_FORWARD_COUNT";

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

    public static String getBlogReportKey(Integer blog_id,Integer user_id) {
        StringBuilder builder = new StringBuilder();
        builder.append(blog_id);
        builder.append("::");
        builder.append(user_id);
        return builder.toString();
    }

    public static String getUserReportKey(Integer user_id,Integer reporter_id) {
        StringBuilder builder = new StringBuilder();
        builder.append(user_id);
        builder.append("::");
        builder.append(reporter_id);
        return builder.toString();
    }


}
