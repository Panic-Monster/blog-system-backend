package com.jayson.blog.model.dto.articleType;

import com.baomidou.mybatisplus.annotation.*;
import com.jayson.blog.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章分类
 * @TableName articletype
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleTypeQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 分类名称
     */
    private String articleTypeName;

    /**
     * 文章量
     */
    private Integer articleNum;

    /**
     * 创建时间
     */
    private Date createTime;

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