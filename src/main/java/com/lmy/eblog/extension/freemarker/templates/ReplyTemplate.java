package com.lmy.eblog.extension.freemarker.templates;

import com.lmy.eblog.extension.freemarker.templates.common.DirectiveHandler;
import com.lmy.eblog.extension.freemarker.templates.common.TemplateDirective;
import com.lmy.eblog.pojo.dto.ResultDto;
import com.lmy.eblog.service.MUserActionService;
import com.lmy.eblog.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 本周热议
 */
@Component
public class ReplyTemplate extends TemplateDirective {

    @Autowired
    private MUserActionService mUserActionService;

    @Override
    public String getName() {
        return "reply";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        ResultDto<List<User>> userReplyList = mUserActionService.findUserReplyList();
        handler.put(RESULTS, userReplyList.getData()).render();
    }
}
