package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/30 10:47
 * @version V1.0
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.search.service.SearchService;
import com.lmy.eblog.service.*;
import com.lmy.eblog.service.impl.MCategoryServiceImpl;
import com.lmy.eblog.shiro.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lmy
 * @ClassName BaseController
 * @Description 控制器公共部分
 * @date 2020/9/30 10:47
 **/
@Controller
public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    MPostService mPostServiceImpl;

    @Autowired
    MCommentService mCommentServiceImpl;

    @Autowired
    MUserActionService mUserActionServiceImpl;

    @Autowired
    MUserService mUserServiceImpl;

    @Autowired
    MUserCollectionService mUserCollectionServiceImpl;

    @Autowired
    MUserMessageService mUserMessageServiceImpl;

    @Autowired
    MCategoryService mCategoryServiceImpl;

    @Autowired
    WsService wsServiceImpl;

    @Autowired
    SearchService searchServiceImpl;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    ChatService chatServiceImpl;


    // 获取分页参数
    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 6);
        // 分页查询博客
        return new Page(pn, size);
    }

    // 获取用户信息
    public UserInfo getUserInfo() {
        return (UserInfo) SecurityUtils.getSubject().getPrincipal();
    }

}
