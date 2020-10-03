package com.lmy.eblog.service;

import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.MUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmy.eblog.shiro.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-09-30
 */
public interface MUserService extends IService<MUser> {

    ResultDto<Void> insUser(MUser user);

    UserInfo login(String username, String password);
}
