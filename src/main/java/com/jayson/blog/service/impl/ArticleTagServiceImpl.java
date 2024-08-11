package com.jayson.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.blog.common.ErrorCode;
import com.jayson.blog.exception.BusinessException;
import com.jayson.blog.mapper.ArticleTagMapper;
import com.jayson.blog.model.entity.ArticleTag;
import com.jayson.blog.service.ArticleTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【articletag(文章标签)】的数据库操作Service实现
* @createDate 2024-07-28 18:09:15
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService {

}




