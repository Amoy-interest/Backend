package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dto.BlogSingleCountDTO;
import com.example.amoy_interest.dto.BlogVoteCountDTO;
import com.example.amoy_interest.dto.UserSingleCountDTO;
import com.example.amoy_interest.entity.BlogReport;
import com.example.amoy_interest.entity.BlogVote;
import com.example.amoy_interest.entity.UserReport;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.service.VoteService;
import com.example.amoy_interest.utils.JedisUtil;
import com.example.amoy_interest.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Mok
 * @Date: 2020/7/29 15:21
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveVote2Redis(Integer blog_id, Integer user_id) {
        String key = RedisKeyUtils.getVoteKey(blog_id, user_id);

        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG, key, 1);
    }

    @Override
    public void cancelVoteFromRedis(Integer blog_id, Integer user_id) {
        String key = RedisKeyUtils.getVoteKey(blog_id, user_id);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG, key, 0);
    }

    @Override
    public void deleteVoteFromRedis(Integer blog_id, Integer user_id) {
        String key = RedisKeyUtils.getVoteKey(blog_id, user_id);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG, key);
    }

    @Override
    public void incrementVoteCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, blog_id, 1);
    }

    @Override
    public void decrementVoteCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, blog_id, -1);
    }

    @Override
    public List<BlogVote> getVoteDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG, ScanOptions.NONE);
        List<BlogVote> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 blog_idH和user_id
            String[] split = key.split("::");
            Integer blog_id = Integer.parseInt(split[0]);
            Integer user_id = Integer.parseInt(split[1]);
            Integer value = (Integer) entry.getValue();

            //组装成 BlogVote 对象
            BlogVote blogVote = new BlogVote(blog_id, user_id, value);
            list.add(blogVote);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG, key);
        }
        return list;
    }

    @Override
    public List<BlogReport> getBlogReportDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_REPORT, ScanOptions.NONE);
        List<BlogReport> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 blog_idH和user_id
            String[] split = key.split("::");
            Integer blog_id = Integer.parseInt(split[0]);
            Integer user_id = Integer.parseInt(split[1]);
            String value = (String) entry.getValue();

            //组装成 BlogReport 对象
            BlogReport blogReport = new BlogReport(blog_id, user_id, value);
            list.add(blogReport);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_REPORT, key);
        }
        return list;
    }

    @Override
    public List<UserReport> getUserReportDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_REPORT, ScanOptions.NONE);
        List<UserReport> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 blog_idH和user_id
            String[] split = key.split("::");
            Integer user_id = Integer.parseInt(split[0]);
            Integer reporter_id = Integer.parseInt(split[1]);
            String value = (String) entry.getValue();

            //组装成 UserReport 对象
            UserReport userReport = new UserReport(user_id, reporter_id, value);
            list.add(userReport);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_REPORT, key);
        }
        return list;
    }

    @Override
    public List<BlogVoteCountDTO> getVoteCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, ScanOptions.NONE);
        List<BlogVoteCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            BlogVoteCountDTO dto = new BlogVoteCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public void deleteVoteCount(List<Integer> list) {
        for (Integer key : list) {
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, key);
        }
    }

    @Override
    public List<BlogSingleCountDTO> getBlogReportCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_REPORT_COUNT, ScanOptions.NONE);
        List<BlogSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            BlogSingleCountDTO dto = new BlogSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_REPORT_COUNT, key);
        }
        return list;
    }

    @Override
    public List<BlogSingleCountDTO> getBlogForwardCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_FORWARD_COUNT, ScanOptions.NONE);
        List<BlogSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            BlogSingleCountDTO dto = new BlogSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public void deleteBlogForwardCount(List<Integer> list) {
        for (Integer key : list) {
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_FORWARD_COUNT, key);
        }
    }

    @Override
    public List<BlogSingleCountDTO> getBlogCommentCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_COMMENT_COUNT, ScanOptions.NONE);
        List<BlogSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            BlogSingleCountDTO dto = new BlogSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public void deleteBlogCommentCount(List<Integer> list) {
        for (Integer key : list) {
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_COMMENT_COUNT, key);
        }
    }

    @Override
    public List<UserSingleCountDTO> getUserFollowCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_FOLLOW_COUNT, ScanOptions.NONE);
        List<UserSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();

//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            UserSingleCountDTO dto = new UserSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public void deleteUserFollowCount(List<Integer> list) {
        for (Integer key : list) {
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_FOLLOW_COUNT, key);
        }
    }

    @Override
    public List<UserSingleCountDTO> getUserFanCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_FAN_COUNT, ScanOptions.NONE);
        List<UserSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();

//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            UserSingleCountDTO dto = new UserSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public void deleteUserFanCount(List<Integer> list) {
        for (Integer key : list) {
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_FAN_COUNT, key);
        }
    }

    @Override
    public List<UserSingleCountDTO> getUserBlogCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_BLOG_COUNT, ScanOptions.NONE);
        List<UserSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();

