package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/29 19:09
 * @version V1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lmy
 * @ClassName IndexController
 * @Description 主控制器
 * @date 2020/9/29 19:09
 **/
@Controller
public class IndexController extends BaseController {


    /**
     * 首页展示以及博客分页
     * @return
     */
    @RequestMapping({"", "/", "index"})
    public String index() {

        // 参数： 分页信息 分类 置顶 用户 精选 排序
        IPage<PostVo> result = mPostServiceImpl.paging(getPage(), null, null, null, null, "created");

        // 返回首页菜单标识
        req.setAttribute("pageData", result);
        req.setAttribute("currentCategoryId", 0);
        return "index";
    }



}
