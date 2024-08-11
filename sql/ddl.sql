-- 创建库
create database if not exists 'blog-system';

-- 切换库
use `blog-system`;

-- 用户表
create table if not exists `blog-system`.user
(
    id              bigint auto_increment comment 'id'
        primary key,
    userName        varchar(256)                           null comment '用户昵称',
    userAccount     varchar(256)                           not null comment '账号',
    userAvatar      varchar(1024)                          null comment '用户头像',
    userRole        varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword    varchar(512)                           not null comment '密码',
    userIp          varchar(128)                           null comment '登录IP',
    userAddress     varchar(128)                           null comment '用户登录地址',
    finallLoginTime datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '最后登录时间',
    createTime      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
)
    comment '用户表';

-- 文章表
create table if not exists `blog-system`.article
(
    id             bigint auto_increment comment '主键'
        primary key,
    articleCover   varchar(256)                       null comment '文章封面',
    authorId       bigint                             not null comment '作者ID',
    articleTypeId  int                                not null comment '文章类型ID',
    articleTags    varchar(512)                       null comment '文章标签',
    articleContent text                               not null comment '文章内容',
    createTIme     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除（0-未删除 1-删除）'
)
    comment '文章表';

-- 文章分类表
create table if not exists `blog-system`.articletype
(
    id         bigint auto_increment comment '主键'
        primary key,
    typeName   varchar(128)                       not null comment '分类名称',
    articleNum int      default 0                 not null comment '文章量',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除（0-未删除 1-删除）'
)
    comment '文章分类';

-- 文章和文章分类关系
create table if not exists `blog-system`.`article-type`
(
    id         bigint auto_increment comment '主键'
        primary key,
    articleId  bigint                             not null comment '文章ID',
    typeId     bigint                             not null comment '文章分类ID',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '文章和文章分类关系';


