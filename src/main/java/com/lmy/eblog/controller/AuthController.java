package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/10/3 15:41
 * @version V1.0
 */

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.code.kaptcha.Producer;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.MUser;
import com.lmy.eblog.service.MUserService;
import com.lmy.eblog.utils.ValidationUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author Lmy
 * @ClassName AuthController
 * @Description 用户授权控制器
 * @date 2020/10/3 15:41
 **/
@Controller
public class AuthController extends BaseController {

    private static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    @Autowired
    private Producer producer;

    @Autowired
    private MUserService mUserServiceImpl;


    /**
     * 展示登录页面
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    /**
     * 做登录逻辑
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultDto<Void> doLogin(String email, String password, String vercode) {
        // 校验验证码
        String sesscode = req.getSession().getAttribute(KAPTCHA_SESSION_KEY) + "";
        if (vercode == null || !vercode.equalsIgnoreCase(sesscode)) {
            return ResultDto.fail("验证码不正确!");
        }

        if (StrUtil.isBlank(email) || StrUtil.isBlank(password)) {
            return ResultDto.fail("邮箱或密码为空！");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(email, SecureUtil.md5(password));
        try {
            // 登录
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return ResultDto.fail( "用户不存在");
            } else if (e instanceof LockedAccountException) {
                return ResultDto.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return ResultDto.fail("密码错误");
            } else {
                return ResultDto.fail("用户认证失败");
            }
        }
        return ResultDto.ok().action("/");
    }


    /**
     * 展示注册页面
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "/auth/reg";
    }


    /**
     * 做注册逻辑
     * @param user
     * @param vercode
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResultDto<Void> doRegister(MUser user, String vercode) {
        // 参数验证
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if (validResult.hasErrors()) {
            return ResultDto.fail(validResult.getErrors());
        }
        // 校验验证码
        String sesscode = req.getSession().getAttribute(KAPTCHA_SESSION_KEY) + "";
        if (vercode == null || !vercode.equalsIgnoreCase(sesscode)) {
            return ResultDto.fail("验证码不正确!");
        }

        // 持久化到数据库
        ResultDto<Void> resultDto = mUserServiceImpl.insUser(user);

        return ResultDto.ok().action("/login");
    }

    /**
     * 获取验证码
     * @param resp
     */
    @GetMapping("/capthca.jpg")
    public void kaptcha(HttpServletResponse resp) {
        // 验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        req.getSession().setAttribute(KAPTCHA_SESSION_KEY, text);
        // 设置浏览器不缓存
        resp.setHeader("Cache-Control", "no-store, no-cache");
        // 告诉浏览器是图片类型
        resp.setContentType("image/jpeg");
        // 以流的形式返回
        try {
            ServletOutputStream outputStream = resp.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/user/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

}
