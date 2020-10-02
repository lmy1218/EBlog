package com.lmy.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MPost;
import com.lmy.eblog.mapper.MPostMapper;
import com.lmy.eblog.service.MPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.vo.PostVo;
import org.springframework.stereotype.Service;

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
}
