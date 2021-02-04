package com.lmy.eblog.extension.shiro;
/**
 * @Project eblog
 * @Package com.lmy.eblog.extension.shiro
 * @author Administrator
 * @date 2020/10/3 21:34
 * @version V1.0
 */

import com.lmy.eblog.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lmy
 * @ClassName UserInfo
 * @Description 用户信息
 * @date 2020/10/3 21:34
 **/
@Data
public class UserInfo extends BaseEntity implements Serializable {

    // id
    private Long id;
    // 用户名
    private String username;
    // 邮箱
    private String email;
    // 头像
    private String avatar;
    // 性别 0 男 1 女
    private String gender;

    public String getSex() {
        return "0".equals(gender) ? "男" : "女";
    }

}
