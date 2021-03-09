package com.lmy.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.mapper.MUserMapper;
import com.lmy.eblog.pojo.dto.ResultDto;
import com.lmy.eblog.pojo.dto.UserReplyListDto;
import com.lmy.eblog.pojo.entity.MUser;
import com.lmy.eblog.pojo.entity.MUserAction;
import com.lmy.eblog.mapper.MUserActionMapper;
import com.lmy.eblog.service.MUserActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.pojo.vo.UserCommentVo;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
@Service
public class MUserActionServiceImpl extends ServiceImpl<MUserActionMapper, MUserAction>
        implements MUserActionService {

    @Resource private MUserActionMapper mUserActionMapper;

    @Resource private MUserMapper mUserMapper;

    @Override
    public List<UserCommentVo> selectList(Long userId) {
        // 查询出当前用户评论的集合
        Page<MUserAction> page = new Page<>(1, 3);
        IPage<MUserAction> pageResul =
                baseMapper.selectPage(
                        page,
                        new QueryWrapper<MUserAction>()
                                .eq("user_id", userId)
                                .orderByDesc("created"));
        List<MUserAction> mUserActions = pageResul.getRecords();
        List<UserCommentVo> list = new ArrayList<>();
        for (MUserAction action : mUserActions) {
            UserCommentVo commentVo =
                    baseMapper.selectCommentInfo(
                            Long.parseLong(action.getPostId()),
                            Long.parseLong(action.getCommentId()));
            Assert.isTrue(commentVo != null, "未查询到回复");
            commentVo.setAction(action.getAction());
            list.add(commentVo);
        }
        return list;
    }

    @Override
    public ResultDto<List<User>> findUserReplyList() {
        List<UserReplyListDto> userCount = mUserActionMapper.selectUserReply();
        if (CollectionUtils.isEmpty(userCount)) {
            return ResultDto.ok();
        }
        List<Long> userIds =
                userCount.stream().map(UserReplyListDto::getUserId).collect(Collectors.toList());

        Map<Long, MUser> userMap = mUserMapper.selectList(
                new LambdaQueryWrapper<MUser>()
                        .select(MUser::getUsername, MUser::getAvatar, MUser::getId)
                        .in(MUser::getId, userIds)).stream().collect(Collectors.toMap(MUser::getId, u -> u));

        List<UserReplyListDto> data = userCount.stream().map(u -> {
            if (userMap.containsKey(u.getUserId())) {
                MUser mUser = userMap.get(u.getUserId());
                u.setUsername(mUser.getUsername());
                u.setAvatar(mUser.getAvatar());
            }
            return u;
        }).collect(Collectors.toList());

        return ResultDto.success(data);
    }
}
