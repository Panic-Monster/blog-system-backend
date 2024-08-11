package com.jayson.blog.model.dto.articleTag;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jayson_Y
 * @date: 2024/7/28
 * @project: blog-system-backend
 */
@Data
public class ArticleTagUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 标签名
     */
    private String articleTagName;

    /**
     * 是否启用（0-启用 1-禁用）
     */
    private Integer isEnable;

    private static final long serialVersionUID = 1L;
}
