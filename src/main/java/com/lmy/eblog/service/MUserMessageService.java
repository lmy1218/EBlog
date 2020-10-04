package com.lmy.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MUserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.vo.UserMessageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MUserMessageService extends IService<MUserMessage> {

    IPage<UserMessageVo> paging(Page page, QueryWrapper<MUserMessage> wrapper);
}
