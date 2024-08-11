package com.jayson.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.jayson.blog.common.*;
import com.jayson.blog.exception.BusinessException;
import com.jayson.blog.model.dto.article.ArticleAddRequest;
import com.jayson.blog.model.dto.article.ArticleQueryRequest;
import com.jayson.blog.model.entity.Article;
import com.jayson.blog.model.entity.ArticleTag;
import com.jayson.blog.model.entity.ArticleTagRelation;
import com.jayson.blog.model.entity.ArticleType;
import com.jayson.blog.model.vo.ArticleTagVO;
import com.jayson.blog.model.vo.ArticleVO;
import com.jayson.blog.service.ArticleService;
import com.jayson.blog.service.ArticleTagRelationService;
import com.jayson.blog.service.ArticleTagService;
import com.jayson.blog.service.ArticleTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Jayson_Y
 * @date: 2024/7/22
 * @project: blog-system-backend
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleTagRelationService articleTagRelationService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleTypeService articleTypeService;

    private final SwitchTimeFormat switchTimeFormat = new SwitchTimeFormat();

    /**
     * 添加文章
     * @param articleAddRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/add")
    public BaseResponse<Long> addArticle(@RequestBody ArticleAddRequest articleAddRequest) {
        if(articleAddRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleAddRequest, article);
        boolean save = articleService.save(article);
        if(!save){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        // 修改文章和标签关系表
        Boolean resultRelation = articleService.addArticleTagRelation(articleAddRequest, article.getId());
        if(!resultRelation){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        // 分类的文章相关数量 + 1
        Boolean resultType = articleService.addArticleTypeNum(articleAddRequest, article.getId());
        if(!resultType){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        //标签的文章相关数量 + 1
        Boolean resultTag = articleService.addArticleTagNum(articleAddRequest);
        if(!resultTag){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(article.getId());
    }

    /**
     * 分页获取文章列表
     *
     * @param articleQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ArticleVO>> listArticleByPage(ArticleQueryRequest articleQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        QueryWrapper<ArticleType> queryWrapperArticleType = new QueryWrapper<>();
        queryWrapperArticleType.eq("articleTypeName", articleQueryRequest.getArticleTypeName());
        ArticleType articleTypeTemp = articleTypeService.getOne(queryWrapperArticleType);
        Article articleQuery = new Article();
        if (articleQueryRequest != null) {
            BeanUtils.copyProperties(articleQueryRequest, articleQuery);
            articleQuery.setArticleTypeId(Math.toIntExact(articleTypeTemp.getId()));
            current = articleQueryRequest.getCurrent();
            size = articleQueryRequest.getPageSize();
        }
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>(articleQuery);
        Page<Article> articlePage = articleService.page(new Page<>(current, size), queryWrapper);
        Page<ArticleVO> articleVOPage = new PageDTO<>(articlePage.getCurrent(), articlePage.getSize(), articlePage.getTotal());
        List<ArticleVO> articleVOList = articlePage.getRecords().stream().map(article -> {
            //联表查标签
            Long articleId = article.getId();
            QueryWrapper<ArticleTagRelation> articleTagRelationQueryWrapper = new QueryWrapper<>();
            articleTagRelationQueryWrapper.eq("articleId", articleId);
            List<ArticleTagRelation> list = articleTagRelationService.list(articleTagRelationQueryWrapper);
            List<ArticleTagVO> articleTagVOList = list.stream().map(articleTagRelation -> {
                ArticleTagVO articleTagVO = new ArticleTagVO();
                ArticleTag articleTag = articleTagService.getById(articleTagRelation.getTagId());
                BeanUtils.copyProperties(articleTag, articleTagVO);
                return articleTagVO;
            }).collect(Collectors.toList());
            ArticleVO articleVO = new ArticleVO();
            //联表查分类
            ArticleType articleType = articleTypeService.getById(article.getArticleTypeId());
            articleVO.setArticleTypeName(articleType.getArticleTypeName());
            // 转换时间格式
            String createTimeStr = switchTimeFormat.switchTimeFormatToString(article.getCreateTime());
            String updateTimeStr = switchTimeFormat.switchTimeFormatToString(article.getUpdateTime());
            BeanUtils.copyProperties(article, articleVO);
            articleVO.setCreateTime(createTimeStr);
            articleVO.setUpdateTime(updateTimeStr);
            articleVO.setArticleTagList(articleTagVOList);
            String author = articleService.getAuthor(article);
            articleVO.setAuthor(author);
            return articleVO;
        }).collect(Collectors.toList());
        articleVOPage.setRecords(articleVOList);
        return ResultUtils.success(articleVOPage);
    }

    /**
     * 删除文章
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteArticle (@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long articleId = deleteRequest.getId();
        // 查询文章的类型和标签，删除时同时减少文章数量字段
        Boolean resultTag = articleService.reduceArticleTagNum(articleId);
        if(!resultTag){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新标签文章数量失败");
        }
        Boolean resultType = articleService.reduceArticleTypeNum(articleId);
        if(!resultType){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新分类文章数量失败");
        }
        // 维护文章和标签关系表
        boolean result = articleService.removeById(articleId);
        return ResultUtils.success(result);
    }

    /**
     * 根据ID查询文章
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ArticleVO> getArticleById (long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleVO articleVO = articleService.getArticleById(id);
        return ResultUtils.success(articleVO);
    }
}
