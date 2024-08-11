package com.jayson.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.blog.common.ErrorCode;
import com.jayson.blog.common.SwitchTimeFormat;
import com.jayson.blog.exception.BusinessException;
import com.jayson.blog.mapper.ArticleMapper;
import com.jayson.blog.mapper.UserMapper;
import com.jayson.blog.model.dto.article.ArticleAddRequest;
import com.jayson.blog.model.entity.*;
import com.jayson.blog.model.vo.ArticleTagVO;
import com.jayson.blog.model.vo.ArticleVO;
import com.jayson.blog.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 * @description 针对表【article(文章表)】的数据库操作Service实现
 * @createDate 2024-07-28 09:16:30
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ArticleTypeService articleTypeService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleTagRelationService articleTagRelationService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    private final SwitchTimeFormat switchTimeFormat = new SwitchTimeFormat();


    /**
     * 维护标签和文章关系
     *
     * @param articleAddRequest
     * @param articleId
     * @return
     */
    @Override
    public Boolean addArticleTagRelation(ArticleAddRequest articleAddRequest, Long articleId) {
        articleAddRequest.getArticleTagList().forEach((item) -> {
            ArticleTagRelation articleTagRelation = new ArticleTagRelation();
            articleTagRelation.setArticleId(articleId);
            articleTagRelation.setTagId(item.getId());
            boolean result = articleTagRelationService.save(articleTagRelation);
            if (!result) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR);
            }
        });
        return true;
    }

    /**
     * 添加文章时修改标签关联文章数量
     *
     * @param articleAddRequest
     * @return
     */
    @Override
    public Boolean addArticleTagNum(ArticleAddRequest articleAddRequest) {
        List<ArticleTagVO> articleTagList = articleAddRequest.getArticleTagList();
        articleTagList.forEach(articleTagVO -> {
            ArticleTag articleTagOld = articleTagService.getById(articleTagVO.getId());
            int articleTagNum = articleTagOld.getArticleNum();
            ArticleTag articleTagNew = new ArticleTag();
            articleTagNew.setId(articleTagOld.getId());
            articleTagNew.setArticleNum(articleTagNum + 1);
            articleTagService.updateById(articleTagNew);
        });
        return true;
    }

    /**
     * 添加文章时修改分类关联文章数量
     *
     * @param articleAddRequest
     * @return
     */
    @Override
    public Boolean addArticleTypeNum(ArticleAddRequest articleAddRequest, Long articleId) {
        ArticleType articleTypeNew = new ArticleType();
        ArticleType articleTypeOld = articleTypeService.getById(articleAddRequest.getArticleTypeId());
        int articleTypeNum = articleTypeOld.getArticleNum();
        if (articleTypeNum < 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        articleTypeNew.setArticleNum(articleTypeNum + 1);
        articleTypeNew.setId(articleTypeOld.getId());
        boolean result = articleTypeService.updateById(articleTypeNew);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }

    /**
     * 获取文章作者名
     *
     * @param article
     * @return
     */
    @Override
    public String getAuthor(Article article) {
        User user = userMapper.selectById(article.getAuthorId());
        return user.getUserName();
    }

    /**
     * 删除文章时修改标签关联文章数量
     *
     * @param articleId
     * @return
     */
    @Override
    public Boolean reduceArticleTagNum(Long articleId) {
        if (articleId <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        QueryWrapper<ArticleTagRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("articleId", articleId);
        List<ArticleTagRelation> tagRelationList = articleTagRelationService.list(queryWrapper);
        tagRelationList.forEach(tagRelation -> {
            ArticleTag articleTagOld = articleTagService.getById(tagRelation.getTagId());
            ArticleTag articleTagNew = new ArticleTag();
            articleTagNew.setId(tagRelation.getTagId());
            articleTagNew.setArticleNum(articleTagOld.getArticleNum() - 1);
            boolean result = articleTagService.updateById(articleTagNew);
            if (!result) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        });
        boolean remove = articleTagRelationService.remove(queryWrapper);
        if (!remove) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }

    /**
     * 删除文章时修改分类关联文章数量
     *
     * @param articleId
     * @return
     */
    @Override
    public Boolean reduceArticleTypeNum(Long articleId) {
        if (articleId <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Article article = this.getById(articleId);
        Integer articleTypeId = article.getArticleTypeId();
        ArticleType articleType = articleTypeService.getById(articleTypeId);
        Integer articleNum = articleType.getArticleNum() - 1;
        ArticleType articleTypeNew = new ArticleType();
        articleTypeNew.setId(Long.valueOf(articleTypeId));
        articleTypeNew.setArticleNum(articleNum);
        boolean result = articleTypeService.updateById(articleTypeNew);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }
    /**
     * 根据Id获取
     * @param articleId
     * @return
     */
    @Override
    public ArticleVO getArticleById(Long articleId) {
        Article article = this.getById(articleId);
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        // 作者姓名
        User user = userService.getById(article.getAuthorId());
        articleVO.setAuthor(user.getUserName());
        // 修改时间格式
        String createTime = switchTimeFormat.switchTimeFormatToString(article.getCreateTime());
        String updateTime = switchTimeFormat.switchTimeFormatToString(article.getUpdateTime());
        articleVO.setCreateTime(createTime);
        articleVO.setUpdateTime(updateTime);
        // 查到文章分类添加到 VO
        ArticleType articleType = articleTypeService.getById(article.getArticleTypeId());
        articleVO.setArticleTypeId(articleType.getId());
        articleVO.setArticleTypeName(articleType.getArticleTypeName());
        // 查到文章标签添加到 VO
        QueryWrapper<ArticleTagRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("articleId", articleId);
        List<ArticleTagVO> articleTagVOList = new ArrayList<>();
        articleTagRelationService.list(queryWrapper).forEach(tagRelation -> {
            ArticleTag articleTag = articleTagService.getById(tagRelation.getTagId());
            if (articleTag == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            ArticleTagVO articleTagVO = new ArticleTagVO();
            BeanUtils.copyProperties(articleTag, articleTagVO);
            String tagCreateTime = switchTimeFormat.switchTimeFormatToString(articleTag.getCreateTime());
            String tagUpdateTime = switchTimeFormat.switchTimeFormatToString(articleTag.getUpdateTime());
            articleTagVO.setCreateTime(tagCreateTime);
            articleTagVO.setUpdateTime(tagUpdateTime);
            articleTagVOList.add(articleTagVO);
        });
        articleVO.setArticleTagList(articleTagVOList);
        return articleVO;
    }
}




