package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dto.BlogVoteCountDTO;
import com.example.amoy_interest.entity.BlogVote;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.VoteService;
import com.example.amoy_interest.utils.JedisUtil;
import com.example.amoy_interest.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
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
    private VoteService voteService;

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
        //数据不存在会自动初始化为0再加一
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
    public List<BlogVoteCountDTO> getVoteCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, ScanOptions.NONE);
        List<BlogVoteCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
//            String key = (String)map.getKey();
            Integer key = (Integer) map.getKey();
            BlogVoteCountDTO dto = new BlogVoteCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_VOTE_COUNT, key);
        }
        return list;
    }

    @Override
    public Integer findStatusFromRedis(Integer blog_id, Integer user_id) {
        String key = RedisKeyUtils.getVoteKey(blog_id, user_id);
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_VOTE_BLOG,key);
        if(obj == null) {
            //为空，说明redis没有数据，
            return -1;
        }else{
            return (Integer) obj;
        }
    }
}
