package com.BlogApp.serviceImplementation;

import com.BlogApp.dto.CommentDto;
import com.BlogApp.entity.Comment;
import com.BlogApp.entity.Posts;
import com.BlogApp.entity.User;
import com.BlogApp.repository.CommentRepo;
import com.BlogApp.repository.PostRepo;
import com.BlogApp.repository.UserRepo;
import com.BlogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createCommentWithPostAndUserId(CommentDto commentDto, int postId) {
        Posts post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));


        Comment comment = modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, int commentId) {
        return null;
    }

    @Override
    public void deleteComment(int commentId) {

    }
}
