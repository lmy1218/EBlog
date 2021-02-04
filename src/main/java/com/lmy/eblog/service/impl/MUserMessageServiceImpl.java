package com.lmy.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.pojo.entity.MUserMessage;
import com.lmy.eblog.mapper.MUserMessageMapper;
import com.lmy.eblog.service.MUserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.pojo.vo.UserMessageVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmy
 * @since 2020-09-30
 */
@Service
public class MUserMessageServiceImpl extends ServiceImpl<MUserMessageMapper, MUserMessage> implements MUserMessageService {

    @Override
    public IPage<UserMessageVo> paging(Page page, QueryWrapper<MUserMessage> wrapper) {
        return baseMapper.selectMessgeVo(page, wrapper);
    }

    @Override
    public void updateToReaded(List<Long> ids) {
        if(ids.isEmpty()) return;

        baseMapper.updateToReaded(new QueryWrapper<MUserMessage>()
                .in("id", ids)
        );
    }
}
