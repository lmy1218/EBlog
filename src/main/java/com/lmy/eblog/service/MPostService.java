package com.lmy.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.vo.PageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MPostService extends IService<MPost> {

    IPage<PageVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);
}
