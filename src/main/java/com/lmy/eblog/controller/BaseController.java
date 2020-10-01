package com.lmy.eblog.controller;
/**
 * @Project eblog
 * @Package com.lmy.eblog.controller
 * @author Administrator
 * @date 2020/9/30 10:47
 * @version V1.0
 */

import com.lmy.eblog.service.MPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lmy
 * @ClassName BaseController
 * @Description 控制器公共部分
 * @date 2020/9/30 10:47
 **/
@Controller
public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    MPostService mPostServiceImpl;

}
