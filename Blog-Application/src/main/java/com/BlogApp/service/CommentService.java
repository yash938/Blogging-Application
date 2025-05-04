package com.BlogApp.service;

import com.BlogApp.dto.CommentDto;
import com.BlogApp.dto.UserDto;
import com.BlogApp.entity.User;

public interface CommentService {
    CommentDto createCommentWithPostAndUserId(CommentDto commentDto,int postId);
    CommentDto updateComment(CommentDto commentDto,int commentId);
    void deleteComment(int commentId);
}
