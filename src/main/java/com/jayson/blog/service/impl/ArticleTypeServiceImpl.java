package com.jayson.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.blog.model.entity.ArticleType;
import com.jayson.blog.service.ArticleTypeService;
import com.jayson.blog.mapper.ArticleTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【articletype(文章分类)】的数据库操作Service实现
* @createDate 2024-07-28 09:56:38
*/
@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType>
    implements ArticleTypeService {

}




