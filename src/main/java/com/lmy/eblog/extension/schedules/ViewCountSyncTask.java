package com.lmy.eblog.extension.schedules;
/**
 * @Project eblog
 * @Package com.lmy.eblog.extension.schedules
 * @author Administrator
 * @date 2020/10/3 14:26
 * @version V1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmy.eblog.pojo.entity.MPost;
import com.lmy.eblog.service.MPostService;
import com.lmy.eblog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lmy
 * @ClassName ViewCountSyncTask
 * @Description 定时器：定时更新阅读数到数据库
 * @date 2020/10/3 14:26
 **/
@Slf4j
@Component
public class ViewCountSyncTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MPostService mPostServiceImpl;


    @Scheduled(cron = "0 */1 * * * ?")
    public void task() {
        log.info("----定时器开始同步阅读数到数据库！----");
        // 查询出所有缓存文章的key
        Set<String> keys = redisTemplate.keys("rank:post:*");
        // 把存在viewCount的文章过滤出来
        List<String> ids = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (String key : keys) {
            if (redisUtil.hHasKey(key, "post:viewCount")) {
                String postId = key.substring("rank:post:".length());
                Integer viewCount = (Integer) redisUtil.hget(key, "post:viewCount");
                map.put(postId, viewCount);
                ids.add(postId);
            }
        }
        if (ids.isEmpty()) {
            return;
        }
        // 更新阅读量
        List<MPost> posts = mPostServiceImpl.list(new QueryWrapper<MPost>().in("id", ids));
        if (posts.isEmpty()) {
            return;
        }
        // 重新设置阅读量
        List<MPost> mPosts = posts.stream().map(p -> {
            p.setViewCount(map.get(p.getId() + ""));
            return p;
        }).collect(Collectors.toList());
        // 更新到数据库
        boolean isSucc = mPostServiceImpl.updateBatchById(mPosts);
        if (isSucc) {
            ids.stream().forEach(id -> {
                redisUtil.hdel("rank:post:" + id, "post:viewCount");
            });
            log.info("---------阅读数同步成功--------");
        }
    }

}
