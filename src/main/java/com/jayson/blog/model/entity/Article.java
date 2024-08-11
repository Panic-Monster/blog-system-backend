package com.jayson.blog.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jayson.blog.model.vo.ArticleTagVO;
import lombok.Data;

/**
 * 文章表
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除（0-未删除 1-删除）
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}