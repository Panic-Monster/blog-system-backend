package com.jayson.blog.model.dto.articleTag;

import com.jayson.blog.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Jayson_Y
 * @date: 2024/7/28
 * @project: blog-system-backend
 */
@Data
public class ArticleTagAddRequest implements Serializable {

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
