package com.jayson.blog.controller;

import com.jayson.blog.common.BaseResponse;
import com.jayson.blog.common.ErrorCode;
import com.jayson.blog.common.ResultUtils;
import com.jayson.blog.exception.BusinessException;
import com.jayson.blog.model.dto.comment.CommentAddRequest;
import com.jayson.blog.model.entity.Comment;
import com.jayson.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Jayson_Y
 * @date: 2024/8/11
 * @project: blog-system-backend
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 提交评论或留言
     * @param commentAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addComment(@RequestBody CommentAddRequest commentAddRequest) {
        if (commentAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddRequest, comment);
        boolean save = commentService.save(comment);
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(comment.getId());
    }
}
