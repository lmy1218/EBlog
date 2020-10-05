package com.lmy.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MUserAction;
import com.lmy.eblog.mapper.MUserActionMapper;
import com.lmy.eblog.service.MUserActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.vo.UserCommentVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
@Service
public class MUserActionServiceImpl extends ServiceImpl<MUserActionMapper, MUserAction> implements MUserActionService {

    @Override
    public List<UserCommentVo> selectList(Long userId) {
        // 查询出当前用户评论的集合
        Page<MUserAction> page = new Page<>(1, 3);
        IPage<MUserAction> pageResul = baseMapper.selectPage(page, new QueryWrapper<MUserAction>().eq("user_id", userId).orderByDesc("created"));
        List<MUserAction> mUserActions = pageResul.getRecords();
        List<UserCommentVo> list = new ArrayList<>();
        for (MUserAction action : mUserActions) {
            UserCommentVo commentVo = baseMapper.selectCommentInfo(Long.parseLong(action.getPostId()), Long.parseLong(action.getCommentId()));
            Assert.isTrue(commentVo != null, "未查询到回复");
            commentVo.setAction(action.getAction());
            list.add(commentVo);
        }
        return list;
    }
}
