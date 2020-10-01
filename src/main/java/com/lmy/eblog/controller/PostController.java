package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/30 9:48
 * @version V1.0
 */

import org.springframework.stereotype.Controller;
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
        req.setAttribute("currentCategoryId", id);
        return "post/category";
    }


    /**
     * 博客详情控制器
     * @param id
     * @return
     */
    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        return "post/detail";
    }

}
