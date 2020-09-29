package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/29 19:09
 * @version V1.0
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lmy
 * @ClassName IndexController
 * @Description 主控制器
 * @date 2020/9/29 19:09
 **/
@Controller
public class IndexController {


    @RequestMapping({"", "/", "index"})
    public String index() {
        return "index";
    }



}
