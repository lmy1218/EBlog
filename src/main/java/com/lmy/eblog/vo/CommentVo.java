package com.lmy.eblog.vo;
/**
 * @Project eblog
 * @Package com.lmy.eblog.vo
 * @author Administrator
 * @date 2020/10/2 16:33
 * @version V1.0
 */

import com.lmy.eblog.entity.MComment;
import lombok.Data;

/**
 * @author Lmy
 * @ClassName CommentVo
 * @Description 评论信息封装返回接口
 * @date 2020/10/2 16:33
 **/
@Data
public class CommentVo extends MComment {

    // 发布者ID
    private Long authorId;
    // 发布者名称
    private String authorName;
    // 发布者头像
    private String authorAvatar;


}
