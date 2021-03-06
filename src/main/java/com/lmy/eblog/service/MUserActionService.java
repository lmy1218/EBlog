package com.lmy.eblog.service;

import com.lmy.eblog.pojo.dto.ResultDto;
import com.lmy.eblog.pojo.entity.MUserAction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.pojo.vo.UserCommentVo;
import org.apache.catalina.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MUserActionService extends IService<MUserAction> {

    List<UserCommentVo> selectList(Long postId);

    ResultDto<List<User>> findUserReplyList();
}
