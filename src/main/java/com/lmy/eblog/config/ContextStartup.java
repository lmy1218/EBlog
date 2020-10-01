package com.lmy.eblog.config;
/**
 * @Project eblog
 * @Package com.lmy.eblog.config
 * @author Administrator
 * @date 2020/9/30 10:54
 * @version V1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmy.eblog.entity.MCategory;
import com.lmy.eblog.service.MCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @author Lmy
 * @ClassName ContextStartup
 * @Description 项目启动时加载的信息
 * @date 2020/9/30 10:54
 **/
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    @Autowired
    private MCategoryService mCategoryServiceImpl;

    private ServletContext servletContext;

    /**
     * 将菜单加载出来后放入servletContext
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        QueryWrapper<MCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0)
                .select("id", "name");
        List<MCategory> categories = mCategoryServiceImpl.list(wrapper);
        servletContext.setAttribute("categorys", categories);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
