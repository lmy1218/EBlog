package com.lmy.eblog.extension.freemarker.templates;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.service.MPostService;
import com.lmy.eblog.extension.freemarker.templates.common.DirectiveHandler;
import com.lmy.eblog.extension.freemarker.templates.common.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsTemplate extends TemplateDirective {

    @Autowired
    MPostService mpostServiceImpl;

    @Override
    public String getName() {
        return "posts";
    }

    /**
     * 处理置顶
     * @param handler
     * @throws Exception
     */
    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        Integer level = handler.getInteger("level");
        Integer pn = handler.getInteger("pn", 1);
        Integer size = handler.getInteger("size", 2);
        Long categoryId = handler.getLong("categoryId");

        IPage page = mpostServiceImpl.paging(new Page(pn, size), categoryId, null, level, null, "created");

        handler.put(RESULTS, page).render();
    }
}
