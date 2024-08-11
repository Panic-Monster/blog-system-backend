package com.jayson.blog.model.dto.comment;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jayson_Y
 * @date: 2024/8/11
 * @project: blog-system-backend
 */
@Data
public class CommentAddRequest implements Serializable {

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 父级Id
     */
    private Long parentId;

    /**
     * 根结点Id
     */
    private Long rootId;

    /**
     * 关联文章Id
     */
    private Long articleId;
    
    private static final long serialVersionUID = 1L;
}
