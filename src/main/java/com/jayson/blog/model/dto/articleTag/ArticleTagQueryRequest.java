package com.jayson.blog.model.dto.articleTag;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleTagQueryRequest extends PageRequest implements Serializable {

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
