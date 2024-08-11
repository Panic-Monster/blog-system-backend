package com.jayson.blog.model.dto.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.jayson.blog.common.PageRequest;
import com.jayson.blog.model.vo.ArticleTagVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户查询请求
 *
 * @author jayson
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleQueryRequest extends PageRequest implements Serializable {

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
     * 文章分类名称
     */
    private String articleTypeName;

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
     * 当前页
     */
    private long current;

    /**
     * 每页数据个数
     */
    private long pageSize;

    private static final long serialVersionUID = 1L;
}