package com.lmy.eblog.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.lmy.eblog.extension.freemarker.templates.HotsTemplate;
import com.lmy.eblog.extension.freemarker.templates.PostsTemplate;
import com.lmy.eblog.extension.freemarker.templates.TimeAgoMethod;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class FreemarkerConfig {

    @Resource
    private freemarker.template.Configuration configuration;

    @Resource
    private PostsTemplate postsTemplate;

    @Resource
    private HotsTemplate hotsTemplate;


    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
        configuration.setSharedVariable("hots", hotsTemplate);
        configuration.setSharedVariable("shiro", new ShiroTags());
    }

}
