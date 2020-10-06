package com.lmy.eblog.search.model;
/**
 * @Project eblog
 * @Package com.lmy.eblog.search
 * @author Administrator
 * @date 2020/10/6 13:02
 * @version V1.0
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author Lmy
 * @ClassName PostDocument
 * @Description 搜索信息实体
 * @date 2020/10/6 13:02
 **/
@Data
@Document(indexName = "post", type = "docs", createIndex = true, shards = 1,replicas = 0)
public class PostDocument {
    // ID
    @Id
    private Long id;
    // 标题
    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String title;
    // 发布者id
    @Field(type = FieldType.Long)
    private Long authorId;
    // 发布者昵称
    @Field(type = FieldType.Keyword)
    private String authorName;
    // 发布者头像
    private String authorAvatar;
    // 分类id
    private Long categoryId;
    // 分类名称
    @Field(type = FieldType.Keyword)
    private String categoryName;
    // 评论数量
    private Integer commentCount;
    // 是否加精
    private Boolean recommend;
    // 置顶等级
    private Integer level;
    // 时间
    @Field(type = FieldType.Date)
    private Date created;

}
