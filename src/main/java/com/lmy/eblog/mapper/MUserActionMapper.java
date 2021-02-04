package com.lmy.eblog.mapper;

import com.lmy.eblog.pojo.entity.MUserAction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmy.eblog.pojo.vo.UserCommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MUserActionMapper extends BaseMapper<MUserAction> {

    UserCommentVo selectCommentInfo(@Param("postId") Long postId, @Param("commentId") Long commentId);
}
