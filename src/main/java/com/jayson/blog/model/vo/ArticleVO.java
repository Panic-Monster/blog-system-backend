package com.jayson.blog.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章视图
 *
 * @TableName article
 */
@Data
public class ArticleVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文章封面
     */
    private String articleName;

    /**
     * 文章简介
     */
    private String articleProfile;

    /**
     * 文章封面
     */
    private String articleCover;

    /**
     * 作者
     */
    private String author;
    /**
     * 文章类型名称
     */
    private Long articleTypeId;

    /**
     * 文章分类名称
     */
    private String articleTypeName;

    /**
     * 标签数组
     */
    private List<ArticleTagVO> articleTagList;

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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}