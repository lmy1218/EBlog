package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/30 9:48
 * @version V1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lmy.eblog.vo.CommentVo;
import com.lmy.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Lmy
 * @ClassName PostController
 * @Description 博客控制器
 * @date 2020/9/30 9:48
 **/
@Controller
public class PostController extends BaseController {

    /**
     * 分类
     *  {id:\d*} 可以指定id的类型为数字类型
     * @param id
     * @return
     */
    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name = "id") Long id) {
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);

        req.setAttribute("currentCategoryId", id);
        req.setAttribute("pn", pn);
        return "post/category";
    }


    /**
     * 博客详情控制器
     * @param id
     * @return
     */
    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        // 查询博客详情
        PostVo post = mPostServiceImpl.selectPostDetail(id);
        // 断言
        Assert.notNull(post, "文章不存在！");
        // 查询评论信息
        // 参数： 分页   文章ID    用户ID    排序
        IPage<CommentVo> comments = mCommentServiceImpl.selectPageComments(getPage(), post.getId(), null, "created");
        // 断言
        Assert.notNull(comments, "无评论信息");

        // 将结果集放入response作用域
        req.setAttribute("comments", comments);
        req.setAttribute("post", post);
        req.setAttribute("currentCategoryId", post.getCategoryId());
        return "post/detail";
    }

}
