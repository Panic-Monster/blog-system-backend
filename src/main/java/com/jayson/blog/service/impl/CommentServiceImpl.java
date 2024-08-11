package com.jayson.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.blog.model.entity.Comment;
import com.jayson.blog.service.CommentService;
import com.jayson.blog.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【comment(评论&留言)】的数据库操作Service实现
* @createDate 2024-08-11 00:22:18
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




