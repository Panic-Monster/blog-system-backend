package com.jayson.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.blog.model.entity.ArticleTagRelation;
import com.jayson.blog.service.ArticleTagRelationService;
import com.jayson.blog.mapper.ArticleTagRelationMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【article-tag(文章和文章标签关系)】的数据库操作Service实现
* @createDate 2024-07-31 19:12:50
*/
@Service
public class ArticleTagRelationServiceImpl extends ServiceImpl<ArticleTagRelationMapper, ArticleTagRelation>
    implements ArticleTagRelationService{

}




