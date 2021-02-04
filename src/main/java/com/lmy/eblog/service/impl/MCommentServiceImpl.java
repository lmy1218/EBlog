package com.lmy.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.pojo.entity.MComment;
import com.lmy.eblog.mapper.MCommentMapper;
import com.lmy.eblog.service.MCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.pojo.vo.CommentVo;
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
public class MCommentServiceImpl extends ServiceImpl<MCommentMapper, MComment> implements MCommentService {

    @Override
    public IPage<CommentVo> selectPageComments(Page page, Long postId, Long userId, String order) {
        // 组装查询条件
        QueryWrapper<MComment> wrapper = new QueryWrapper<>();
        wrapper.eq(postId != null, "post_id", postId)
                .eq(userId != null, "user_id", userId)
                .orderByDesc(order);
        // 查询并返回
        return baseMapper.selectComments(page, wrapper);
    }
}
