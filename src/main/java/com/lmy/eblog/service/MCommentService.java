package com.lmy.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.pojo.entity.MComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.pojo.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MCommentService extends IService<MComment> {

    IPage<CommentVo> selectPageComments(Page page, Long postId, Long userId, String order);
}
