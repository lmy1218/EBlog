package com.lmy.eblog.vo;
/**
 * @Project eblog
 * @Package com.lmy.eblog.vo
 * @author Administrator
 * @date 2020/10/3 23:22
 * @version V1.0
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lmy
 * @ClassName UserCommentVo
 * @Description 最近评论结构
 * @date 2020/10/3 23:22
 **/
@Data
public class UserCommentVo implements Serializable {
    // 被回复的id
    private String postId;
    // 文章标题
    private String title;
    // 回复内容
    private String content;
    // 评论类型
    private String action;
    // 创建时间
    private Date created;

}
