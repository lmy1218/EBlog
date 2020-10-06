package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/10/5 20:01
 * @version V1.0
 */

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.dto.ResultDto;
import com.lmy.eblog.entity.MPost;
import com.lmy.eblog.search.model.PostDocument;
import com.lmy.eblog.search.repository.PostRepository;
import com.lmy.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lmy
 * @ClassName AdminController
 * @Description 管理员控制器
 * @date 2020/10/5 20:01
 **/
@Controller
@RequestMapping("admin")
public class AdminController extends BaseController {


    @Autowired
    PostRepository postRepository;


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


    /**
     * 同步es数据
     * @return
     */
    @PostMapping("init")
    @ResponseBody
    public ResultDto initEsData() {
        // 查询spu信息
        int page = 1;
        int rows = 100;
        int size = 0;
        int tatol = 0;
        do{
            Page pg = new Page(page, rows);
            IPage<PostVo> posts = mPostServiceImpl.paging(pg, null, null, null, null, "created");
            List<PostVo> postList = posts.getRecords();
            if (CollectionUtils.isEmpty(postList)) {
                break;
            }
            //构建成goods
            List<PostDocument> documents = searchServiceImpl.buildPost(postList);
            //导入数据库索引
            postRepository.saveAll(documents);
            page++;
            size = postList.size();
            tatol += size;
        } while (size == 100);

        return ResultDto.success("Es数据同步成功，共 "+ tatol + " 条记录！", null);
    }

}
