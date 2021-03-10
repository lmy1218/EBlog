package com.lmy.eblog.extension.search.repository;
/**
 * @Project eblog
 * @Package com.lmy.eblog.extension.search.repository
 * @author Administrator
 * @date 2020/10/6 14:07
 * @version V1.0
 */

import com.lmy.eblog.extension.search.model.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lmy
 * @ClassName PostRepository
 * @Description es操作类
 * @date 2020/10/6 14:07
 **/
@Repository
public interface PostRepository extends ElasticsearchRepository<PostDocument, Long> {

}
