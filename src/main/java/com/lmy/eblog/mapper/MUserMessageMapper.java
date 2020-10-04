package com.lmy.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MUserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmy.eblog.vo.UserMessageVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MUserMessageMapper extends BaseMapper<MUserMessage> {

    IPage<UserMessageVo> selectMessgeVo(Page page, @Param(Constants.WRAPPER) QueryWrapper<MUserMessage> wrapper);
}
