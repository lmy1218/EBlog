package com.lmy.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.lmy.eblog.config.Consts;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.im.vo.ImUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController extends BaseController {

    @GetMapping("/getMineAndGroupData")
    public ResultDto getMineAndGroupData() {

        //默认群
        Map<String, Object> group = new HashMap<>();
        group.put("name", "社区群聊");
        group.put("type", "group");
        group.put("avatar", "https://ferryblog.oss-cn-shenzhen.aliyuncs.com/eblog/508f7854-33a8-44c7-a0ff-54a7069dfb9f.jpg?x-oss-process=image/resize,h_200");
        group.put("id", Consts.IM_GROUP_ID);
        group.put("members", 0);

        ImUser user = chatServiceImpl.getCurrentUser();
        return ResultDto.success(MapUtil.builder()
                .put("group", group)
                .put("mine", user)
                .map());
    }

    @GetMapping("/getGroupHistoryMsg")
    public ResultDto getGroupHistoryMsg() {

        List<Object> messages = chatServiceImpl.getGroupHistoryMsg(20);
        return ResultDto.success(messages);
    }

}
