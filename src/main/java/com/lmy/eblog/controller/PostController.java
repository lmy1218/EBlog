package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/30 9:48
 * @version V1.0
 */

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.MPost;
import com.lmy.eblog.entity.MUserCollection;
import com.lmy.eblog.service.MUserCollectionService;
import com.lmy.eblog.vo.CommentVo;
import com.lmy.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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

        mPostServiceImpl.putViewCount(post);

        // 将结果集放入response作用域
        req.setAttribute("comments", comments);
        req.setAttribute("post", post);
        req.setAttribute("currentCategoryId", post.getCategoryId());
        return "post/detail";
    }


    /**
     * 查询当前文章是否被当前用户收藏
     * @param postId 文章id
     * @return
     */
    @PostMapping("collection/find")
    @ResponseBody
    public ResultDto collectionFind(Long postId) {
        int count = mUserCollectionServiceImpl.count(new QueryWrapper<MUserCollection>()
                .eq("post_id", postId)
                .eq("user_id", getUserInfo().getId())
        );
        return ResultDto.success(MapUtil.of("collection", count > 0));
    }


    /**
     * 收藏
     * @param postId 文章id
     * @return
     */
    @PostMapping("collection/add")
    @ResponseBody
    public ResultDto collectionAdd(Long postId) {
        // 判断文章是否存在
        MPost post = mPostServiceImpl.getById(postId);
        Assert.isTrue(post!=null, "文章已不存在！");

        // 判断文章是否已被收藏
        int cCount = mUserCollectionServiceImpl.count(new QueryWrapper<MUserCollection>()
                .eq("post_id", postId)
                .eq("user_id", getUserInfo().getId())
        );

        if (cCount > 0) {
            ResultDto.fail("文章已收藏，无需重复操作！");
        }

        // 增加收藏
        Date date = new Date();
        MUserCollection collection = new MUserCollection();
        collection.setPostId(postId);
        collection.setUserId(getUserInfo().getId());
        collection.setCreated(date);
        collection.setModified(date);
        collection.setPostUserId(post.getUserId());

        // 保存
        mUserCollectionServiceImpl.save(collection);

        return ResultDto.ok();
    }


    /**
     * 删除收藏
     * @param postId
     * @return
     */
    @PostMapping("collection/remove")
    @ResponseBody
    public ResultDto collectionRemove(Long postId) {
        // 判断文章是否存在
        MPost post = mPostServiceImpl.getById(postId);
        Assert.isTrue(post!=null, "文章已不存在！");
        // 删除收藏
        boolean remove = mUserCollectionServiceImpl.remove(new QueryWrapper<MUserCollection>()
                .eq("post_id", postId)
                .eq("user_id", getUserInfo().getId())
        );
        if (!remove) {
            ResultDto.fail("收藏不存在！");
        }
        return ResultDto.ok();
    }

}
