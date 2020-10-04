package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/10/3 22:56
 * @version V1.0
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.MPost;
import com.lmy.eblog.entity.MUser;
import com.lmy.eblog.provider.AliyunProvider;
import com.lmy.eblog.shiro.UserInfo;
import com.lmy.eblog.vo.UserCommentVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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

    @Autowired
    private AliyunProvider aliyunProvider;

    /**
     * 展示我的主页
     * @return
     */
    @GetMapping("home")
    public String home() {
        // 查询出用户信息
        MUser user = mUserServiceImpl.getById(getUserInfo().getId());
        user.setPassword(null);
        // 展示最近评论
        List<UserCommentVo> userCommentVo = mUserActionServiceImpl.selectList(user.getId());
        // 查询出最近三十天发布的文章
        List<MPost> posts = mPostServiceImpl.list(new QueryWrapper<MPost>()
                .eq("user_id", user.getId())
                .gt("created", DateUtil.offsetDay(new Date(),-30))
                .orderByDesc("created")
        );
        // 将结果返回到response作用域
        req.setAttribute("user", user);
        req.setAttribute("posts", posts);
        req.setAttribute("commentInfo", userCommentVo);
        return "/user/home";
    }


    /**
     * 展示用户设置
     * @return
     */
    @GetMapping("set")
    public String userSet() {
        // 查询出用户信息
        MUser user = mUserServiceImpl.getById(getUserInfo().getId());
        if (user != null) {
            user.setPassword(null);
        }
        req.setAttribute("user", user);
        return "/user/set";
    }


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("set")
    @ResponseBody
    public ResultDto doSet(MUser user) {
        if (user == null) {
            return ResultDto.fail("用户数据为空");
        }
        if (StrUtil.isNotBlank(user.getAvatar())) {
            // 查询出用户信息
            MUser oldUser = mUserServiceImpl.getById(getUserInfo().getId());
            oldUser.setAvatar(user.getAvatar());
            // 更新
            mUserServiceImpl.updateById(oldUser);
            // 更新shiro中的用户信息
            UserInfo userInfo = getUserInfo();
            userInfo.setAvatar(user.getAvatar());
            SecurityUtils.getSubject().getSession().setAttribute("user", userInfo);
        }
        // 查询出用户信息
        MUser oldUser = mUserServiceImpl.getById(getUserInfo().getId());
        if (user.getEmail() == null || !user.getEmail().equals(oldUser.getEmail())) {
            return ResultDto.fail("邮箱不能更改或邮箱为空！");
        }

        // 组装User
        oldUser.setGender(user.getGender());
        oldUser.setUsername(user.getUsername());
        oldUser.setCity(user.getCity());
        oldUser.setMobile(user.getMobile());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setSign(user.getSign());
        oldUser.setWechat(user.getWechat());
        // 更新
        mUserServiceImpl.updateById(oldUser);
        // 更新shiro中的用户信息
        UserInfo userInfo = getUserInfo();
        if (StrUtil.isNotBlank(user.getAvatar())) {
            userInfo.setAvatar(user.getAvatar());
        }
        userInfo.setUsername(oldUser.getUsername());

        return ResultDto.ok().action("/user/set#info");
    }


    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public ResultDto<String> uploadAvatar(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            return ResultDto.fail("文件为空！");
        }
        String url = "";
        try {
            url = aliyunProvider.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultDto.success(url);
    }


    @PostMapping("repass")
    @ResponseBody
    public ResultDto repss(String nowpass, String password, String repass) {
        if (password == null || !password.equals(repass)) {
            return ResultDto.fail("新密码为空或两次密码不一致！");
        }
        // 查询出用户信息
        MUser oldUser = mUserServiceImpl.getById(getUserInfo().getId());
        if (nowpass == null || !SecureUtil.md5(nowpass).equals(oldUser.getPassword())) {
            return ResultDto.fail("旧密码不正确！");
        }

        oldUser.setPassword(SecureUtil.md5(password));
        mUserServiceImpl.updateById(oldUser);
        SecurityUtils.getSubject().logout();

        return ResultDto.success("请求成功请重新登录", null).action("/user/set#pass");
    }

}
