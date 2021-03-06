package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/10/3 22:56
 * @version V1.0
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lmy.eblog.pojo.dto.ResultDto;
import com.lmy.eblog.pojo.entity.MPost;
import com.lmy.eblog.pojo.entity.MUser;
import com.lmy.eblog.pojo.entity.MUserCollection;
import com.lmy.eblog.pojo.entity.MUserMessage;
import com.lmy.eblog.extension.provider.AliyunProvider;
import com.lmy.eblog.extension.shiro.UserInfo;
import com.lmy.eblog.pojo.vo.UserCommentVo;
import com.lmy.eblog.pojo.vo.UserMessageVo;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Lmy
 * @ClassName UserController
 * @Description 用户控制器
 * @date 2020/10/3 22:56
 **/
@Controller
public class UserController extends BaseController {

    @Autowired
    private AliyunProvider aliyunProvider;




    /**
     * 展示我的主页
     * @return
     */
    @GetMapping("/user/{id}")
    public String home(@PathVariable("id") Long id) {

        // 查询用户信息
        MUser user = mUserServiceImpl.getById(id);

        // 展示最近评论
        List<UserCommentVo> userCommentVo = mUserActionServiceImpl.selectList(id);
        // 查询出最近三十天发布的文章
        List<MPost> posts = mPostServiceImpl.list(new QueryWrapper<MPost>()
                .eq("user_id", id)
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
    @GetMapping("user/set")
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
    @PostMapping("user/set")
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
            // 将用户信息存入session
            SecurityUtils.getSubject().getSession().setAttribute("userInfo", userInfo);
            ResultDto.ok().action("/user/set#avatar");
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
        // 将用户信息存入session
        SecurityUtils.getSubject().getSession().setAttribute("userInfo", userInfo);
        return ResultDto.ok().action("/user/set#info");
    }


    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("user/upload")
    @ResponseBody
    public ResultDto<String> uploadAvatar(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            return ResultDto.fail("文件为空！");
        }
        String url = "";
        try {
            // 上传到OSS并返回URL
            url = aliyunProvider.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultDto.success(url);
    }


    /**
     * 修改密码
     * @param nowpass
     * @param password
     * @param repass
     * @return
     */
    @PostMapping("user/repass")
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


    /**
     * 展示用户中心
     * @return
     */
    @GetMapping("user/index")
    public String index() {
        // 查询出用户信息
        MUser oldUser = mUserServiceImpl.getById(getUserInfo().getId());
        //查询出发布和收藏的数量
        int postCount = mPostServiceImpl.count(new QueryWrapper<MPost>().eq("user_id", oldUser.getId()));
        int colectionCount = mUserCollectionServiceImpl.count(new QueryWrapper<MUserCollection>().eq("user_id", oldUser.getId()));

        req.setAttribute("postCount", postCount);
        req.setAttribute("coleCount", colectionCount);
        return "/user/index";
    }


    /**
     * 展示消息中心
     * @return
     */
    @GetMapping("user/message")
    public String message() {
        // 查询出当前用户的消息
        IPage<UserMessageVo> page = mUserMessageServiceImpl.paging(getPage(), new QueryWrapper<MUserMessage>()
                .eq("to_user_id", getUserInfo().getId())
                .orderByDesc("created")
        );
        // 把消息改成已读状态
        List<Long> ids = new ArrayList<>();
        for(UserMessageVo messageVo : page.getRecords()) {
            if(messageVo.getStatus() == 0) {
                ids.add(messageVo.getId());
            }
        }
        // 批量修改成已读
        mUserMessageServiceImpl.updateToReaded(ids);
        req.setAttribute("messages", page);
        return "/user/message";
    }

    /**
     * 获取我发布的文章
     * @return
     */
    @GetMapping("user/public")
    @ResponseBody
    public ResultDto userPublic() {
        // 根据用户ID查询发布的文章
        IPage page = mPostServiceImpl.page(getPage(), new QueryWrapper<MPost>()
                .eq("user_id", getUserInfo().getId()).orderByDesc("created"));

        return ResultDto.success(page);
    }

    /**
     * 获取我收藏的文章
     * @return
     */
    @GetMapping("user/collection")
    @ResponseBody
    public ResultDto collection() {
        // 查询收藏的文章
        IPage page = mPostServiceImpl.page(getPage(), new QueryWrapper<MPost>()
                .inSql("id", "select post_id from m_user_collection where user_id = " + getUserInfo().getId()));

        return ResultDto.success(page);
    }


    /**
     *  清楚消息
     * @param id 消息id
     * @param all 是否清除所有消息
     * @return
     */
    @PostMapping("message/remove")
    @ResponseBody
    public ResultDto msgResmove(Long id, @RequestParam(defaultValue = "false") Boolean all) {
        boolean remove = mUserMessageServiceImpl.remove(new QueryWrapper<MUserMessage>()
                .eq("to_user_id", getUserInfo().getId())
                .eq(!all, "id", id)
        );

        return remove ?  ResultDto.ok() : ResultDto.fail("删除失败");
    }


    @PostMapping("user/message/nums")
    @ResponseBody
    public Map msgNums() {
        // 查询当前用户未读的消息
        int count = mUserMessageServiceImpl.count(new QueryWrapper<MUserMessage>()
                .eq("to_user_id", getUserInfo().getId())
                .eq("status", 0)
        );
        return MapUtil.builder("status", 0).put("count", count).build();
    }



    @GetMapping("user/reply-Week-List")
    public ResultDto<List<User>> getUserReplyList() {
        return mUserActionServiceImpl.findUserReplyList();
    }




}
