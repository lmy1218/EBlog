package com.lmy.eblog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MPost;
import com.lmy.eblog.mapper.MPostMapper;
import com.lmy.eblog.service.MPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.utils.RedisUtil;
import com.lmy.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
@Service
public class MPostServiceImpl extends ServiceImpl<MPostMapper, MPost> implements MPostService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页查询博客信息
     * @param page 分页信息
     * @param categoryId 分类id
     * @param userId 发布者ID
     * @param level 置顶
     * @param recommend 精选
     * @param order 排序字段
     * @return
     */
    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {
        if(level == null) {
            level = -1;
        }

        // 组装查询条件
        QueryWrapper<MPost> wrapper = new QueryWrapper<MPost>()
                .eq(categoryId != null, "category_id", categoryId)
                .eq(userId != null, "user_id", userId)
                .eq(level == 0, "level", 0)
                .gt(level > 0, "level", 0)
                .orderByDesc(order != null, order);
        // 查询
        return baseMapper.selectPosts(page, wrapper);
    }

    /**
     * 根据id查询博客详情
     * @param id
     * @return
     */
    @Override
    public PostVo selectPostDetail(Long id) {
        // 根据id查询
        QueryWrapper<MPost> wrapper = new QueryWrapper<>();
        wrapper.eq("p.id", id);
        return baseMapper.selectOnePost(wrapper);
    }

    /**
     * 本周热议
     */
    @Override
    public void initWeekRank() {
        // 获取7天内发布的文章
        List<MPost> posts = this.list(new QueryWrapper<MPost>()
                .ge("created", DateUtil.offsetDay(new Date(), -7))
                .select("id, title, user_id, comment_count, view_count, created")
        );

        // 初始化文章的总评论数
        for (MPost post : posts) {
            // 定义ZSet的集合名称: zdd day:rank:20200923   yyyyMMdd
            String key = "day:rank:" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);

            redisUtil.zSet(key, post.getId(), post.getCommentCount());
            // 7天后自动过期
            long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
            long expireTime = (7 - between) * 24 * 60 * 60;

            redisUtil.expire(key, expireTime);
            // 缓存文章的基本信息
            this.hashCachPost(post, expireTime);
        }

        // 做并集
        this.zunionWeekRank();

    }

    // 做并集，合并每日评论的数量
    private void zunionWeekRank() {
        // 当前时间的key
        String currenkey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        // 并集后保存的集合
        String destkey = "week:rank";
        List<String> ortherKeys = new ArrayList<>();
        // 循环添加前七天的key
        for (int i = -7; i < 0; i++) {
            String temp = "day:rank:" +
                    DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);
            ortherKeys.add(temp);
        }

        redisUtil.zUnionAndStore(currenkey, ortherKeys, destkey);

    }

    // 缓存博客的基本信息
    private void hashCachPost(MPost post, long expireTime) {
        String key = "rank:post:" + post.getId();
        if(! redisUtil.hasKey(key)) {
            redisUtil.hset(key, "post:id", post.getId(), expireTime);
            redisUtil.hset(key, "post:title", post.getTitle(), expireTime);
            redisUtil.hset(key, "post:commentCount", post.getCommentCount(), expireTime);
        }
    }
}
