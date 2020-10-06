package com.lmy.eblog.search.service.impl;
/**
 * @Project eblog
 * @Package com.lmy.eblog.search.service.impl
 * @author Administrator
 * @date 2020/10/6 14:21
 * @version V1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmy.eblog.entity.MPost;
import com.lmy.eblog.mq.PostMqIndexMessage;
import com.lmy.eblog.search.model.PostDocument;
import com.lmy.eblog.search.repository.PostRepository;
import com.lmy.eblog.search.service.SearchService;
import com.lmy.eblog.service.MPostService;
import com.lmy.eblog.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lmy
 * @ClassName SearchServiceImpl
 * @Description 搜索业务
 * @date 2020/10/6 14:21
 **/
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    PostRepository postRepository;


    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    MPostService mPostServiceImpl;

    @Override
    public IPage search(Page page, String query) {
        // 设置分页信息
        Long curr = page.getCurrent() - 1;
        Long size = page.getSize();

        PageRequest pageRequest = PageRequest.of(curr.intValue(), size.intValue());
        // 搜索
        // 多个字段匹配
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "title", "authorName", "categoryName");
        // 查询
        org.springframework.data.domain.Page<PostDocument> documents = postRepository.search(queryBuilder, pageRequest);

        // 组装结果
        IPage pageData = new Page(page.getCurrent(), page.getSize(), documents.getTotalElements());
        pageData.setRecords(documents.getContent());

        return pageData;
    }

    @Override
    public List<PostDocument> buildPost(List<PostVo> postList) {
        List<PostDocument> documents = postList.stream().map(p -> {
            // 将p中的数据映射到document中
            return modelMapper.map(p, PostDocument.class);
        }).collect(Collectors.toList());
        return documents;
    }

    @Override
    public void createOrUpdateIndex(PostMqIndexMessage message) {
        Long postId = message.getPostId();
        PostVo postVo = mPostServiceImpl.selectOnePost(new QueryWrapper<MPost>().eq("p.id", postId));
        PostDocument postDocment = modelMapper.map(postVo, PostDocument.class);

        postRepository.save(postDocment);

        log.info("es 索引更新成功！ ---> {}", postDocment.toString());
    }

    @Override
    public void removeIndex(PostMqIndexMessage message) {
        Long postId = message.getPostId();

        postRepository.deleteById(postId);
        log.info("es 索引删除成功！ ---> {}", message.toString());
    }
}
