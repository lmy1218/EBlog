package com.lmy.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmy.eblog.vo.PostVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MPostMapper extends BaseMapper<MPost> {

    IPage<PostVo> selectPosts(Page page, @Param(Constants.WRAPPER) QueryWrapper<MPost> wrapper);

    PostVo selectOnePost(@Param(Constants.WRAPPER) QueryWrapper<MPost> wrapper);

    PostVo selectPost(@Param(Constants.WRAPPER) QueryWrapper<MPost> wrapper);
}
