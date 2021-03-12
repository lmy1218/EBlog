package com.lmy.eblog.mapper;

import com.lmy.eblog.pojo.dto.UserReplyListDto;
import com.lmy.eblog.pojo.entity.MUserAction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmy.eblog.pojo.vo.UserCommentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("select user_id as userId, count(comment_id) as count from m_user_action group by user_id order by count desc")
    List<UserReplyListDto> selectUserReply();
}
