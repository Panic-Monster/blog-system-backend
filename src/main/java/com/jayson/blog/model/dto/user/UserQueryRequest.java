package com.jayson.blog.model.dto.user;

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
 * 用户查询请求
 *
 * @author jayson
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 登录IP
     */
    private String userIp;

    /**
     * 用户登录地址
     */
    private String userAddress;

    /**
     * 最后登录时间
     */
    private Date finallLoginTime;

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