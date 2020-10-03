package com.lmy.eblog.service;

import com.lmy.eblog.entity.MUserAction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.vo.UserCommentVo;

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
}
