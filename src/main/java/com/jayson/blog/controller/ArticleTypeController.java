package com.jayson.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.jayson.blog.common.BaseResponse;
import com.jayson.blog.common.DeleteRequest;
import com.jayson.blog.common.ErrorCode;
import com.jayson.blog.common.ResultUtils;
import com.jayson.blog.exception.BusinessException;
import com.jayson.blog.model.dto.articleType.ArticleTypeAddRequest;
import com.jayson.blog.model.dto.articleType.ArticleTypeQueryRequest;
import com.jayson.blog.model.dto.articleType.ArticleTypeUpdateRequest;
import com.jayson.blog.model.entity.ArticleType;
import com.jayson.blog.model.vo.ArticleTypeVO;
import com.jayson.blog.model.vo.UserVO;
import com.jayson.blog.service.ArticleTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章分类接口
 *
 * @author: Jayson_Y
 * @date: 2024/7/28
 * @project: blog-system-backend
 */
@RestController
@RequestMapping("/category")
public class ArticleTypeController {

    @Resource
    private ArticleTypeService articleTypeService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 增删改查

    /**
     * 添加文章分类
     *
     * @param articleTypeAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addArticleType(@RequestBody ArticleTypeAddRequest articleTypeAddRequest) {
        if (articleTypeAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeAddRequest, articleType);
        boolean save = articleTypeService.save(articleType);
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(articleType.getId());
    }

    /**
     * 删除文章分类
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteArticleType(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = articleTypeService.removeById(deleteRequest.getId());
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 更新文章分类
     *
     * @param articleTypeUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateArticleType(@RequestBody ArticleTypeUpdateRequest articleTypeUpdateRequest) {
        if (articleTypeUpdateRequest == null || articleTypeUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeUpdateRequest, articleType);
        boolean result = articleTypeService.updateById(articleType);
        return ResultUtils.success(result);
    }

    /**
     * 获取所有文章分类
     * @param articleTypeQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<ArticleTypeVO>> listArticleType(ArticleTypeQueryRequest articleTypeQueryRequest) {
        ArticleType articleTypeQuery = new ArticleType();
        if(articleTypeQueryRequest != null) {
            BeanUtils.copyProperties(articleTypeQueryRequest, articleTypeQuery);
        }
        QueryWrapper<ArticleType> queryWrapper = new QueryWrapper<>();
        List<ArticleType> articleList = articleTypeService.list(queryWrapper);
        List<ArticleTypeVO> articleTypeVOList = articleList.stream().map(articleType -> {
            ArticleTypeVO articleTypeVO = new ArticleTypeVO();
            String createTimeStr = sdf.format(articleType.getCreateTime());
            String updateTimeStr = sdf.format(articleType.getUpdateTime());
            BeanUtils.copyProperties(articleType, articleTypeVO);
            articleTypeVO.setCreateTime(createTimeStr);
            articleTypeVO.setUpdateTime(updateTimeStr);
            return articleTypeVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(articleTypeVOList);
    }

    /**
     * 分页获取文章分类列表
     *
     * @param articleTypeQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ArticleTypeVO>> listArticleTypeByPage(ArticleTypeQueryRequest articleTypeQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        ArticleType articleTypeQuery = new ArticleType();
        if (articleTypeQueryRequest != null) {
            BeanUtils.copyProperties(articleTypeQueryRequest, articleTypeQuery);
            current = articleTypeQueryRequest.getCurrent();
            size = articleTypeQueryRequest.getPageSize();
        }
        QueryWrapper<ArticleType> queryWrapper = new QueryWrapper<>(articleTypeQuery);
        Page<ArticleType> articleTypePage = articleTypeService.page(new Page<>(current, size), queryWrapper);
        Page<ArticleTypeVO> articleTypeVOPage = new PageDTO<>(articleTypePage.getCurrent(), articleTypePage.getSize(), articleTypePage.getTotal());
        List<ArticleTypeVO> articleTypeVOList = articleTypePage.getRecords().stream().map(articleType -> {
            String createTimeStr = sdf.format(articleType.getCreateTime());
            String updateTimeStr = sdf.format(articleType.getUpdateTime());
            ArticleTypeVO articleTypeVO = new ArticleTypeVO();
            BeanUtils.copyProperties(articleType, articleTypeVO);
            articleTypeVO.setCreateTime(createTimeStr);
            articleTypeVO.setUpdateTime(updateTimeStr);
            return articleTypeVO;
        }).collect(Collectors.toList());
        articleTypeVOPage.setRecords(articleTypeVOList);
        return ResultUtils.success(articleTypeVOPage);
    }
}
