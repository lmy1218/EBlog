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
import com.lmy.eblog.vo.PageVo;
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

        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 2);

        // 分页查询博客
        Page page = new Page(pn, size);

        // 分页信息 分类 置顶 用户 精选 排序
        IPage<PageVo> result = mPostServiceImpl.paging(page, null, null, null, null, "created");

        // 返回首页菜单标识
        req.setAttribute("pageData", result);
        req.setAttribute("currentCategoryId", 0);
        return "index";
    }



}
