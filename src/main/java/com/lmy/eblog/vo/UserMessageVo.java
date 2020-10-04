package com.lmy.eblog.vo;

import com.lmy.eblog.entity.MUserMessage;
import lombok.Data;

@Data
public class UserMessageVo extends MUserMessage{

    // 发送者昵称
    private String fromUserName;
    // 文章ID
    private String postTitle;


}
