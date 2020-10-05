package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/10/5 20:01
 * @version V1.0
 */

import cn.hutool.core.map.MapUtil;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.MPost;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lmy
 * @ClassName AdminController
 * @Description 管理员控制器
 * @date 2020/10/5 20:01
 **/
@Controller
@RequestMapping("admin")
public class AdminController extends BaseController {


    /**
     * 管理员删除、置顶、加精
     * @param id 文章ID
     * @param rank 0 表示取消 1 表示操作
     * @param field 操作类型
     * @return
     */
    @PostMapping("jie-set")
    @ResponseBody
    public ResultDto jieSet(Long id, Integer rank, String field) {
        // 查询出要处理的文章
        MPost post = mPostServiceImpl.getById(id);
        Assert.notNull(post, "该文章不存在");
        if ("delete".equals(field)) {
            // 删除该文章、以及相关收藏和消息
            mPostServiceImpl.removeById(id);
            mUserMessageServiceImpl.removeByMap(MapUtil.of("post_id", id));
            mUserCollectionServiceImpl.removeByMap(MapUtil.of("post_id", id));
            mUserActionServiceImpl.removeByMap(MapUtil.of("post_id", id));
            return ResultDto.ok();
        } else if ("stick".equals(field)){
            // 置顶
            post.setLevel(rank);
        } else if ("status".equals(field)){
            // 加精
            post.setRecommend(rank == 1);
        }
        // 更新
        mPostServiceImpl.updateById(post);

        return ResultDto.ok();
    }



}
