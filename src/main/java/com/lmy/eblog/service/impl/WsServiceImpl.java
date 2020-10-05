package com.lmy.eblog.service.impl;
/**
 * @Project eblog
 * @Package com.lmy.eblog.service.impl
 * @author Administrator
 * @date 2020/10/5 22:02
 * @version V1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmy.eblog.entity.MUserMessage;
import com.lmy.eblog.service.MUserMessageService;
import com.lmy.eblog.service.WsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Lmy
 * @ClassName WsServiceImpl
 * @Description webSocket往通道发送消息
 * @date 2020/10/5 22:02
 **/
@Service
public class WsServiceImpl implements WsService {

    @Autowired
    MUserMessageService mUserMessageServiceImpl;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @Override
    public void sendMessCountToUser(Long userId) {
        //  查询未读消息的数量
        int count = mUserMessageServiceImpl.count(new QueryWrapper<MUserMessage>()
                .eq("status", 0)
                .eq("to_user_id", userId)
        );

        // webSocket通知 (user/20/messCount)
        simpMessagingTemplate.convertAndSendToUser(userId.toString(), "/messCount", count);
    }
}
