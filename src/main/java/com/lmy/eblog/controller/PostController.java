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
import com.lmy.eblog.config.RabbitConfig;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.*;
import com.lmy.eblog.mq.PostMqIndexMessage;
import com.lmy.eblog.service.MCategoryService;
import com.lmy.eblog.service.MUserCollectionService;
import com.lmy.eblog.utils.ValidationUtil;
import com.lmy.eblog.vo.CommentVo;
import com.lmy.eblog.vo.PostVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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


    /**
     * 展示发布或编辑页面
     * @return
     */
    @GetMapping("/post/edit")
    public String showEdit() {
        // 判断是发布还是编辑
        String id = req.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            // 编辑
            // 验证
            MPost post = mPostServiceImpl.getById(id);
            Assert.isTrue(post != null, "该文章已不存在！");
            Assert.isTrue(post.getUserId().longValue() == getUserInfo().getId().longValue(), "没有权限！");
            req.setAttribute("post", post);
        }
        req.setAttribute("categories", mCategoryServiceImpl.list());
        return "/post/edit";
    }


    /**
     * 文章新增或编辑提交
     * @param post
     * @return
     */
    @PostMapping("/post/submit")
    @ResponseBody
    public ResultDto postAdd(MPost post) {
        // 校验参数
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(post);
        if (validResult.hasErrors()) {
            return ResultDto.fail(validResult.getErrors());
        }
        Long userId = getUserInfo().getId();
        if(post.getId() == null) {
            // 新增
            post.setUserId(userId);

            post.setModified(new Date());
            post.setCreated(new Date());
            post.setCommentCount(0);
            post.setEditMode(null);
            post.setLevel(0);
            post.setRecommend(false);
            post.setViewCount(0);
            post.setVoteDown(0);
            post.setVoteUp(0);
            mPostServiceImpl.save(post);

        } else {
            // 编辑
            MPost tempPost = mPostServiceImpl.getById(post.getId());
            Assert.isTrue(tempPost.getUserId().longValue() == userId.longValue(), "无权限编辑此文章！");

            tempPost.setTitle(post.getTitle());
            tempPost.setContent(post.getContent());
            tempPost.setCategoryId(post.getCategoryId());
            mPostServiceImpl.updateById(tempPost);
        }
        // 通知mq告知es更新
        amqpTemplate.convertAndSend(RabbitConfig.es_exchage, RabbitConfig.es_bind_key,
                new PostMqIndexMessage(post.getId(), PostMqIndexMessage.CREATE_OR_UPDATE));
        return ResultDto.ok().action("/post/" + post.getId());
    }


    /**
     * 文章删除
     * @param id
     * @return
     */
    @PostMapping("/post/delete")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResultDto postDelete(Long id) {
        // 验证
        MPost post = mPostServiceImpl.getById(id);
        Assert.isNull(post, "该文章不存在");
        Assert.isTrue(post.getUserId().longValue() == getUserInfo().getId().longValue(), "小伙子，这不是你发布的文章哟！");
        // 删除该文章、以及相关收藏和消息
        mPostServiceImpl.removeById(id);
        mUserMessageServiceImpl.removeByMap(MapUtil.of("post_id", id));
        mUserCollectionServiceImpl.removeByMap(MapUtil.of("post_id", id));
        mUserActionServiceImpl.removeByMap(MapUtil.of("post_id", id));

        // 通知mq告知es更新
        amqpTemplate.convertAndSend(RabbitConfig.es_exchage, RabbitConfig.es_bind_key,
                new PostMqIndexMessage(id, PostMqIndexMessage.REMOVE));

        return ResultDto.success("删除成功", null).action("/");
    }


    /**
     * 回复功能
     * @param postId 被评论的文章ID
     * @param content 评论的内容
     * @return
     */
    @PostMapping("/post/reply")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResultDto postReply(Long postId, String content) {
        // 验证参数
        Assert.notNull(postId, "找不到对应的文章！");
        Assert.hasLength(content, "回复内容不能为空！");
        // 保存评论
        MPost post = mPostServiceImpl.getById(postId);
        Assert.isTrue(post != null, "该文章已被删除");
        // 组装评论实体
        MComment comment = new MComment();
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setUserId(getUserInfo().getId());
        comment.setCreated(new Date());
        comment.setModified(new Date());
        comment.setLevel(0);
        comment.setVoteDown(0);
        comment.setVoteUp(0);
        mCommentServiceImpl.save(comment);
        // 文章评论数量加一
        post.setCommentCount(post.getCommentCount() + 1);
        mPostServiceImpl.updateById(post);

        // 本周热议数据更新
        mPostServiceImpl.addCommentCountforWeekRank(postId, true);
        // 通知作者有人评论了你的文章
        if (comment.getUserId() != post.getId()) {
            MUserMessage message = new MUserMessage();
            message.setPostId(postId);
            message.setCommentId(comment.getId());
            message.setFromUserId(getUserInfo().getId());
            message.setToUserId(post.getUserId());
            message.setType(1);
            message.setContent(content);
            message.setCreated(new Date());
            message.setStatus(0);
            mUserMessageServiceImpl.save(message);

            // 即时通知作者（websocket）
            wsServiceImpl.sendMessCountToUser(message.getToUserId());
        }

        // 增加最近回复
        MUserAction action = new MUserAction();
        action.setCommentId(comment.getId() + "");
        action.setPoint(0);
        action.setPostId(postId + "");
        action.setUserId(getUserInfo().getId() + "");
        action.setCreated(new Date());

        // 通知被@的人，有人回复了你的评论
        if(content.startsWith("@")) {
            String username = content.substring(1, content.indexOf(" "));
            System.out.println(username);

            MUser user = mUserServiceImpl.getOne(new QueryWrapper<MUser>().eq("username", username));
            if (user != null) {
                MUserMessage message = new MUserMessage();
                message.setPostId(postId);
                message.setCommentId(comment.getId());
                message.setFromUserId(getUserInfo().getId());
                message.setToUserId(user.getId());
                message.setType(2);
                message.setContent(content);
                message.setCreated(new Date());
                message.setStatus(0);
                mUserMessageServiceImpl.save(message);
                action.setAction("回复");
                // 即时通知被@的用户
            }

        } else {
            action.setAction("评论");
        }
        //保存用户的最近回复
        mUserActionServiceImpl.save(action);
        return ResultDto.ok().action("/post/" + postId);
    }


    /**
     * 删除评论
     * @param id
     * @return
     */
    @PostMapping("/comment/delete")
    @ResponseBody
    public ResultDto commentDelete(Long id) {
        // 查询评论
        MComment comment = mCommentServiceImpl.getById(id);
        Assert.notNull(comment, "评论不存在！");
        Assert.isTrue(comment.getUserId().longValue() == getUserInfo().getId().longValue(), "这不是你的评论！");
        // 删除
        mCommentServiceImpl.removeById(id);
        return ResultDto.ok();
    }

}
