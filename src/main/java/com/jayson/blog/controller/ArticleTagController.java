package com.jayson.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.jayson.blog.common.*;
import com.jayson.blog.exception.BusinessException;
import com.jayson.blog.model.dto.article.ArticleQueryRequest;
import com.jayson.blog.model.dto.articleTag.ArticleTagAddRequest;
import com.jayson.blog.model.dto.articleTag.ArticleTagQueryRequest;
import com.jayson.blog.model.dto.articleTag.ArticleTagUpdateRequest;
import com.jayson.blog.model.dto.articleType.ArticleTypeQueryRequest;
import com.jayson.blog.model.entity.ArticleTag;
import com.jayson.blog.model.entity.ArticleType;
import com.jayson.blog.model.vo.ArticleTagVO;
import com.jayson.blog.model.vo.ArticleTypeVO;
import com.jayson.blog.service.ArticleTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Jayson_Y
 * @date: 2024/7/28
 * @project: blog-system-backend
 */
@RestController
@RequestMapping("/tag")
public class ArticleTagController {

    @Resource
    private ArticleTagService articleTagService;

    private SwitchTimeFormat switchTimeFormat = new SwitchTimeFormat();

    // 增删改查

    /**
     * 添加文章标签
     *
     * @param articleTagAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addArticleTag(@RequestBody ArticleTagAddRequest articleTagAddRequest) {
        if (articleTagAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleTag articleTag = new ArticleTag();
        BeanUtils.copyProperties(articleTagAddRequest, articleTag);
        boolean save = articleTagService.save(articleTag);
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(articleTag.getId());
    }

    /**
     * 删除文章标签
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteArticleTag(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = articleTagService.removeById(deleteRequest.getId());
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 更新文章标签
     * @param articleTagUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateArticleTag(@RequestBody ArticleTagUpdateRequest articleTagUpdateRequest) {
        if(articleTagUpdateRequest == null || articleTagUpdateRequest.getId() < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleTag articleTag = new ArticleTag();
        BeanUtils.copyProperties(articleTagUpdateRequest, articleTag);
        boolean result = articleTagService.updateById(articleTag);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 获取所有文章标签
     * @param articleTagQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<ArticleTagVO>> listArticleTag(ArticleTagQueryRequest articleTagQueryRequest) {
        ArticleTag articleTagQuery = new ArticleTag();
        if(articleTagQueryRequest != null) {
            BeanUtils.copyProperties(articleTagQueryRequest, articleTagQuery);
        }
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        List<ArticleTagVO> articleTagVOList = articleTagList.stream().map(articleTag -> {
            ArticleTagVO articleTagVO = new ArticleTagVO();
            String createTimeStr = switchTimeFormat.switchTimeFormatToString(articleTag.getCreateTime());
            String updateTimeStr = switchTimeFormat.switchTimeFormatToString(articleTag.getUpdateTime());
            BeanUtils.copyProperties(articleTag, articleTagVO);
            articleTagVO.setCreateTime(createTimeStr);
            articleTagVO.setUpdateTime(updateTimeStr);
            return articleTagVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(articleTagVOList);
    }

    /**
     * 分页获取文章标签列表
     *
     * @param articleTagQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ArticleTagVO>> listArticleTagByPage(ArticleTagQueryRequest articleTagQueryRequest) {
        long current = 1;
        long size = 10;
        ArticleTag articleTagQuery = new ArticleTag();
        if (articleTagQueryRequest != null) {
            BeanUtils.copyProperties(articleTagQueryRequest, articleTagQuery);
            current = articleTagQueryRequest.getCurrent();
            size = articleTagQueryRequest.getPageSize();
        }
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>(articleTagQuery);
        Page<ArticleTag> articleTagPage = articleTagService.page(new Page<>(current, size), queryWrapper);
        Page<ArticleTagVO> articleTagVOPage = new PageDTO<>(articleTagPage.getCurrent(), articleTagPage.getSize(), articleTagPage.getTotal());
        List<ArticleTagVO> articleTagVOList = articleTagPage.getRecords().stream().map(articleTag -> {
            String createTimeStr = switchTimeFormat.switchTimeFormatToString(articleTag.getCreateTime());
            String updateTimeStr = switchTimeFormat.switchTimeFormatToString(articleTag.getUpdateTime());
            ArticleTagVO articleTagVO = new ArticleTagVO();
            BeanUtils.copyProperties(articleTag, articleTagVO);
            articleTagVO.setCreateTime(createTimeStr);
            articleTagVO.setCreateTime(updateTimeStr);
            return articleTagVO;
        }).collect(Collectors.toList());
        articleTagVOPage.setRecords(articleTagVOList);
        return ResultUtils.success(articleTagVOPage);
    }
    /**
     * 获取所有文章标签（除去被禁用的标签）
     * @param articleTagQueryRequest
     * @return
     */
    @GetMapping("/list/front")
    public BaseResponse<List<ArticleTagVO>> listArticleTagFront(ArticleTagQueryRequest articleTagQueryRequest) {
        ArticleTag articleTagQuery = new ArticleTag();
        if(articleTagQueryRequest != null) {
            BeanUtils.copyProperties(articleTagQueryRequest, articleTagQuery);
        }
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isEnable", 0);
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        List<ArticleTagVO> articleTagVOList = articleTagList.stream().map(articleTag -> {
            ArticleTagVO articleTagVO = new ArticleTagVO();
            String createTimeStr = switchTimeFormat.switchTimeFormatToString(articleTag.getCreateTime());
            String updateTimeStr = switchTimeFormat.switchTimeFormatToString(articleTag.getUpdateTime());
            BeanUtils.copyProperties(articleTag, articleTagVO);
            articleTagVO.setCreateTime(createTimeStr);
            articleTagVO.setUpdateTime(updateTimeStr);
            return articleTagVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(articleTagVOList);
    }
}
