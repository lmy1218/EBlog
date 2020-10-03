package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/10/3 22:56
 * @version V1.0
 */

import com.lmy.eblog.service.MUserActionService;
import com.lmy.eblog.vo.UserCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Lmy
 * @ClassName UserController
 * @Description 用户控制器
 * @date 2020/10/3 22:56
 **/
@Controller
@RequestMapping("user")
public class UserController extends BaseController {



    /**
     * 展示我的主页
     * @return
     */
    @GetMapping("home")
    public String home() {
        // 展示最近评论
        List<UserCommentVo> userCommentVo = mUserActionServiceImpl.selectList(getUserInfo().getId());

        req.setAttribute("commentInfo", userCommentVo);
        return "/user/home";
    }




}
