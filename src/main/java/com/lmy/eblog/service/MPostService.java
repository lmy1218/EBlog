package com.lmy.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.pojo.entity.MPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.pojo.vo.PostVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmy
 * @since 2020-09-30
 */
public interface MPostService extends IService<MPost> {

    IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);

    PostVo selectPostDetail(Long id);

    void initWeekRank();

    void addCommentCountforWeekRank(Long postId, boolean isAdd);

    void putViewCount(PostVo post);

    PostVo selectOnePost(QueryWrapper<MPost> wrapper);
}
