package com.jayson.blog.service;

import com.jayson.blog.model.dto.article.ArticleAddRequest;
import com.jayson.blog.model.entity.Article ;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jayson.blog.model.vo.ArticleVO;

/**
* @author DELL
* @description 针对表【article(文章表)】的数据库操作Service
* @createDate 2024-07-28 09:16:30
*/
public interface ArticleService extends IService<Article> {

    /**
     * 维护文章和标签关系
     * @param articleAddRequest
     * @param articleId
     * @return
     */
    Boolean addArticleTagRelation(ArticleAddRequest articleAddRequest, Long articleId);
    /**
     * 添加文章时修改标签关联文章数量
     * @param articleAddRequest
     * @return
     */
    Boolean addArticleTagNum(ArticleAddRequest articleAddRequest);
    /**
     * 添加文章时修改分类关联文章数量
     * @param articleAddRequest
     * @return
     */
    Boolean addArticleTypeNum(ArticleAddRequest articleAddRequest, Long articleId);
    /**
     * 获取文章作者名
     * @param article
     * @return
     */
    String getAuthor(Article article);
    /**
     * 删除文章时修改标签关联文章数量
     * @param articleId
     * @return
     */
    Boolean reduceArticleTagNum(Long articleId);
    /**
     * 删除文章时修改标签关联文章数量
     * @param articleId
     * @return
     */
    Boolean reduceArticleTypeNum(Long articleId);
    /**
     * 根据Id获取（完整版 包括文章分类和文章标签）
     * @param articleId
     * @return
     */
    ArticleVO getArticleById(Long articleId);
}
