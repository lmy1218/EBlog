package com.lmy.eblog.templates;

import com.lmy.eblog.templates.common.DirectiveHandler;
import com.lmy.eblog.templates.common.TemplateDirective;
import com.lmy.eblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 本周热议
 */
@Component
public class HotsTemplate extends TemplateDirective {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String getName() {
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String weekRankKey = "week:rank";
        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(weekRankKey, 0, 6);

        List<Map> hotPosts = new ArrayList<>();
        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            Map<String, Object> map = new HashMap<>();
            Object value = typedTuple.getValue(); // post的id
            String postKey = "rank:post:" + value;
            System.out.println("========!!!" + redisUtil.hget(postKey, "post:title"));
            map.put("id", value);
            map.put("title", redisUtil.hget(postKey, "post:title"));
            map.put("commentCount", typedTuple.getScore());
            System.out.println("=============" + map.get("title"));
            hotPosts.add(map);
        }
        handler.put(RESULTS, hotPosts).render();
    }
}
