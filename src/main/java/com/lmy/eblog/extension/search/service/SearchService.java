package com.lmy.eblog.extension.search.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.extension.mq.PostMqIndexMessage;
import com.lmy.eblog.extension.search.model.PostDocument;
import com.lmy.eblog.pojo.vo.PostVo;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project eblog
 * @Package com.lmy.eblog.extension.search.service
 * @date 2020/10/6 14:20
 */
public interface SearchService {
    IPage search(Page page, String query);

    List<PostDocument> buildPost(List<PostVo> postList);

    void createOrUpdateIndex(PostMqIndexMessage message);

    void removeIndex(PostMqIndexMessage message);
}
