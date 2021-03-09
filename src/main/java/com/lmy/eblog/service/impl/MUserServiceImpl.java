package com.lmy.eblog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmy.eblog.pojo.dto.ResultDto;
import com.lmy.eblog.pojo.entity.MUser;
import com.lmy.eblog.mapper.MUserMapper;
import com.lmy.eblog.service.MUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmy.eblog.extension.shiro.UserInfo;
import org.apache.catalina.User;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
@Service
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements MUserService {


    /**
     * 用户新增
     * @param user
     * @return
     */
    @Override
    public ResultDto<Void> insUser(MUser user) {
        // 验证邮箱是否重复
        int count = this.count(new QueryWrapper<MUser>().eq("email", user.getEmail()));
        if (count > 0) {
            return ResultDto.fail("邮箱不能重复");
        }
        // 组装User
        Date date = new Date();
        MUser temp = new MUser();
        temp.setAvatar("/res/images/avatar/default.png");
        temp.setUsername(user.getUsername());
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setEmail(user.getEmail());
        temp.setCreated(date);
        temp.setModified(date);
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);
        temp.setGender("0");
        // 存入数据库
        this.save(temp);
        return ResultDto.ok();
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserInfo login(String username, String password) {
        // 根据用户名查询用户
        MUser user = this.getOne(new QueryWrapper<MUser>().eq("email", username));
        if (user == null) {
            throw new UnknownAccountException();
        }
        // 验证密码
        if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException();
        }
        // 更新用户最后登录时间
        user.setLasted(new Date());
        this.updateById(user);
        // 返回用户信息
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }



}
