package com.lmy.eblog.pojo.vo;
/**
 * @Project eblog
 * @Package com.lmy.eblog.pojo.vo
 * @author Administrator
 * @date 2020/10/1 12:08
 * @version V1.0
 */

import com.lmy.eblog.pojo.entity.MPost;
import lombok.Data;

/**
 * @author Lmy
 * @ClassName PageVo
 * @Description 分页展示的博客信息
 * @date 2020/10/1 12:08
 **/
@Data
public class PostVo extends MPost {
    // 发布者ID
    private Long authorId;
    // 发布者名称
    private String authorName;
    // 发布者头像
    private String authorAvatar;
    // 分类名称
    private String categoryName;

}
