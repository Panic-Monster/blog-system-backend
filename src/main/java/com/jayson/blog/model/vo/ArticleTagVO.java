package com.jayson.blog.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Jayson_Y
 * @date: 2024/7/28
 * @project: blog-system-backend
 */
@Data
public class ArticleTagVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 标签名
     */
    private String articleTagName;

    /**
     * 文章数量
     */
    private Integer articleNum;

    /**
     * 是否启用（0-启用 1-禁用）
     */
    private Integer isEnable;

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
