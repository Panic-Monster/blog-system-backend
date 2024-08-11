package com.jayson.blog.model.dto.article;

import com.jayson.blog.model.entity.ArticleTag;
import com.jayson.blog.model.vo.ArticleTagVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Jayson_Y
 * @date: 2024/7/22
 * @project: blog-system-backend
 */
@Data
public class ArticleAddRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文章名称
     */
    private String articleName;

    /**
     * 文章封面
     */
    private String articleCover;

    /**
     * 文章简介
     */
    private String articleProfile;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 标签数组
     */
    private List<ArticleTagVO> articleTagList;

    /**
     * 文章分类ID
     */
    private Integer articleTypeId;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 是否发布（0-发布 1-下架）
     */
    private Integer isPublish;

    /**
     * 是否推荐（0-不推荐 1-推荐）
     */
    private Integer isRecommend;

    /**
     * 是否置顶（0-不置顶 1-置顶）
     */
    private Integer isTop;

    /**
     * 是否出现在轮播图中（0-不出现 1- 出现）
     */
    private Integer isCarousel;

    private static final long serialVersionUID = 1L;
}
