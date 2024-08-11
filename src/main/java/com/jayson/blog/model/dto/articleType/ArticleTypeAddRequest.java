package com.jayson.blog.model.dto.articleType;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jayson_Y
 * @date: 2024/7/28
 * @project: blog-system-backend
 */
@Data
public class ArticleTypeAddRequest implements Serializable {

    /**
     * 文章分类名称
     */
    private String articleTypeName;

    private static final long serialVersionUID = 1L;
}