//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            UserSingleCountDTO dto = new UserSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public void deleteUserBlogCount(List<Integer> list) {
        for (Integer key : list) {
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_BLOG_COUNT, key);
        }
    }

    @Override
    public List<UserSingleCountDTO> getUserReportCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_REPORT_COUNT, ScanOptions.NONE);
        List<UserSingleCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();

//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            UserSingleCountDTO dto = new UserSingleCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_REPORT_COUNT, key);
        }
        return list;
    }

    @Override
    public Integer getBlogCommentCountFromRedis(Integer blog_id) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_BLOG_COMMENT_COUNT, blog_id);
        if (obj == null) {
            return null;
        } else
            return (Integer) obj;
    }

    @Override
    public Integer getBlogForwardCountFromRedis(Integer blog_id) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_BLOG_FORWARD_COUNT, blog_id);
        if (obj == null) {
            return null;
        } else
            return (Integer) obj;
    }

    @Override
    public Integer getUserFollowCountFromRedis(Integer user_id) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_FOLLOW_COUNT, user_id);
        if (obj == null) {
            return null;
        } else
            return (Integer) obj;
    }

    @Override
    public Integer getUserFanCountFromRedis(Integer user_id) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_FAN_COUNT, user_id);
        if (obj == null) {
            return null;
        } else
            return (Integer) obj;
    }

    @Override
    public Integer getUserBlogCountFromRedis(Integer user_id) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_BLOG_COUNT, user_id);
        if (obj == null) {
            return null;
        } else
            return (Integer) obj;
    }

    @Override
    public Integer findStatusFromRedis(Integer blog_id, Integer user_id) {
        String key = RedisKeyUtils.getVoteKey(blog_id, user_id);
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG, key);
        if (obj == null) {
            //为空，说明redis没有数据，
            return -1;
        } else {
            return (Integer) obj;
        }
    }

    @Override
    public boolean blogIsReported(Integer blog_id, Integer user_id) {
        String key = RedisKeyUtils.getBlogReportKey(blog_id, user_id);
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_BLOG_REPORT, key);
        if (obj == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean userIsReported(Integer user_id, Integer reporter_id) {
        String key = RedisKeyUtils.getUserReportKey(user_id, reporter_id);
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_REPORT, key);
        if (obj == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer getVoteCountFromRedis(Integer blog_id) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, blog_id);
        if (obj == null) {
            return null;
        } else
            return (Integer) obj;
    }

    @Override
    public void saveBlogReport2Redis(Integer blog_id, Integer user_id, String report_reason) {
        String key = RedisKeyUtils.getBlogReportKey(blog_id, user_id);

        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_BLOG_REPORT, key, report_reason);
    }

    @Override
    public void saveUserReport2Redis(Integer user_id, Integer reporter_id, String report_reason) {
        String key = RedisKeyUtils.getUserReportKey(user_id, reporter_id);

        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_REPORT, key, report_reason);
    }

    @Override
    public void incrementUserFollowCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_FOLLOW_COUNT, user_id, 1);
    }

    @Override
    public void incrementUserFanCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_FAN_COUNT, user_id, 1);
    }

    @Override
    public void incrementUserBlogCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_BLOG_COUNT, user_id, 1);
    }

    @Override
    public void incrementBlogCommentCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_COMMENT_COUNT, blog_id, 1);
    }

    @Override
    public void incrementBlogReportCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_REPORT_COUNT, blog_id, 1);
    }

    @Override
    public void incrementUserReportCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_REPORT_COUNT, user_id, 1);
    }

    @Override
    public void incrementBlogForwardCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_FORWARD_COUNT, blog_id, 1);
    }

    @Override
    public void decrementUserFollowCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_FOLLOW_COUNT, user_id, -1);
    }

    @Override
    public void decrementUserFanCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_FAN_COUNT, user_id, -1);
    }

    @Override
    public void decrementUserBlogCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_BLOG_COUNT, user_id, -1);
    }

    @Override
    public void decrementBlogCommentCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_COMMENT_COUNT, blog_id, -1);
    }

    @Override
    public void decrementBlogReportCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_REPORT_COUNT, blog_id, -1);
    }

    @Override
    public void decrementUserReportCount(Integer user_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_REPORT_COUNT, user_id, -1);
    }

    @Override
    public void decrementBlogForwardCount(Integer blog_id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_FORWARD_COUNT, blog_id, -1);
    }

    @Override
    public void setVoteCount(Integer blog_id, Integer count) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, blog_id, count);
    }

    @Override
    public void setBlogCommentCount(Integer blog_id, Integer count) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_BLOG_COMMENT_COUNT, blog_id, count);
    }

    @Override
    public void setBlogForwardCount(Integer blog_id, Integer count) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_BLOG_FORWARD_COUNT, blog_id, count);
    }

    @Override
    public void setUserFollowCount(Integer user_id, Integer count) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_FOLLOW_COUNT, user_id, count);
    }

    @Override
    public void setUserFanCount(Integer user_id, Integer count) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_FAN_COUNT, user_id, count);
    }

    @Override
    public void setUserBlogCount(Integer user_id, Integer count) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_REPORT_COUNT, user_id, count);
    }

}
